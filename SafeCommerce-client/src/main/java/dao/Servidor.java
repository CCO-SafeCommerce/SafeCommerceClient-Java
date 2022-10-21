/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author I
 */
public class Servidor {
    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    private Integer idServidor;
    private String enderecoMac;
    
    public Integer getIdServidor( String enderecoMac){
         System.out.println(enderecoMac);
         List<Servidor> servidores = con.query("SELECT idServidor FROM Servidor WHERE enderecoMac = ?", new BeanPropertyRowMapper(Servidor.class), enderecoMac);
         System.out.println(servidores.get(0).getIdServidor());
         return servidores.get(0).getIdServidor();
    }

    public Integer getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(Integer idServidor) {
        this.idServidor = idServidor;
    }

    public String getEnderecoMac() {
        return enderecoMac;
    }

    public void setEnderecoMac(String enderecoMac) {
        this.enderecoMac = enderecoMac;
    }
    
    
}
