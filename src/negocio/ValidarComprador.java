package negocio;

import model.domain.EntidadeDominio;
import model.domain.Produto;

public class ValidarComprador implements IStrategy {
	@Override
	public String processar(EntidadeDominio entidade) {
		Produto produto = (Produto) entidade;
		String mensagem ="";
		if(produto.getItemDeCompra().getCompra().getComprador() == null || produto.getItemDeCompra().getCompra().getComprador() == "" ) {
			mensagem += "O comprador é obrigatório.";
		}
		return mensagem;
	}
}
