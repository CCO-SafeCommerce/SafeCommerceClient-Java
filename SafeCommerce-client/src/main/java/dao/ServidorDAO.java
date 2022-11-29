package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServidorDAO {
    /*
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
    }*/
    private ConexaoAzure conexao;
   private Statement statement;
   public ServidorDAO() {

       try {
           conexao = new ConexaoAzure();
           statement = conexao.getStatement();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
    public Boolean isServidorCadastrado(String enderecoMac) {
        List<Servidor> servidores = new ArrayList<>();

        try {
            String sql = "SELECT idServidor FROM visaoGeralServidores WHERE enderecoMac = '" + enderecoMac + "'";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Servidor servidor = new Servidor();
                servidor.setIdServidor(rs.getInt("idServidor"));
                servidores.add(servidor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return !servidores.isEmpty();
    }

    public void cadastrarServidor(String modelo, String so, String enderecoMac, Integer fkEmpresa, String ipServidor) {
        try {
            String sql = "INSERT INTO Servidor (modelo, so, enderecoMac, fkEmpresa, ipServidor) VALUES ('" + modelo + "','" + so + "','" + enderecoMac + "','" + fkEmpresa+ "','" +ipServidor + "')";
            statement.executeUpdate(sql);
            //"INSERT INTO Parametro VALUES ((SELECT idServidor FROM Servidor WHERE enderecoMac = '{mac_add}'), 2), ((SELECT idServidor FROM Servidor WHERE enderecoMac = '{mac_add}'), 5), ((SELECT idServidor FROM Servidor WHERE enderecoMac = '{mac_add}'), 7);")
            String sqlValoresDefault = "INSERT INTO Parametro (fk_Servidor, fk_Metrica) VALUES ((SELECT idServidor FROM Servidor WHERE enderecoMac = '" + enderecoMac + "'), 2), ((SELECT idServidor FROM Servidor WHERE enderecoMac = '" + enderecoMac + "'), 5), ((SELECT idServidor FROM Servidor WHERE enderecoMac = '" + enderecoMac + "'), 7)";
            statement.executeUpdate(sqlValoresDefault);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Servidor getServidorByMac(String enderecoMac) {
        List<Servidor> servidores = new ArrayList<>();
        try {
            String sql = "SELECT idServidor, ultimoRegistro FROM visaoGeralServidores WHERE enderecoMac = '" + enderecoMac + "'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Servidor servidor = new Servidor();
                servidor.setIdServidor(rs.getInt("idServidor"));
                String date = rs.getString("ultimoRegistro");
                OffsetDateTime odt = OffsetDateTime.parse ( "2016-08-18 14:27:15.103+02" , DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" ) ) ;
                LocalDateTime dateTime = odt.toLocalDateTime();
                servidor.setUltimoRegistro(dateTime);
                servidores.add(servidor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!servidores.isEmpty()) {
            return servidores.get(0);
        }

        return null;
    }
 }
