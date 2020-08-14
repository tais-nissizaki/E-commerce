package controle;

import model.domain.EntidadeDominio;

public class SalvarProdutoCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.salvar(entidade);
	}

}
