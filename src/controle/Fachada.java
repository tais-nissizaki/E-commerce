package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ProdutoDAO;
import dao.AcessorioDAO;
import dao.UsuarioDAO;
import dao.FichaTecnicaDAO;
import dao.IDAO;
import dao.LinhaDAO;
import model.Log;
import model.domain.Produto;
import model.domain.Usuario;
import model.domain.FichaTecnica;
import model.domain.LinhaProduto;
import model.domain.Acessorio;
import model.domain.EntidadeDominio;
import negocio.ComplementarDt;
import negocio.Criptografar;
import negocio.GerarLog;
import negocio.IStrategy;
import negocio.ValidarAcessorio;
import negocio.ValidarProduto;
import negocio.ValidarComprador;
import negocio.ValidarUsuario;
import negocio.ValidarFichaTecnica;
import negocio.ValidarFiltroConsulta;
import negocio.ValidarItemEmEstoque;

public class Fachada implements IFachada{
	private Map<String, List<IStrategy>> mapaAntesPesistencia;
	private Map<String, List<IStrategy>> mapaDepoisPesistencia;
	
	private Map<String, List<IStrategy>> mapaAntesInativar;
	private Map<String, List<IStrategy>> mapaDepoisInativar;
	
	private Map<String, List<IStrategy>> mapaAntesConsultar;
	private Map<String, List<IStrategy>> mapaDepoisConsultar;
	
	private Map<String, IDAO> mapaDaos;
	
	
	public Fachada() {
		
		ComplementarDt compDtCad = new ComplementarDt();
		Criptografar criptografar = new Criptografar();		
		GerarLog gLog = new GerarLog();
		ValidarAcessorio vAcessorio = new ValidarAcessorio();
		ValidarComprador vComprador = new ValidarComprador();
		ValidarFichaTecnica vFichaTecnica = new ValidarFichaTecnica();
		ValidarFiltroConsulta vFiltroConsulta = new ValidarFiltroConsulta();
		ValidarItemEmEstoque vItemEstoque = new ValidarItemEmEstoque();
		ValidarProduto vProduto = new ValidarProduto();
		ValidarUsuario vUsuario = new ValidarUsuario();
		
		
		List<IStrategy> rnsAntesProduto = new ArrayList<IStrategy>();
		List<IStrategy> rnsAntesAcessorio = new ArrayList<IStrategy>();
		List<IStrategy> rnsAntesFichaTecnica = new ArrayList<IStrategy>();
		List<IStrategy> rnsAntesConsultaProduto = new ArrayList<IStrategy>();
		List<IStrategy> rnsAntesUsuario = new ArrayList<IStrategy>();
		
		List<IStrategy> rnsDepoisProduto = new ArrayList<IStrategy>();	
		List<IStrategy> rnsDepoisFichaTecnica = new ArrayList<IStrategy>();	
		List<IStrategy> rnsDepoisAcessorio = new ArrayList<IStrategy>();	
		
		//Lista de regras executadas antes da persistência de produto	
		rnsAntesProduto.add(compDtCad);
		rnsAntesProduto.add(vItemEstoque);
		rnsAntesProduto.add(vComprador);
        //rnsAntesProduto.add(vFichaTecnica);
		rnsAntesProduto.add(vProduto);
		
		//Lista de regras executadas antes da persistência de ficha tecnica
		rnsAntesFichaTecnica.add(vFichaTecnica);
		
		//Lista de regras executadas antes da persistência de acessorio
		rnsAntesAcessorio.add(vAcessorio);
		
		//Lista de regras executadas antes da persistência de usuario
		rnsAntesUsuario.add(criptografar);
		rnsAntesUsuario.add(vUsuario);
		
		//Lista de regras executadas depois da persistência de produto	
		rnsDepoisProduto.add(gLog);
		
		//Lista de regras executadas depois da persistência de acessorio	
		rnsDepoisAcessorio.add(gLog);
				
		//Lista de regras executadas depois da persistência de ficha tecnica	
		rnsDepoisFichaTecnica.add(gLog);
		
		mapaAntesPesistencia = new HashMap<String, List<IStrategy>>();
		
		
		String nmProduto = Produto.class.getName();
		String nmAcessorio = Acessorio.class.getName();
		String nmFichaTecnica = FichaTecnica.class.getName();
		String nmUsuario = Usuario.class.getName();
		String nmLinha = LinhaProduto.class.getName();
		
		mapaAntesPesistencia.put(nmProduto, rnsAntesProduto);
		mapaAntesPesistencia.put(nmFichaTecnica, rnsAntesFichaTecnica);
		mapaAntesPesistencia.put(nmAcessorio, rnsAntesAcessorio);
		mapaAntesPesistencia.put(nmUsuario, rnsAntesUsuario);
		
		mapaDepoisPesistencia = new HashMap<String, List<IStrategy>>();
		mapaDepoisPesistencia.put(nmProduto, rnsDepoisProduto);			
		mapaDepoisPesistencia.put(nmFichaTecnica, rnsDepoisFichaTecnica);
		mapaDepoisPesistencia.put(nmAcessorio, rnsDepoisAcessorio);
		
		mapaDaos = new HashMap<String, IDAO>();
		mapaDaos.put(nmProduto, new ProdutoDAO());
		mapaDaos.put(nmFichaTecnica, new FichaTecnicaDAO());
		mapaDaos.put(nmAcessorio, new AcessorioDAO());
		mapaDaos.put(nmUsuario, new UsuarioDAO());	
		mapaDaos.put(nmLinha, new LinhaDAO());
		
		mapaAntesConsultar = new HashMap<String, List<IStrategy>>();
		mapaDepoisConsultar = new HashMap<String, List<IStrategy>>();
		
		mapaAntesInativar = new HashMap<String, List<IStrategy>>();
		mapaDepoisInativar = new HashMap<String, List<IStrategy>>();
		
	}

