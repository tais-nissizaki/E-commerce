package model.domain;

import java.util.ArrayList;
import java.util.List;

public class FichaTecnica extends EntidadeDominio {

	private String nomeFicha;

	private String descricao;
	
	private String observacoes;

	private Categoria categoria;

	private Subcategoria subcategoria;
	
	private List<Componente> componentes; 

	private List<Acessorio> acessorios;
	
	private boolean ativo;

	public FichaTecnica(String nomeFicha, String descricao, Categoria categoria, Subcategoria subcategoria, Componente componenteBasico, Componente componentePrimario, Componente componenteSecundario) {
		this.nomeFicha = nomeFicha;
		this.descricao = descricao; 
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.componentes = new ArrayList<>();
		this.componentes.add(componenteBasico);
		this.componentes.add(componentePrimario);
		this.componentes.add(componenteSecundario);
			
	}
	
	public FichaTecnica(int id) {
		setId(id);
			
	}
	
	public FichaTecnica(String nomeFicha, String descricao, String observacoes, Categoria categoria, Subcategoria subcategoria, Componente componenteBasico, Componente componentePrimario, Componente componenteSecundario) {
		this.nomeFicha = nomeFicha;
		this.descricao = descricao; 
		this.observacoes = observacoes;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.componentes = new ArrayList<>();
		this.componentes.add(componenteBasico);
		this.componentes.add(componentePrimario);
		this.componentes.add(componenteSecundario);
			
	}

	public String getNomeFicha() {
		return nomeFicha;
	}

	public void setNomeFicha(String nomeFicha) {
		this.nomeFicha = nomeFicha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricaoFicha) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}
	
	public void adicionarAcessorio(Acessorio acessorio) {
		if(this.acessorios==null) {
			this.acessorios = new ArrayList<>();
		}
		this.acessorios.add(acessorio);
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
