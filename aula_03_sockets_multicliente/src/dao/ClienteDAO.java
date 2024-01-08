package dao;

import aula_03_sockets_multicliente.Conexao;
import aula_03_sockets_multicliente.Mensagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ClienteModel;

public class ClienteDAO {

    public boolean cadastrarClinete(ClienteModel dados) {

        Connection c = Conexao.obterConexao();

        String sql = "INSERT INTO sockets_fy.cadastro (nome, senha, ip)"
                + " VALUES (?, ?, ?)";

        try {
            PreparedStatement insercao = c.prepareStatement(sql);

            insercao.setString(1, dados.getNome());
            insercao.setString(2, dados.getSenha());
            insercao.setString(3, dados.getIp());
            insercao.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void deletaBanco(String nome) {

        Connection c = Conexao.obterConexao();

        String sql = "DELETE FROM sockets_fy.cliente WHERE nome = '" + nome + "'";

        try (PreparedStatement insercao = c.prepareStatement(sql)) {

            insercao.execute();
            
        } catch (SQLException ex) {
            System.out.println("Erro ao apagar IP" + ex.getMessage());
        }
    }

    public void gravarCliente(Mensagem dados, String ip) {
        try {
            Connection c = Conexao.obterConexao();
            String sql = "INSERT INTO sockets_fy.cliente(nome, ip) "
                    + " VALUES (?, ?);";
            PreparedStatement insercao = c.prepareStatement(sql);
            insercao.setString(1, dados.getNome());
            insercao.setString(2, ip);
            insercao.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gravaMensagem(Mensagem dados) {
        try {
            Connection c = Conexao.obterConexao();
            String sql = "INSERT INTO sockets_fy.mensagem(texto, nome) "
                    + " VALUES (?, ?);";
            PreparedStatement insercao = c.prepareStatement(sql);
            insercao.setString(1, dados.getTexto());
            insercao.setString(2, dados.getNome());
            insercao.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Mensagem> consultaMensagnsRecebidas(String nome) {
        try {
            ArrayList<Mensagem> retorno = new ArrayList<>();
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM sockets_fy.mensagemprivada WHERE nomerecebeu = ?";

            PreparedStatement consulta = c.prepareStatement(sql);
            consulta.setString(1, nome);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                Mensagem atual = new Mensagem(null, null);
                atual.setNome(resultado.getString("nomemandou"));
                atual.setTexto(resultado.getString("texto"));
                retorno.add(atual);
            }
            return retorno;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<Mensagem> consultaMensagens() {
        try {
            ArrayList<Mensagem> retorno = new ArrayList<>();
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM sockets_fy.mensagem";

            PreparedStatement consulta = c.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                Mensagem atual = new Mensagem(null, null);
                atual.setNome(resultado.getString("nome"));
                atual.setTexto(resultado.getString("texto"));
                retorno.add(atual);
            }
            return retorno;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public boolean consultaCliente(String nome) {
        ArrayList<String> clientes = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM sockets_fy.cliente";
            PreparedStatement consulta = c.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String nomes = resultado.getString("nome");
                clientes.add(nomes);
            }

            if (clientes.contains(nome)) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
        }

        return true;
    }

    public ArrayList<String> consultaTodosClientes() {
        ArrayList<String> clientes = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM sockets_fy.cliente";
            PreparedStatement consulta = c.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String nomes = resultado.getString("nome");
                clientes.add(nomes);
            }

            return clientes;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
        }

        return clientes;
    }

    public void enviarSMS(String nomeMandou, String nomeRecebeu, String texto) {
        Connection c = Conexao.obterConexao();
        String sql = "INSERT INTO sockets_fy.mensagemprivada(nomeMandou, nomeRecebeu, texto) "
                + " VALUES (?, ?, ?);";
        try (PreparedStatement insercao = c.prepareStatement(sql)) {
            
            insercao.setString(1, nomeMandou);
            insercao.setString(2, nomeRecebeu);
            insercao.setString(3, texto);
            insercao.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
