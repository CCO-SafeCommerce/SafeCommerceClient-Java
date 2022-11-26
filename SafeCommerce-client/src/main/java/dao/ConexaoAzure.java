package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoAzure {

        private Connection connection;
        private String SERVER = "jdbc:sqlserver://grupo0-1cco.database.windows.net:1433;";
        private String DATABASE = "safecommmerce;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        private String USER = "grupo01cco;";
        private String PASSWORD = "1cco#grupo4;";

        public ConexaoAzure() throws SQLException {
                 connection = DriverManager.getConnection(SERVER + "database=" + DATABASE + "user=" + USER + "password=" + PASSWORD + ";");

        }

        public Connection getConnection() {

            return connection;

        }

        public Statement getStatement() throws SQLException {
            return connection.createStatement();
        }
}
