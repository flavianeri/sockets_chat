package aula_03_sockets_multicliente;

import dao.ClienteDAO;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Cliente {

    private Socket soquete;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private String mensagens;
    private Cliente NovoCliente = null;
    private ArrayList<String> Clientesconectados = new ArrayList<>();
    private ArrayList<String> MensagensEnviadas = new ArrayList<>();
    private ArrayList<Mensagem> MensagensRecebidas = new ArrayList<>();
    ClienteDAO Sa = new ClienteDAO();

    public Cliente(String endereco, int porta) throws Exception {
        super();
        this.soquete = new Socket(endereco, porta);
        this.saida = new ObjectOutputStream(this.soquete.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete.getInputStream());
    }

    public Cliente() {

    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    public void finalizar() throws IOException {
        this.soquete.close();
    }

    public static void main(String[] args) throws Exception {

    }

    //opcao 1
    public void conecta(String nome) {
        try {
            System.out.println("x");
            this.NovoCliente = new Cliente("10.90.37.36", 15500);
            System.out.println(this.NovoCliente);
            this.NovoCliente.enviar_mensagem(new Mensagem(nome, "1"));
            System.out.println("C");
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //opcao 2
    public ArrayList<Mensagem> listarMensagensDiretas(Mensagem mensagem) {
        try {
            enviar_mensagem(mensagem);
            this.MensagensRecebidas = (ArrayList<Mensagem>) NovoCliente.receber_mensagem();
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.MensagensRecebidas;
    }

    //opcao 3
    public ArrayList<String> listaClientes(String nome) {
        try {
            System.out.println(this.NovoCliente);
            this.NovoCliente.enviar_mensagem(new Mensagem(nome, "4"));
            System.out.println(NovoCliente);
            Clientesconectados = (ArrayList<String>) this.NovoCliente.receber_mensagem();
            if (!Clientesconectados.contains(nome)) {
                Clientesconectados.add(nome);
            }
            System.out.println(Clientesconectados);
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Clientesconectados;
    }

    //opcao 4
    public ArrayList<String> enviarSMSservidor(Mensagem mensagem) {
        try {
            enviar_mensagem(mensagem);
            System.out.println(MensagensEnviadas);
            return MensagensEnviadas;
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //opcao 5
    public void enviaSMSCliente(String nome, String qualCliente, String mensagem) {

        try {
            String nomes = nome + "/" + qualCliente;
            mensagem = mensagem + "/" + "6";
            Mensagem mensagemEnviada = new Mensagem(nomes, mensagem);
            enviar_mensagem(mensagemEnviada);
            System.out.println(mensagemEnviada);
            System.out.println(qualCliente);

        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //opcap 6
    public ArrayList<Mensagem> listarSMSrecebidos(String nome) {
        ArrayList<Mensagem> mensagensRecebidas = new ArrayList<>();
        try {
            Mensagem mensagem = new Mensagem(nome, "7");
            enviar_mensagem(mensagem);
            mensagensRecebidas = (ArrayList<Mensagem>) NovoCliente.receber_mensagem();
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensagensRecebidas;
    }

//    public void executaCliente(int opcao, String nome, String texto) {
//
//        while (true) {
////            System.out.println("O que vc deseja fazer? 1- Conetctar; 2- Sair; "
////                    + "3- Listar mensagens;4- Listar clientes conectados; 5- Enviar mensagem para o servidor, "
////                    + "6- Enviar para um cliente especifico, 7- Listar mensagens recebidas;");
//            String mensagemDoCliente;
//            String qualCliente;
////            if (conectado == false && opcao != 1) {
////                try {
////                    JOptionPane.showMessageDialog(null, "Vc precisa se conectar primeiro. Vamos fazer isso por você e o comando que você escolheu será executado!");
////                    this.NovoCliente = new Cliente("10.90.37.93", 15500);
////                    conectado = true;
////                } catch (Exception ex) {
////                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
////                }
////            } else if (opcao == 1 && conectado == false) {
////                try {
////                    this.NovoCliente = new Cliente("10.90.37.93", 15500);
////                    conectado = true;
////                } catch (Exception ex) {
////                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
////                }
////
////            } else if (conectado == true && opcao == 1) {
////                System.out.println("Você já está conectado!");
////            }
//
////            if (conectado == true) {
//            try {
//                String opcaoString = Integer.toString(opcao);
//                NovoCliente.enviar_mensagem(new Mensagem(nome, opcaoString));
//
//                if (opcao == 0) {
//                    try {
//                        System.out.println(NovoCliente.receber_mensagem());
//                        mensagemDoCliente = JOptionPane.showInputDialog("Escreva sua mensagem");
//                        NovoCliente.enviar_mensagem(new Mensagem("yara", mensagemDoCliente));
//                    } catch (Exception ex) {
//                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//                if (opcao == 3) {
//
//                    try {
//                        Clientesconectados = (ArrayList<String>) NovoCliente.receber_mensagem();
//                        System.out.println(Clientesconectados);
//                    } catch (Exception ex) {
//                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if (opcao == 2) {
//                    try {
//                        System.out.println(NovoCliente.receber_mensagem());
//                    } catch (Exception ex) {
//                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if (opcao == 1) {
//                    try {
//                        NovoCliente.finalizar();
//                        System.exit(0);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//                if (opcao == 5) {
//                    try {
//                        String mensagemrecebida = (String) NovoCliente.receber_mensagem();
//                        System.out.println(mensagemrecebida);
//                        qualCliente = JOptionPane.showInputDialog("Qual Cliente?");
//                        mensagemDoCliente = JOptionPane.showInputDialog("Qual Mnesagem?");
//                        NovoCliente.enviar_mensagem(new Mensagem(qualCliente, mensagemDoCliente));
//                    } catch (Exception ex) {
//                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if (opcao == 6) {
//                    MensagensEnviadas = (ArrayList<String>) NovoCliente.receber_mensagem();
//                    System.out.println(MensagensEnviadas);
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
//    //}
}
