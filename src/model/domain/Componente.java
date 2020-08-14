package model.domain;

public class Componente {

	private String nomeComponente;

	private TipoComponente tipoComponente;

	public Componente(String nomeComponente, TipoComponente tipoComponente) {
		this.nomeComponente = nomeComponente;
		this.tipoComponente = tipoComponente;
	}

	public String getNomeComponente() {
		return nomeComponente;
	}

	public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	}

	public TipoComponente getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(TipoComponente tipoComponente) {
		this.tipoComponente = tipoComponente;
	}
}
