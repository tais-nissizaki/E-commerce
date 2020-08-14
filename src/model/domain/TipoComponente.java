package model.domain;

public class TipoComponente {

	private String nomeTipoComponente;
	
	public static final String BASICO = "BASICO";
	public static final String PRIMARIO = "PRIMARIO";
	public static final String SECUNDARIO = "SECUNDARIO";

	public TipoComponente(String nomeTipoComponente) {
		this.nomeTipoComponente = nomeTipoComponente;
	}

	public String getNomeTipoComponente() {
		return nomeTipoComponente;
	}

	public void setNomeTipoComponente(String nomeTipoComponente) {
		this.nomeTipoComponente = nomeTipoComponente;
	}

}
