package dao;

public class Usuario {
	private String idUsuario;
	private String nome;
	private String email;
	private String fkUsuario;
	private String fkEmpresa;
	private String senha;
	
	UsuarioDAO dao = new UsuarioDAO();
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFkUsuario() {
		return fkUsuario;
	}
	public void setFkUsuario(String fkUsuario) {
		this.fkUsuario = fkUsuario;
	}
	public String getFkEmpresa() {
		return fkEmpresa;
	}
	public void setFkEmpresa(String fkEmpresa) {
		this.fkEmpresa = fkEmpresa;
	}
	public void fazerLogin(String email, String senha) {
		Usuario logado = dao.login(email, senha);
		if(logado == null) {
			System.out.println("Tivemos um probkema");
		}else {
			System.out.println("ok");
		}
		
	}
	
	 @Override
	  public String toString() {
		 return String.format("\nID: %s\nNOME: %s\n",
	                this.idUsuario, this.nome);
	 }
	
}
