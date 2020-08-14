package controle;

import model.domain.EntidadeDominio;

public class InativarProdutoCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.inativar(entidade);
	}

}
