package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AplicacaoDAO {
    private ConexaoAzure conexao;
    private Statement statement;
    
    public AplicacaoDAO() {
       try {
           conexao = new ConexaoAzure();
           statement = conexao.getStatement();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
    
    public List<Aplicacao> getAplicacoes(Integer fkServidor) {
        List<Aplicacao> aplicacoes = new ArrayList();
        
        try {
            String sql = "SELECT nome, porta FROM Aplicacao WHERE fkServidor = " + fkServidor + ";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Aplicacao app = new Aplicacao();
                app.setNome(rs.getString("nome"));
                app.setPorta(rs.getInt("porta"));
                aplicacoes.add(app);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return aplicacoes;
    }
}
