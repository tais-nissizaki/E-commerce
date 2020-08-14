package negocio;

import model.domain.EntidadeDominio;
import model.domain.Produto;

public class ValidarProduto implements IStrategy{
	@Override
	public String processar(EntidadeDominio entidade) {
		Produto produto = (Produto) entidade;
		String mensagem = "";
		if(!validarNomeProduto(produto)) {
			mensagem += "O nome do Produto é obrigatório.";
		}
		if(!validarValorDeCompra(produto)) {
			mensagem += "O valor de compra do produto é obrigatório.";
		}
		if(!validarLinhaProduto(produto)) {
			mensagem += "A  Linha do Produto é obrigatória.";
		}
		if(!validarFichaTecnica(produto)) {
			mensagem += "A Ficha Técnica é obrigatória.";
		}
		String resultadoValidacaoFicha = new ValidarFichaTecnica().processar(produto.getFichaTecnica());
		if(resultadoValidacaoFicha != null && !resultadoValidacaoFicha.equals("")) {
			mensagem += resultadoValidacaoFicha;
		}
		return mensagem;
	}
	
	private boolean validarNomeProduto(Produto produto) {
		return produto.getNomeProduto()!= null && produto.getNomeProduto().trim()!= "";
	}
	
	private boolean validarValorDeCompra(Produto produto) {
		return produto.getItemDeCompra()!= null && produto.getItemDeCompra().getValorDeCompra()> 0;
	}
	
	private boolean validarLinhaProduto(Produto produto) {
		return produto.getLinhaProduto()!= null && produto.getLinhaProduto().getNomeLinhaProduto()!= null && produto.getLinhaProduto().getNomeLinhaProduto().trim()!= "";
	}
	
	private boolean validarFichaTecnica(Produto produto) {
		return produto.getFichaTecnica()!= null;
	}
}

