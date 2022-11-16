/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Leitura {
    private Integer fkServidor;
    private Integer fkMetrica;
    private String valor_leitura;
    private String situacao;
    private String componente;

    public Leitura(Integer fkServidor, Integer fkMetrica, String valor_leitura, String situacao, String componente) {
        this.fkServidor = fkServidor;
        this.fkMetrica = fkMetrica;
        this.valor_leitura = valor_leitura;
        this.situacao = situacao;
        this.componente = componente;
    }
    
    
    
    //private List<Leitura> leituras;

    public Integer getFkServidor() {
        return fkServidor;
    }

    public void setFkServidor(Integer fkServidor) {
        this.fkServidor = fkServidor;
    }

    public Integer getFkMetrica() {
        return fkMetrica;
    }

    public void setFkMetrica(Integer fkMetrica) {
        this.fkMetrica = fkMetrica;
    }

    public String getValor_leitura() {
        return valor_leitura;
    }

    public void setValor_leitura(String valor_leitura) {
        this.valor_leitura = valor_leitura;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }
}
