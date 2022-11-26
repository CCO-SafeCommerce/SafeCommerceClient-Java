/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UsuarioDAO {
	Conexao conexao = new Conexao();
	JdbcTemplate jdbcTemplate = conexao.getConnection();

	public Usuario login(String email, String senha) throws SQLException {
			/*try {

			Usuario registro = jdbcTemplate.queryForObject("select * from Usuario where email = ?",
					new BeanPropertyRowMapper<Usuario>(Usuario.class), email);

			BCrypt.Result result = BCrypt.verifyer().verify(senha.getBytes(StandardCharsets.UTF_8), registro.getSenha().getBytes(StandardCharsets.UTF_8));
			// resultStrict.verified == false
			System.out.println(result);
			if(result.verified) {
				return registro;
			}else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Tivemos um Problema ao realizar login");
			return null;
		}*/
			ConexaoAzure conexao = new ConexaoAzure();
			Statement statement = conexao.getStatement();
			ResultSet rs = statement.executeQuery("select * from Usuario where email = '" + email + "'");
			Usuario registro = new Usuario();
			while(rs.next()){
				registro.setIdUsuario(rs.getInt("idUsuario"));
				registro.setNome(rs.getString("nome"));
				registro.setEmail(rs.getString("email"));
				registro.setSenha(rs.getString("senha"));
				registro.setFkUsuario(rs.getInt("fkUsuario"));
				registro.setFkEmpresa(rs.getInt("fkEmpresa"));
			}
			BCrypt.Result result = BCrypt.verifyer().verify(senha.getBytes(StandardCharsets.UTF_8), registro.getSenha().getBytes(StandardCharsets.UTF_8));
			// resultStrict.verified == false
			System.out.println(result);
			if(result.verified) {
				return registro;
			}else {
				return null;
			}


	}

}
