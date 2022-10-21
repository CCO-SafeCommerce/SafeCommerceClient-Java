package dao;

public class Usuario {
	private Integer idUsuario;
	private String nome;
	private String email;
	private Integer fkUsuario;
	private Integer fkEmpresa;
	private String senha;
	
	UsuarioDAO dao = new UsuarioDAO();
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
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
	public Integer getFkUsuario() {
		return fkUsuario;
	}
	public void setFkUsuario(Integer fkUsuario) {
		this.fkUsuario = fkUsuario;
	}
	public Integer getFkEmpresa() {
		return fkEmpresa;
	}
	public void setFkEmpresa(Integer fkEmpresa) {
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
		 return String.format("\nID: %d\nNOME: %s\n",
	                this.idUsuario, this.nome);
	 }
	
}
