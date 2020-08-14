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

import controle.ConsultarLinhaCommand;
import model.domain.Acessorio;
import model.domain.LinhaProduto;

@WebServlet(name = "ControleLinhaProdutoServlet", value = "/ControleLinhaProduto")
public class CtrlLinhaProduto extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<LinhaProduto> linhasCadastradas = new ArrayList<>();
		
		linhasCadastradas = (List)new ConsultarLinhaCommand().executar(new LinhaProduto(null,null));
		
		req.setAttribute("linhasCadastradas", linhasCadastradas);
		req.getRequestDispatcher("/FormCadLinhaProduto.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(Arrays.asList(req.getParameterValues("idAcessorioLinha")));
		System.out.println(Arrays.asList(req.getParameterValues("nomeAcessorioLinha")));
		System.out.println(Arrays.asList(req.getParameterValues("descricaoAcessorioLinha")));
		
		String[] idsAcessorios = req.getParameterValues("idAcessorioLinha");
		List<Acessorio> acessorios = new ArrayList<>();
		for(int i=0; i<idsAcessorios.length; i++) {
			Acessorio acessorio = new Acessorio(req.getParameterValues("nomeAcessorioLinha")[i], req.getParameterValues("descricaoAcessorioLinha")[i]);
			acessorio.setId(Integer.parseInt(idsAcessorios[i]));
			acessorios.add(acessorio);
		}
		
		LinhaProduto linhaProduto = new LinhaProduto(req.getParameter("txtNomeLinha"), acessorios);
		
		req.setAttribute("linhaProdutoCadastrada", linhaProduto);
		
		StringBuilder response = new StringBuilder();
		response.append("<body><script>");
		response.append("var linhaProduto = ");
		response.append("{");
		response.append("nome: '").append(linhaProduto.getNomeLinhaProduto()).append("', ");
		response.append("acessorios: [");
		for(Acessorio aces: linhaProduto.getAcessorio()) {
			response.append("{");
			response.append("id: '").append(aces.getId()).append("', ");
			response.append("nome: '").append(aces.getNomeAcessorio()).append("', ");
			response.append("descricao: '").append(aces.getDescricaoAcessorio()).append("', ");
			response.append("},");
		}
		response.append("]");
		response.append("};");
		response.append("window.opener.atribuirLinha(linhaProduto);");
		response.append("window.close();</script></body>");
		
		resp.getWriter().append(response.toString());
	}

}
