/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Leitura {
     Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();
    
    private double valorLeitura;

    public double getValorLeitura() {
        return valorLeitura;
    }

    public void setValorLeitura(double valorLeitura) {
        this.valorLeitura = valorLeitura;
    }
    
    
    
    
    public List<Leitura> getCpu(){
        List<Leitura> leiturasCpu = con.query("SELECT valor_leitura FROM Leitura where fkMetrica = '1' ", new BeanPropertyRowMapper(Leitura.class));
        return leiturasCpu;
    }
    
    public List<Leitura> getRam(){
        List<Leitura> leiturasRam = con.query("SELECT valor_leitura FROM Leitura where fkMetrica = '6' ", new BeanPropertyRowMapper(Leitura.class));
        return leiturasRam;
    }
    
    public List<Leitura> getDisco(){
        List<Leitura> leiturasDisco = con.query("SELECT valor_leitura FROM Leitura where fkMetrica = '7' ", new BeanPropertyRowMapper(Leitura.class));
        return leiturasDisco;
    }
}
