package dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServidorDAO {

    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    public Servidor getServidorByMac(String enderecoMac) {
        List<Servidor> servidores = con.query("SELECT idServidor, enderecoMac, so FROM Servidor WHERE enderecoMac = ?", new BeanPropertyRowMapper(Servidor.class), enderecoMac);
        if (servidores.size() > 0) {
            return servidores.get(0);
        }
        return null;
    }
}
