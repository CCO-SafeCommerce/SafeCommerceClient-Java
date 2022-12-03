package dao;

public class Aplicacao {
    private String nome;
    private Integer porta;

    public Aplicacao() {
    }
    
    public Aplicacao(String nome, Integer porta) {
        this.nome = nome;
        this.porta = porta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }
    
    public String getComponente() {
        return this.nome + ":" + this.porta;
    }
}
