package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {

    private JdbcTemplate connection;

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setUrl("jdbc:mysql://localhost:3306/safecommerce?useTimezone=true&serverTimezone=America/Sao_Paulo&&allowLoadLocalInfile=true&useServerPrepStmts=false&&rewriteBatchedStatements=true");

        dataSource.setUsername("aluno");

        dataSource.setPassword("sptech");

        this.connection = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConnection() {

        return connection;

    }
}
