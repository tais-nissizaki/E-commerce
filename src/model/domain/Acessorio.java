package model.domain;

public class Acessorio extends EntidadeDominio {

	private String nomeAcessorio;

	private String descricaoAcessorio;
	
	private ItemEmEstoque itemEmEstoque;
	
	private boolean ativo; 

	public Acessorio(String nomeAcessorio, String descricaoAcessorio) {
		this.nomeAcessorio = nomeAcessorio;
		this.descricaoAcessorio = descricaoAcessorio;
	}

	public String getNomeAcessorio() {
		return nomeAcessorio;
	}

	public void setNomeAcessorio(String nomeAcessorio) {
		this.nomeAcessorio = nomeAcessorio;
	}

	public String getDescricaoAcessorio() {
		return descricaoAcessorio;
	}

	public void setDescricaoAcessorio(String descricaoAcessorio) {
		this.descricaoAcessorio = descricaoAcessorio;
	}

	public ItemEmEstoque getItemEmEstoque() {
		return itemEmEstoque;
	}

	public void setItemEmEstoque(ItemEmEstoque itemEmEstoque) {
		this.itemEmEstoque = itemEmEstoque;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
