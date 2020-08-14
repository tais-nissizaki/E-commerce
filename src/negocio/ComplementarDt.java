package negocio;

import java.util.Date;

import model.domain.EntidadeDominio;
import model.domain.Produto;

public class ComplementarDt implements IStrategy{
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Produto produto = (Produto) entidade;
		if(produto.getId() > 0) {
			entidade.setDtAlteracao(new Date());
		} else {
			entidade.setDtCadastro(new Date());
		}
		return null;
	}

}
