package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.ConsultarAcessorioCommand;
import controle.SalvarAcessorioCommand;
import model.domain.Acessorio;
import model.domain.ItemEmEstoque;

@WebServlet(name = "ControleAcessorioServlet", value = "/ControleAcessorio")
public class CtrlAcessorio extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Acessorio> acessoriosCadastrados = new ArrayList<>();
		
		acessoriosCadastrados = consultar(0,null,null);
		
		req.setAttribute("acessoriosCadastrados", acessoriosCadastrados);
		req.getRequestDispatcher("/FormCadAcessorio.jsp").forward(req, resp);
		
	}
	
	public List consultar(int id, String nomeAcessorio, String descricaoAcessorio) {
		Acessorio acessorio = new Acessorio(nomeAcessorio, descricaoAcessorio);
		try {
			ConsultarAcessorioCommand sProdCmd = new ConsultarAcessorioCommand();
			return (List)sProdCmd.executar(acessorio);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Acessorio acessorio = new Acessorio(req.getParameter("txtNomeAcessorio"), req.getParameter("txtDescricaoAcessorio"));
		acessorio.setItemEmEstoque(new ItemEmEstoque(0,null, 1));
		
		salvar(acessorio);

		req.setAttribute("janelaPai", req.getParameter("janelaPai"));
		req.setAttribute("acessorioCadastrado", acessorio);
		req.getRequestDispatcher("/MensagemCadastroAcessorio.jsp").forward(req, resp);
	}

	public void salvar(Acessorio acessorio) {
		try {
			SalvarAcessorioCommand sProdCmd = new SalvarAcessorioCommand();
			sProdCmd.executar(acessorio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
