package controle;

import model.domain.EntidadeDominio;

public class ConsultarProdutoCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.consultar(entidade);
	}

}
