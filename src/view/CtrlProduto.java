package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.AlterarProdutoCommand;
import controle.ConsultarProdutoCommand;
import controle.InativarProdutoCommand;
import controle.SalvarProdutoCommand;
import model.domain.Acessorio;
import model.domain.Categoria;
import model.domain.Componente;
import model.domain.Compra;
import model.domain.FichaTecnica;
import model.domain.ItemDeCompra;
import model.domain.ItemEmEstoque;
import model.domain.LinhaProduto;
import model.domain.Produto;
import model.domain.Subcategoria;
import model.domain.TipoComponente;

@WebServlet(name = "ControleProdutoServlet", value = "/ControleProduto")
public class CtrlProduto extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("operacao"));
		System.out.println(req.getParameter("operacao"));
		if(req.getParameter("operacao").equals("CONSULTAR")) {

			LinhaProduto linhaProduto = new LinhaProduto(req.getParameter("txtLinhaProduto"), null);
			Subcategoria subcategoria = new Subcategoria(req.getParameter("txtSubCategoria"));
			Categoria categoria = new Categoria(req.getParameter("txtCategoria"), subcategoria);
			
			Componente componenteBasico = new Componente(req.getParameter("txtComponenteBasico"), new TipoComponente(TipoComponente.BASICO));
			Componente componentePrimario = new Componente(req.getParameter("txtComponentePrimario"), new TipoComponente(TipoComponente.PRIMARIO));
			Componente componenteSecundario = new Componente(req.getParameter("txtComponenteSecundario"), new TipoComponente(TipoComponente.SECUNDARIO));
			
			
			FichaTecnica fichaTecnica = new FichaTecnica(null, req.getParameter("txtDescricao"), categoria, subcategoria, componenteBasico, 
					componentePrimario, componenteSecundario);
			
			List<Produto> produtos = new ArrayList<>();
			int codigo = 0;
			if(req.getParameter("txtCodigoProduto")!= null && !req.getParameter("txtCodigoProduto").equals("")) {
				codigo = Integer.parseInt(req.getParameter("txtCodigoProduto"));
			}
			produtos = consultar(codigo,req.getParameter("txtNomeProduto"), fichaTecnica, linhaProduto);
			req.setAttribute("produtosConsultados", produtos);
			req.getRequestDispatcher("/FormConsProd.jsp").forward(req, resp);
		} else if(req.getParameter("operacao").equals("INATIVAR")) {
			System.out.println(req.getParameter("txtIdProduto"));
			int idProduto = Integer.parseInt(req.getParameter("txtIdProduto"));
			Produto produto = new Produto(idProduto);
			produto.setId(idProduto);
			
			inativar(produto);	

			req.getRequestDispatcher("/FormConsProd.jsp").forward(req, resp);
		} else if(req.getParameter("operacao").equals("CARREGARALTERAR")) {
			System.out.println(req.getParameter("txtIdProduto"));
			int idProduto = Integer.parseInt(req.getParameter("txtIdProduto"));
			Produto produto = new Produto(idProduto);
			produto.setId(idProduto);
			List produtos = consultar(idProduto, null,null,null);
			
			if(produtos!=null && !produtos.isEmpty()) {
				produto = (Produto)produtos.get(0);
			}
			
			req.setAttribute("produtoAlteracao", produto);
			req.getRequestDispatcher("/FormCadProd.jsp").forward(req, resp);
		}
		
	}

	public List consultar(int codigo, String nomeProduto, FichaTecnica fichaTecnica, LinhaProduto linhaProduto) {
		Produto produto = new Produto(codigo,nomeProduto, fichaTecnica, linhaProduto);
		try {
			ConsultarProdutoCommand sProdCmd = new ConsultarProdutoCommand();
			return (List)sProdCmd.executar(produto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void inativar(Produto produto) {
		try {
			InativarProdutoCommand sProdCmd = new InativarProdutoCommand();
			sProdCmd.executar(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String salvar(Produto produto) {
		try {
			SalvarProdutoCommand sProdCmd = new SalvarProdutoCommand();
			return (String) sProdCmd.executar(produto);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public void alterar(Produto produto) {
		try {
			AlterarProdutoCommand sProdCmd = new AlterarProdutoCommand();
			sProdCmd.executar(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Produto produto = null;
		String codigoStr = req.getParameter("txtCodigo");
		String comprador = req.getParameter("txtQuantidade");
		String nomeProduto = req.getParameter("txtNomeProduto");
		String quantidadeProdutoStr = req.getParameter("txtQuantidade");
		List<Acessorio> acessoriosLinha = new ArrayList<>();
		String[] idAcessorioLinha = req.getParameterValues("idAcessorioLinha");
		if(idAcessorioLinha != null) {
			for(int i=0; i<idAcessorioLinha.length; i++) {
				Acessorio acessorio = new Acessorio(req.getParameterValues("nomeAcessorioLinha")[i], req.getParameterValues("descricaoAcessorioLinha")[i]);
				acessorio.setId(Integer.parseInt(idAcessorioLinha[i]));
				acessoriosLinha.add(acessorio);
			}
		}
		LinhaProduto linhaProduto = new LinhaProduto(req.getParameter("txtLinhaProduto"), acessoriosLinha);
		
		List<Acessorio> acessoriosFicha = new ArrayList<>();
		String[] idAcessorioFicha= req.getParameterValues("idAcessorioFicha");
		if(idAcessorioFicha != null) {
			for(int i=0; i<idAcessorioFicha.length; i++) {
				Acessorio acessorio = new Acessorio(req.getParameterValues("nomeAcessorioFicha")[i], req.getParameterValues("descricaoAcessorioFicha")[i]);
				acessorio.setId(Integer.parseInt(idAcessorioFicha[i]));
				acessoriosFicha.add(acessorio);
			}
		}
		String nomeFicha = req.getParameter("txtNomeFicha");
		String descricaoFicha = req.getParameter("txtDescricaoFicha");
		String observacoesFicha = req.getParameter("txtObservacoesFicha");
		Subcategoria subcategoria = new Subcategoria(req.getParameter("txtSubcategoria"));
		Categoria categoria = new Categoria(req.getParameter("txtCategoria"), subcategoria);
		Componente componenteBasico = new Componente(req.getParameter("txtComponenteBasico"), new TipoComponente(TipoComponente.BASICO));
		Componente componentePrimario = new Componente(req.getParameter("txtComponentePrimario"), new TipoComponente(TipoComponente.PRIMARIO));
		Componente componenteSecundario = new Componente(req.getParameter("txtComponenteSecundario"), new TipoComponente(TipoComponente.SECUNDARIO));
		FichaTecnica fichaTecnica = new FichaTecnica(nomeFicha, descricaoFicha, categoria, subcategoria, componenteBasico, componentePrimario, componenteSecundario);
		fichaTecnica.setAcessorios(acessoriosFicha);
		fichaTecnica.setObservacoes(observacoesFicha);
		if(req.getParameter("txtIdFicha") != null && !req.getParameter("txtIdFicha").equals("")) {			
			fichaTecnica.setId(Integer.parseInt(req.getParameter("txtIdFicha")));
		}
		
		int codigo = 0;
		if(codigoStr != null && !codigoStr.equals("")) {
			codigo = Integer.parseInt(codigoStr);
		}
		produto = new Produto(codigo, nomeProduto, fichaTecnica, linhaProduto);
		double valorCompra = 0;
		if(req.getParameter("txtValor") != null && !req.getParameter("txtValor").equals("")) {
			valorCompra = Double.parseDouble(req.getParameter("txtValor"));
		}
		ItemDeCompra itemDeCompra = new ItemDeCompra(0, valorCompra);
		itemDeCompra.setCompra(new Compra(comprador, valorCompra));
		produto.setItemDeCompra(itemDeCompra);
		
		int quantidadeProduto = 0;
		if(quantidadeProdutoStr != null && !quantidadeProdutoStr.equals("")) {
			quantidadeProduto = Integer.parseInt(quantidadeProdutoStr);
		}
		ItemEmEstoque itemEmEstoque = new ItemEmEstoque(quantidadeProduto, new Date(), 0);
		produto.setItemEmEstoque(itemEmEstoque);
		if(req.getParameter("operacao").equals("SALVAR")) {
			System.out.println("SALVAR PRODUTO");
			
			String mensagem = salvar(produto);
			if(mensagem != null && !mensagem.equals("")) {
				req.setAttribute("mensagem", mensagem);
				req.getRequestDispatcher("/FormCadProd.jsp").forward(req, resp);
			} else {
				req.setAttribute("mensagem", "Produto Cadastrado com sucesso");
				req.getRequestDispatcher("/MensagemCadastroProduto.jsp").forward(req, resp);
			}
		} else if(req.getParameter("operacao").equals("ALTERAR")) {

			int idProduto = 0;
			if(req.getParameter("txtIdProduto") != null) {
				idProduto = Integer.parseInt(req.getParameter("txtIdProduto"));
				produto.setId(idProduto);
			}			
			
			System.out.println("ALTERAR PRODUTO");
			alterar(produto);
			req.setAttribute("mensagem", "Produto alterado com sucesso");
			req.getRequestDispatcher("/MensagemCadastroProduto.jsp").forward(req, resp);
		}
	}

}