	@Override
	public String salvar(EntidadeDominio entidade) {
		String nmClass = entidade.getClass().getName();
		List<IStrategy> rnsAntes = mapaAntesPesistencia.get(nmClass);
		
		StringBuilder sb = executarStrategies(rnsAntes, entidade);		
		
		if(sb.length()==0) {
			//se não houve erros
			IDAO dao = mapaDaos.get(nmClass);
			boolean salvouComSucesso = dao.salvar(entidade);
			if(!salvouComSucesso) {
				return "Falha ao gravar o produto na base de dados";
			}
			List<IStrategy> rnsDepois = mapaDepoisPesistencia.get(nmClass);
			sb.append(executarStrategies(rnsDepois, entidade));
		}else {
			return sb.toString();
		}
		
		return null;
	}
	
	private StringBuilder executarStrategies(List<IStrategy> strategies, EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		if(strategies != null) {
			for(IStrategy rn:strategies) {
				String msg = rn.processar(entidade);
				if(msg!= null) {
					sb.append(msg);
				}
			}
		}
		return sb;
	}

	@Override
	public  String alterar(EntidadeDominio entidade) {
		String nmClass = entidade.getClass().getName();
		List<IStrategy> rnsAntes = mapaAntesPesistencia.get(nmClass);
		
		StringBuilder sb = executarStrategies(rnsAntes, entidade);		
		
		if(sb.length()==0) {
			//se não houve erros
			IDAO dao = mapaDaos.get(nmClass);
			dao.alterar(entidade);
			List<IStrategy> rnsDepois = mapaDepoisPesistencia.get(nmClass);
			sb = executarStrategies(rnsDepois, entidade);
		}else {
			return sb.toString();
		}
		
		return null;

	}

	@Override
	public String inativar(EntidadeDominio entidade) {
		String nmClass = entidade.getClass().getName();
		List<IStrategy> rnsAntes = mapaAntesInativar.get(nmClass);
		
		StringBuilder sb = executarStrategies(rnsAntes, entidade);		
		
		if(sb.length()==0) {
			//se não houve erros
			IDAO dao = mapaDaos.get(nmClass);
			dao.inativar(entidade);
			List<IStrategy> rnsDepois = mapaDepoisInativar.get(nmClass);
			sb = executarStrategies(rnsDepois, entidade);
		}else {
			return sb.toString();
		}
		
		return null;

	}


	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		String nmClass = entidade.getClass().getName();
		List<IStrategy> rnsAntes = mapaAntesConsultar.get(nmClass);
		List<EntidadeDominio> listaRetorno = null;
		
		StringBuilder sb = executarStrategies(rnsAntes, entidade);		
		
		if(sb.length()==0) {
			//se não houve erros
			IDAO dao = mapaDaos.get(nmClass);
			listaRetorno = dao.consultar(entidade);
			List<IStrategy> rnsDepois = mapaDepoisConsultar.get(nmClass);
			sb = executarStrategies(rnsDepois, entidade);
		}else {
			return listaRetorno;
		}
		
		return listaRetorno;

	}

}
