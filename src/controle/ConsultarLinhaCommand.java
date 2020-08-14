package controle;

import model.domain.EntidadeDominio;

public class ConsultarLinhaCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.consultar(entidade);
	}

}
