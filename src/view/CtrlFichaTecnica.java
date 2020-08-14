package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.ConsultarFichaCommand;
import controle.SalvarFichaCommand;
import model.domain.Acessorio;
import model.domain.Categoria;
import model.domain.Componente;
import model.domain.FichaTecnica;
import model.domain.Subcategoria;
import model.domain.TipoComponente;

@WebServlet(name = "ControleFichaTecnicaServlet", value = "/ControleFichaTecnica")
public class CtrlFichaTecnica extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<FichaTecnica> fichasCadastradas = new ArrayList<>();
		fichasCadastradas = consultar(null,null,null,null,null,null,null,null);
		req.setAttribute("fichasCadastradas", fichasCadastradas);
		req.getRequestDispatcher("/FormCadFicha.jsp").forward(req, resp);
		
	}

	public List consultar(String nomeFicha, String descricao, String observacoes, Categoria categoria, Subcategoria subcategoria, Componente componenteBasico, Componente componentePrimario, Componente componenteSecundario) {
		FichaTecnica fichaTecnica = new FichaTecnica(nomeFicha, descricao, observacoes, categoria, subcategoria, componenteBasico, componentePrimario, componenteSecundario);
		try {
			ConsultarFichaCommand sProdCmd = new ConsultarFichaCommand();
			return (List)sProdCmd.executar(fichaTecnica);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getParameterMap().forEach((nome, valor) -> {
			System.out.println(nome + " = " + Arrays.asList(valor));
		});
		Subcategoria subcategoria = new Subcategoria(req.getParameter("txtSubcategoria"));
		Categoria categoria = new Categoria(req.getParameter("txtCategoria"), subcategoria);
		
		Componente componenteBasico = new Componente(req.getParameter("txtComponenteBasico"), new TipoComponente(TipoComponente.BASICO));
		Componente componentePrimario = new Componente(req.getParameter("txtComponentePrimario"), new TipoComponente(TipoComponente.PRIMARIO));
		Componente componenteSecundario = new Componente(req.getParameter("txtComponenteSecundario"), new TipoComponente(TipoComponente.SECUNDARIO));
		
		FichaTecnica fichaTecnica = new FichaTecnica(
				req.getParameter("txtNomeFicha"), 
				req.getParameter("txtDescricaoFicha"), 
				categoria, 
				subcategoria, 
				componenteBasico, 
				componentePrimario, 
				componenteSecundario);
		fichaTecnica.setObservacoes(req.getParameter("txtObservacoesFicha"));
		String[] idsAcessorioFicha = req.getParameterValues("idAcessorioFicha");
		if(idsAcessorioFicha != null) {
			for(int i=0; i<idsAcessorioFicha.length; i++) {
				Acessorio acessorio = new Acessorio(req.getParameterValues("nomeAcessorioFicha")[i], req.getParameterValues("descricaoAcessorioFicha")[i]);
				acessorio.setId(Integer.valueOf(idsAcessorioFicha[i]));
				fichaTecnica.adicionarAcessorio(acessorio);
			}
		}
		
		salvar(fichaTecnica);
		
		req.setAttribute("janelaPai", req.getParameter("janelaPai"));
		req.setAttribute("fichaCadastrada", fichaTecnica);
		req.getRequestDispatcher("/MensagemCadastroFicha.jsp").forward(req, resp);
	}
	public void salvar(FichaTecnica fichaTecnica) {
		try {
			SalvarFichaCommand sProdCmd = new SalvarFichaCommand();
			sProdCmd.executar(fichaTecnica);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
