package model.domain;

public class Usuario extends EntidadeDominio {

	private String login;

	private String senha;

	private String confirmaSenha;
	
	private boolean ativo;

	public Usuario(String login, String senha, String confirmaSenha) {
		this.login = login;
		this.senha = senha;
		this.confirmaSenha = confirmaSenha;
	}
	
	public Usuario(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmacaoSenha) {
		this.confirmaSenha = confirmacaoSenha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
