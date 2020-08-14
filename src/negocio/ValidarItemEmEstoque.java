package negocio;

import java.util.Date;

import model.domain.Acessorio;
import model.domain.EntidadeDominio;
import model.domain.Produto;

public class ValidarItemEmEstoque implements IStrategy {
	@Override
	public String processar(EntidadeDominio entidade) {
		String mensagem = "";
		if(entidade instanceof Produto) {
			Produto produto = (Produto) entidade;
			if(!validarQuantidadeProduto(produto)) {
				mensagem += "Você deve informar a quantidade";
			}
		} else if(entidade instanceof Acessorio) {
			Acessorio acessorio = (Acessorio) entidade;
			if(!validarQuantidadeAcessorio(acessorio)) {
				mensagem += "Você deve informar a quantidade";
			}
		}
		return mensagem;
	}
	
	private boolean validarQuantidadeProduto(Produto produto) {
		return produto.getItemEmEstoque()!=null && produto.getItemEmEstoque().getQuantidadeProduto()>0;
	}
	
	private boolean validarQuantidadeAcessorio(Acessorio acessorio) {
		return acessorio.getItemEmEstoque()!=null && acessorio.getItemEmEstoque().getQuantidadeAcessorio()>0;
	}

}
