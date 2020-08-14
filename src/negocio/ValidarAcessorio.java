package negocio;

import model.domain.Acessorio;
import model.domain.EntidadeDominio;

public class ValidarAcessorio implements IStrategy {
	@Override
	public String processar(EntidadeDominio entidade) {
		Acessorio acessorio = (Acessorio) entidade;
		String mensagem = "";
		if(acessorio.getNomeAcessorio() == null || acessorio.getNomeAcessorio().trim() == "") {
			mensagem += "O nome do acessório é obrigatório.";	
		}
		if(acessorio.getDescricaoAcessorio() == null || acessorio.getDescricaoAcessorio().trim() == "") {
			mensagem += "A descrição do acessório é obrigatória.";	
		}
		return mensagem;
	}
}
