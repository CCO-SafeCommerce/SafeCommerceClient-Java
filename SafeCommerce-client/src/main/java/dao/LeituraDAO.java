package dao;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

public class LeituraDAO {

    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    private LocalDateTime ultimoRegistro;
  
    public void inserirLeitura(List<Leitura> leituras) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
        LocalDateTime now = LocalDateTime.now();
        String dataFormatada = dtf.format(now);
        dataFormatada = dataFormatada.substring(0,dataFormatada.length()-5);
        
            for (Leitura leitura : leituras) {
                con.update("INSERT INTO Leitura VALUES (?, ?, ?, ?, ?, ?)", leitura.getFkServidor(), leitura.getFkMetrica(), dataFormatada, leitura.getValor_leitura(), leitura.getSituacao(), leitura.getComponente());
            }
            ultimoRegistro = now;
        }

    public LocalDateTime getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(LocalDateTime ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }
}
