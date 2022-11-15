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
public class ParametroDao {
    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();
   
    public List<Parametro> getParametros(Integer fkServidor) {
        List<Parametro> parametros = con.query("SELECT fk_metrica FROM Parametro where fk_Servidor = ?", new BeanPropertyRowMapper(Parametro.class), fkServidor);
        return parametros;
    }

}
