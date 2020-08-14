package model.domain;

public class ItemDeCompra {
	
	private int quantidade;
	
	private double valorDeCompra;
	
	private Compra compra;
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public ItemDeCompra(int quantidade, double valorDeCompra) {
		this.quantidade = quantidade;
		this.valorDeCompra = valorDeCompra;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorDeCompra() {
		return valorDeCompra;
	}

	public void setValorDeCompra(double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}
	
	
}
