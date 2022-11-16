package dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProcessoDAO {

    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    private LocalDateTime ultimoRegistro;
    
    public void inserirProcesso(List<Processo> processos){
        for(Processo processo : processos){
            con.update("INSERT INTO Processo VALUES (?, ?, ?, ?, ?, ?, ?, ?)", processos);
        }
    }

}
