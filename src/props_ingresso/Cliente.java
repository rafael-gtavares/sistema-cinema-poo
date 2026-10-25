package props_ingresso;

public class Cliente {
	private String email, senha, role;
	
	public Cliente(String email, String senha) {
		this.email = email;
		this.senha = senha;
		this.role = "usuario";
	}
	
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public String getRole() {
		return role;
	}
}
