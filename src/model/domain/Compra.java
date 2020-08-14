package model.domain;

import java.util.Date;

public class Compra {
	
	private String comprador;
	
	private double valorTotal;
	
	private Date dtCompra;

	public Compra(String comprador, double valorTotal) {
		this.comprador = comprador;
		this.valorTotal = valorTotal;
	}

	public String getComprador() {
		return comprador;
	}

	public void setComprador(String comprador) {
		this.comprador = comprador;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDtCompra() {
		return dtCompra;
	}

	public void setDtCompra(Date dtCompra) {
		this.dtCompra = dtCompra;
	}
	
}
