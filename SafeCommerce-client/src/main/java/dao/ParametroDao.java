/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author I
 */
public class ParametroDao {
    ConexaoAzure conexao;
    Statement statement;
    {
        try {
            conexao = new ConexaoAzure();
           statement = conexao.getStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    public List<Parametro> getParametros(Integer fkServidor) {
        List<Parametro> parametros = con.query("SELECT fk_metrica FROM Parametro where fk_Servidor = ?", new BeanPropertyRowMapper(Parametro.class), fkServidor);
        return parametros;
    }
    */
    public List<Parametro> getParametros(Integer fkServidor) throws SQLException {
        List<Parametro> parametros = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT fk_metrica FROM Parametro where fk_Servidor = " + fkServidor);
        while(rs.next()){
            parametros.add(new Parametro(rs.getInt("fk_metrica")));
        }
        return parametros;
    }

}
