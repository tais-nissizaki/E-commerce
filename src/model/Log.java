package model;

import java.util.HashMap;

public class Log {

	private String descricao;

	public void gerar(HashMap<String, String> atributos) {
		System.out.println("Log gerado!");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
