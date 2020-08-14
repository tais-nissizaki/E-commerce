package model.domain;

import java.util.Date;

public class ItemEmEstoque {

	private int quantidadeProduto;

	private Date dtEntrada;
	
	private int quantidadeAcessorio;

	public ItemEmEstoque(int qtdProduto, Date dtEntrada, int qtdAcessorio) {
		this.quantidadeProduto = qtdProduto;
		this.dtEntrada = dtEntrada;
		this.quantidadeAcessorio = qtdAcessorio;
	}

	public int getQuantidadeAcessorio() {
		return quantidadeAcessorio;
	}

	public void setQuantidadeAcessorio(int quantidadeAcessorio) {
		this.quantidadeAcessorio = quantidadeAcessorio;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(int quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Date getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

}
