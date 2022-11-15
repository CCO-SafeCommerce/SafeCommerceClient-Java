package dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServidorDAO {

    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    public Boolean isServidorCadastrado(String enderecoMac) {
        List<Servidor> servidores = con.query("SELECT idServidor FROM visaoGeralServidores WHERE enderecoMac = ?", new BeanPropertyRowMapper(Servidor.class), enderecoMac);
        
        return !servidores.isEmpty();
    }
    
    public void cadastrarServidor(String modelo, String so, String enderecoMac, Integer fkEmpresa) {
        con.update(
            "INSERT INTO Servidor (modelo, so, enderecoMac, fkEmpresa) VALUES (?,?,?,?)", 
                modelo, so, enderecoMac, fkEmpresa);
    }
    
    public Servidor getServidorByMac(String enderecoMac) {
        List<Servidor> servidores = con.query("SELECT idServidor, ultimoRegistro FROM visaoGeralServidores WHERE enderecoMac = ?", new BeanPropertyRowMapper(Servidor.class), enderecoMac);
        
        if (!servidores.isEmpty()) {
            return servidores.get(0);
        }
        
        return null;
    }
}
