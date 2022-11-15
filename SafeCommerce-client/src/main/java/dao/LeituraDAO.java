package dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeituraDAO {
    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();
    
    public void criarCSV(List<Leitura> leituras) {
        
    }
    
    public void inserirLeitura(Leitura leituras) {
        
    }
}
