package VO;

public class UsuarioVO extends PadraoVO {
    private String nome;
    private String login;
    private String senha;
    private int idPerfilGasto;
    private float saldo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIdPerfilGasto(int idPerfilGasto) {
        this.idPerfilGasto = idPerfilGasto;
    }

    public int getIdPerfilGasto() {
        return idPerfilGasto;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
}
