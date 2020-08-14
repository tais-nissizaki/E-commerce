package model.domain;

public class Categoria {

	private String nomeCategoria;

	private Subcategoria subcategoria;

	public Categoria(String nomeCategoria, Subcategoria subcategoria) {
		this.nomeCategoria = nomeCategoria;
		this.subcategoria = subcategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}
}
