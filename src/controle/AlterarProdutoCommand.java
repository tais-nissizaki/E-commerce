package controle;

import model.domain.EntidadeDominio;

public class AlterarProdutoCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.alterar(entidade);
	}

}
