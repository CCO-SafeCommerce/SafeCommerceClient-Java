package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProcessoDAO {
    private LocalDateTime ultimoRegistro;
    private ConexaoAzure conexao;
    private Statement statement;

    public ProcessoDAO() {
        try {
            conexao = new ConexaoAzure();
            statement = conexao.getStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
/*
    Conexao connection = new Conexao();
    JdbcTemplate con = connection.getConnection();



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
*/
public void inserirProcesso(List<Process> processos){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
    LocalDateTime now = LocalDateTime.now();
    //localdatetime to date
    Date date = java.sql.Timestamp.valueOf(now);
//split date in . and get the first part
    String dataFormatada = date.toString().split("\\.")[0];


    //if((now.getSecond() - ultimoRegistro) >= 10){
    for(Process processo : processos){

            String sql = "INSERT INTO Processo VALUES ("+processo.getFkServidor()+", "+
                    processo.getPid()+", '"
                    +dataFormatada+"', '"
                    +processo.getNome()+"', "
                    +processo.getUsoCpu()+", '"
                    +processo.situacaoCpu+"', "
                    +processo.getUsoRam()+", '"
                    +processo.getSituacaoRam()+"')";
        System.out.println(sql);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao inserir processo");
            throw new RuntimeException(e);

        }
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
