package aula_03_sockets_multicliente;

import dao.ClienteDAO;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import view.Frame;

public class TrataCliente implements Runnable {

    public Socket soquete_cliente;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ArrayList<Mensagem> mensagens;
    private ArrayList<String> clienteConectado;
    private ArrayList<Mensagem> MensagensRecebidas = new ArrayList<>();
    ClienteDAO sa = new ClienteDAO();

    private String nome;

    public TrataCliente(Socket soquete_cliente, ArrayList<Mensagem> mensagens, ArrayList<String> clienteConectado) throws Exception {
        super();
        this.soquete_cliente = soquete_cliente;
        this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
        this.mensagens = mensagens;
        this.clienteConectado = clienteConectado;
    }

    public TrataCliente() {

    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    public void finalizar() throws IOException {
        this.soquete_cliente.close();
    }

    @Override
    public void run() {
        try {
            //1 - conectar; 2- sair; 3- listar mensagem; 4- listar cliente; 5- enviar sms
            //this.clienteConectado.add(this.soquete_cliente.getInetAddress().toString());

            boolean saiu = false;

            do {
                Mensagem mensagem = (Mensagem) receber_mensagem();
                int opcao;

                if (mensagem.getTexto().contains("5")) {
                    opcao = 5;
                } else if (mensagem.getTexto().contains("6")) {
                    opcao = 6;
                } else {
                    opcao = Integer.parseInt(mensagem.getTexto());
                    System.out.println(opcao);
                }

                Mensagem sms = new Mensagem(mensagem.getNome(), mensagem.getTexto());

                if (!this.clienteConectado.contains(sms.getNome() + "/" + this.soquete_cliente.getInetAddress().toString())) {
                    this.clienteConectado.add(sms.getNome() + "/" + this.soquete_cliente.getInetAddress().toString());
                }

                switch (opcao) {
                    case 1:
                        //conectar
                        if (sa.consultaCliente(sms.getNome())) {
                            sa.gravarCliente(sms, this.soquete_cliente.getInetAddress().toString());
                        }
                        break;
                    case 2:
                        //sair
                        sa.deletaBanco(sms.getNome());
                        saiu = true;
                        break;
                    case 3:
                        //listar mensagens
                        ArrayList<Mensagem> mensagens = sa.consultaMensagens();
                        enviar_mensagem(mensagens);
                        break;
                    case 4:
                        //listar clientes conectados
                        this.clienteConectado = sa.consultaTodosClientes();
                        enviar_mensagem(this.clienteConectado);
                        break;
                    case 5:
                        //enviar sms
                        //Mensagem receber = (Mensagem) receber_mensagem();
                        String text[] = mensagem.getTexto().split("- ");
                        mensagem = new Mensagem(mensagem.getNome(), text[1]);
                        sa.gravaMensagem(mensagem);
                        break;
                    case 6:
                        String splitNome[] = mensagem.getNome().split(",");
                        String splitTexto[] = mensagem.getTexto().split(", ");
                        sa.enviarSMS(splitNome[0], splitNome[1], splitTexto[0]);
                        break;
                    case 7:
                        //listar mensagens enviadas
                        ArrayList<Mensagem> mensagemCons = sa.consultaMensagnsRecebidas(mensagem.getNome());
                        enviar_mensagem(mensagemCons);
                        break;
                    default:
                        throw new AssertionError();
                }
            } while (saiu == false);
            finalizar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
