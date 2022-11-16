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
    
    /*public void criarCSV(Integer fkServidor, Integer fkMetrica, String valor, String situacao, String componente) throws IOException {
        String[] header = {"fkServidor", "fkMetrica", "dataLeitura", "valor_leitura", "situacao", "componente"};
        //System.out.println(componente);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
        LocalDateTime now = LocalDateTime.now();
        String[] record1 = {String.valueOf(fkServidor), String.valueOf(fkMetrica), dtf.format(now), valor, situacao, componente};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        String caminhoTemp = System.getProperty("java.io.tmpdir") + "/insert.csv";
        caminhoTemp = caminhoTemp.replace("\\", "/");
        //System.out.println(caminhoTemp);
        try (CSVWriter writer = new CSVWriter(new FileWriter(System.getProperty("java.io.tmpdir") + "/insert.csv"))) {
            writer.writeAll(list);
        }
        String esquel = " LOAD DATA LOCAL INFILE '" + caminhoTemp
                + "' INTO TABLE Leitura "
                + " FIELDS TERMINATED BY \',\' ENCLOSED BY \'\"'"
                + " LINES TERMINATED BY \'\\n\'";
        StopWatch timer = new StopWatch();
        timer.start();
        con.update(esquel);
        timer.stop();

    }*/
    public void inserirLeitura(List<Leitura> leituras, LocalDateTime registro) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
        LocalDateTime now = LocalDateTime.now();
        ;
        if ((ultimoRegistro.getSecond() - now.getSecond()) >= 10 ) {
            for (Leitura leitura : leituras) {
                con.update("INSERT INTO Leitura VALUES (?, ?, ?, ?, ?, 'Disco')", leitura.getFkServidor(), leitura.getFkMetrica(), dtf.format(now), leitura.getValor_leitura(), leitura.getSituacao());
            }
            ultimoRegistro = now;
        }
    }

    public LocalDateTime getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(LocalDateTime ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }
}
