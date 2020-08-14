package negocio;

import model.domain.Componente;
import model.domain.EntidadeDominio;
import model.domain.FichaTecnica;
import model.domain.TipoComponente;

public class ValidarFichaTecnica implements IStrategy {
	@Override
	public String processar(EntidadeDominio entidade) {
		FichaTecnica fichaTecnica = (FichaTecnica) entidade;
		String mensagem = "";
		if(!validarNome(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarDescricao(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarCategoria(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarSubcategoria(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarComponenteBasico(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarComponentePrimario(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarComponenteSecundario(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}
		if(!validarAcessorios(fichaTecnica)) {
			mensagem += "O nome da Ficha Técnica é obrigatório.";
		}

		return mensagem;
	}
	
	private boolean validarNome(FichaTecnica fichaTecnica) {
		return fichaTecnica.getNomeFicha()!= null && fichaTecnica.getNomeFicha().trim()!="";
	}

	private boolean validarDescricao(FichaTecnica fichaTecnica) {
		return fichaTecnica.getDescricao()!= null && fichaTecnica.getDescricao().trim()!="";
	}

	private boolean validarCategoria(FichaTecnica fichaTecnica) {
		return fichaTecnica.getCategoria()!= null && fichaTecnica.getCategoria().getNomeCategoria()!= null && fichaTecnica.getCategoria().getNomeCategoria().trim()!= "";
	}

	private boolean validarSubcategoria(FichaTecnica fichaTecnica) {
		return fichaTecnica.getSubcategoria()!= null && fichaTecnica.getSubcategoria().getNome()!= null && fichaTecnica.getSubcategoria().getNome().trim()!= "";
	}
	
	private boolean validarComponente(FichaTecnica fichaTecnica, String tipoComponente) {
		if(fichaTecnica.getComponentes() == null) {
			return false;
		}
		for(Componente componente: fichaTecnica.getComponentes()) {
			if(componente.getTipoComponente()!=null && componente.getTipoComponente().getNomeTipoComponente()!= null && componente.getTipoComponente().getNomeTipoComponente()==tipoComponente) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validarComponenteBasico(FichaTecnica fichaTecnica) {
		return validarComponente(fichaTecnica, TipoComponente.BASICO);
	}

	private boolean validarComponentePrimario(FichaTecnica fichaTecnica) {
		return validarComponente(fichaTecnica, TipoComponente.PRIMARIO);
	}

	private boolean validarComponenteSecundario(FichaTecnica fichaTecnica) {
		return validarComponente(fichaTecnica, TipoComponente.SECUNDARIO);
	}

	private boolean validarAcessorios(FichaTecnica fichaTecnica) {
		return fichaTecnica.getAcessorios()!= null && !fichaTecnica.getAcessorios().isEmpty();
	}

}
