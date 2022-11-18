package dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProcessoDAO {

    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();

    private LocalDateTime ultimoRegistro;
    
    public void inserirProcesso(List<Process> processos){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn");
        LocalDateTime now = LocalDateTime.now();
        String dataFormatada = dtf.format(now);
        dataFormatada = dataFormatada.substring(0,dataFormatada.length()-6);

        
        //if((now.getSecond() - ultimoRegistro) >= 10){
            for(Process processo : processos){
                con.update("INSERT INTO Processo VALUES "+ "(?, ?, ?, ?, ?, ?, ?, ?)", processo.getFkServidor(),
                        processo.getPid(), dataFormatada, processo.getNome(),
                        processo.getUsoCpu(), processo.situacaoCpu,
                        processo.getUsoRam(), processo.getSituacaoRam());
            }
            ultimoRegistro = now;
        //}
    }

    public LocalDateTime getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(LocalDateTime ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }
    
    

}
