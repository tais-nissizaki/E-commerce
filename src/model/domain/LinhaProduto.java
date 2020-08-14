package model.domain;

import java.util.List;

public class LinhaProduto extends EntidadeDominio {

	private String nomeLinhaProduto;

	private List<Acessorio> acessorios;

	public LinhaProduto(String nomeLinhaProduto, List<Acessorio> acessorios) {
		this.nomeLinhaProduto = nomeLinhaProduto;
		this.acessorios = acessorios;
	}
	
	public LinhaProduto(String nomeLinhaProduto) {
		this.nomeLinhaProduto = nomeLinhaProduto;
	}

	public String getNomeLinhaProduto() {
		return nomeLinhaProduto;
	}

	public void setNomeLinhaProduto(String nomeLinhaProduto) {
		this.nomeLinhaProduto = nomeLinhaProduto;
	}

	public List<Acessorio> getAcessorio() {
		return acessorios;
	}

	public void setAcessorio(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

}
