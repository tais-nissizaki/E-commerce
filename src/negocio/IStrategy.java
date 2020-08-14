package negocio;

import model.domain.EntidadeDominio;

public interface IStrategy {

	public String processar(EntidadeDominio entidade);
	
}
