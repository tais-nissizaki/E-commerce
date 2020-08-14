package controle;

import model.domain.EntidadeDominio;

public interface ICommand {
	
	public Object executar(EntidadeDominio entidade);
}
