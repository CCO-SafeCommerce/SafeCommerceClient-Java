    package dao;

    import com.opencsv.CSVWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.time.LocalDateTime;
    import java.time.ZoneId;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Date;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.util.StopWatch;

    public class LeituraDAO {
        private LocalDateTime ultimoRegistro;
        private ConexaoAzure conexao;
        private Statement statement;

        public LeituraDAO() {
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
    */
        public void inserirLeitura(List<Leitura> leituras) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

            Date date = java.sql.Timestamp.valueOf(now);
            String dataFormatada = date.toString().split("\\.")[0];

            for (Leitura leitura : leituras) {
                String sql = "INSERT INTO Leitura VALUES ("+leitura.getFkServidor()+", "+
                        leitura.getFkMetrica()+", '"
                        +dataFormatada+"', '"
                        +leitura.getValor_leitura()+"', '"
                        +leitura.getSituacao()+"', '"
                        +leitura.getComponente()+"')";
                System.out.println(sql);
                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    System.out.println("Erro ao inserir leitura");
                    throw new RuntimeException(e);

                }

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
