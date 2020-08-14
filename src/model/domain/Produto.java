package model.domain;

import java.util.Date;
import java.util.List;

public class Produto extends EntidadeDominio {

	private int codigo;

	private String nomeProduto;

	private boolean ativo;

	private Date dtCadastro;
	
	private Date dtAlteracao;
	
	private LinhaProduto linhaProduto;
	
	private FichaTecnica fichaTecnica;
	
	private ItemDeCompra itemDeCompra;

	private ItemEmEstoque itemEmEstoque;

	public Produto(String nomeProduto, FichaTecnica fichaTecnica, LinhaProduto linhaProduto) {
		this.nomeProduto = nomeProduto;
		this.fichaTecnica = fichaTecnica;
		this.linhaProduto = linhaProduto;
	}
	
	public Produto(int codigo, String nomeProduto, FichaTecnica fichaTecnica, LinhaProduto linhaProduto) {
		this.codigo = codigo;
		this.nomeProduto = nomeProduto;
		this.fichaTecnica = fichaTecnica;
		this.linhaProduto = linhaProduto;
	}
	
	public Produto(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public LinhaProduto getLinhaProduto() {
		return linhaProduto;
	}

	public void setLinhaProduto(LinhaProduto linhaProduto) {
		this.linhaProduto = linhaProduto;
	}

	public FichaTecnica getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(FichaTecnica fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
	
	public ItemDeCompra getItemDeCompra() {
		return itemDeCompra;
	}

	public void setItemDeCompra(ItemDeCompra itemDeCompra) {
		this.itemDeCompra = itemDeCompra;
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
