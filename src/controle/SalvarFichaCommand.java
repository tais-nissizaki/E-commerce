package controle;

import model.domain.EntidadeDominio;

public class SalvarFichaCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.salvar(entidade);
	}

}
