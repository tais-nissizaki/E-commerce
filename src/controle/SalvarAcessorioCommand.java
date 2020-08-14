package controle;

import model.domain.EntidadeDominio;

public class SalvarAcessorioCommand extends AbsCommand {
	
	@Override
	public Object executar(EntidadeDominio entidade) {
		
		return fachada.salvar(entidade);
	}

}
