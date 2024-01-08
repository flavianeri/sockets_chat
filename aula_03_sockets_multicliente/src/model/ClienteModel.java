package model;


public class ClienteModel {
    
    private String nome;
    private String Senha;
    private String ip;

    public ClienteModel(String nome, String Senha, String ip) {
        this.nome = nome;
        this.Senha = Senha;
        this.ip = ip;
    }
   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public ClienteModel( String nome) {
        this.nome = nome;
    }

    public ClienteModel( ) {

    }

}
