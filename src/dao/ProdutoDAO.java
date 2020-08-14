package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.domain.Acessorio;
import model.domain.Componente;
import model.domain.Compra;
import model.domain.EntidadeDominio;
import model.domain.FichaTecnica;
import model.domain.ItemDeCompra;
import model.domain.ItemEmEstoque;
import model.domain.LinhaProduto;
import model.domain.Produto;
import model.domain.TipoComponente;
import util.Conexao;

public class ProdutoDAO implements IDAO {
	
	private Connection connection = null;
	
	@Override
	public boolean salvar(EntidadeDominio entidade) {
		
		Produto produto = (Produto)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`produto`(`nome`,`ativo`,`dtCadastro`,`dtAlteracao`,`valor_compra`,`comprador`,`linha`,`quantidade`,`id_ficha`, `codigo`)VALUES(?,true,?,?,?,?,?,?,?,?)");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, produto.getNomeProduto());
			Timestamp time = new Timestamp(produto.getDtCadastro().getTime());
			pst.setTimestamp(2, time);
			pst.setTimestamp(3, time);
			pst.setDouble(4, produto.getItemDeCompra().getValorDeCompra());
			pst.setString(5, produto.getItemDeCompra().getCompra().getComprador());
			pst.setString(6, produto.getLinhaProduto().getNomeLinhaProduto());
			pst.setInt(7, produto.getItemEmEstoque().getQuantidadeProduto());
			pst.setInt(8, produto.getFichaTecnica().getId());
			pst.setInt(9, produto.getCodigo());

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int idAce = 0;
			if (rs.next())
				idAce = rs.getInt(1);
			produto.setId(idAce);
			
			sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`produto_acessorio`(`id_produto`,`id_acessorio`)VALUES(?,?)");
			pst = connection.prepareStatement(sql.toString());
			for(Acessorio acessorio: produto.getLinhaProduto().getAcessorio()) {
				PreparedStatement ps = connection.prepareStatement("SELECT * from `cadastro`.`produto_acessorio` WHERE `produto_acessorio`.`id_produto` = ? AND `produto_acessorio`.`id_acessorio` = ?");
				ps.setInt(1, idAce);
				ps.setInt(2, acessorio.getId());
				ResultSet r = ps.executeQuery();
			}
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean alterar(EntidadeDominio entidade) {
		
		Produto produto = (Produto)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`produto` SET `nome` = ?,`dtAlteracao` = ?,`valor_compra` = ?,`linha` = ? WHERE `id` = ?");

			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, produto.getNomeProduto());
			Timestamp time = new Timestamp(produto.getDtAlteracao().getTime());
			pst.setTimestamp(2, time);
			pst.setDouble(3, produto.getItemDeCompra().getValorDeCompra());
			pst.setString(4, produto.getLinhaProduto().getNomeLinhaProduto());
			pst.setInt(5, produto.getId());
			
			pst.executeUpdate();
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean inativar(EntidadeDominio entidade) {

		Produto produto = (Produto)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`produto` SET `ativo` = false WHERE `id` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, produto.getId());
			
			pst.executeUpdate();
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public List consultar(EntidadeDominio entidade) {

		Produto produto = (Produto)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			List parametros = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT `produto`.`id`,`produto`.`codigo`,`produto`.`nome`,`produto`.`valor_compra`,`produto`.`linha`,`produto`.`id_ficha`, `produto`.`comprador`, `produto`.`quantidade`, `produto`.`ativo` FROM `cadastro`.`produto` ");
			sql.append(" JOIN `cadastro`.`ficha_tecnica` on `produto`.`id_ficha` = `ficha_tecnica`.`idficha_tecnica`");
			sql.append(" WHERE 1=1");
			if(produto.getNomeProduto()!= null && !produto.getNomeProduto().equals("")) {
				sql.append(" and `produto`.`nome`=?");
				parametros.add(produto.getNomeProduto());
			}
			if(produto.getItemDeCompra()!= null && produto.getItemDeCompra().getValorDeCompra()> 0) {
				sql.append(" and `produto`.`valor_compra`=?");
				parametros.add(produto.getItemDeCompra().getValorDeCompra());
			}
			if(produto.getLinhaProduto()!= null && produto.getLinhaProduto().getNomeLinhaProduto()!= null && !produto.getLinhaProduto().getNomeLinhaProduto().equals("")) {
				sql.append(" and `produto`.`linha`=?");
				parametros.add(produto.getLinhaProduto());
			}
			if(produto.getLinhaProduto() != null && produto.getLinhaProduto().getNomeLinhaProduto()!= null && produto.getFichaTecnica().getId()> 0) {
				sql.append(" and `produto`.`id_ficha`=?");
				parametros.add(produto.getFichaTecnica().getId());
			}
			if(produto.getCodigo()> 0) {
				sql.append(" and `produto`.`codigo`=?");
				parametros.add(Integer.valueOf(produto.getCodigo()));
			}
			if(produto.getId()> 0) {
				sql.append(" and `produto`.`id`=?");
				parametros.add(Integer.valueOf(produto.getId()));
			}
			if(produto.getFichaTecnica() != null && produto.getFichaTecnica().getDescricao() != null && !produto.getFichaTecnica().getDescricao().equals("")) {
				sql.append(" and `ficha_tecnica`.`descricao`=?");
				parametros.add(produto.getFichaTecnica().getDescricao());
			}
			if(produto.getFichaTecnica() != null && produto.getFichaTecnica().getCategoria() != null && produto.getFichaTecnica().getCategoria().getNomeCategoria() != null 
					&& !produto.getFichaTecnica().getCategoria().getNomeCategoria().equals("")) {
				sql.append(" and `ficha_tecnica`.`categoria`=?");
				parametros.add(produto.getFichaTecnica().getCategoria().getNomeCategoria());
			}
			if(produto.getFichaTecnica() != null && produto.getFichaTecnica().getSubcategoria() != null && produto.getFichaTecnica().getSubcategoria().getNome() != null 
					&& !produto.getFichaTecnica().getSubcategoria().getNome().equals("")) {
				sql.append(" and `ficha_tecnica`.`categoria`=?");
				parametros.add(produto.getFichaTecnica().getSubcategoria().getNome());
			}
			if(produto.getFichaTecnica() != null && produto.getFichaTecnica().getComponentes() != null && !produto.getFichaTecnica().getComponentes().isEmpty()) {
				for (Componente	componente : produto.getFichaTecnica().getComponentes()) {
					if(componente.getNomeComponente() != null && !componente.getNomeComponente().equals("")) {
						if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.BASICO)) {
							sql.append(" and `ficha_tecnica`.`comp_basico`=?");
						} else if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.PRIMARIO)) {
							sql.append(" and `ficha_tecnica`.`comp_primario`=?");
						} else if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.SECUNDARIO)) {
							sql.append(" and `ficha_tecnica`.`comp_secundario`=?");
						}
						
						parametros.add(componente.getNomeComponente());
					}
				}
			}
			
			pst = connection.prepareStatement(sql.toString());
				
			for(Object parametro:parametros) {
				if(parametro instanceof String) {
					pst.setString(parametros.indexOf(parametro)+1,(String)parametro);
				} else if(parametro instanceof Integer) {
					pst.setInt(parametros.indexOf(parametro)+1,(Integer)parametro);
				}
			}
			
			ResultSet resultSet = pst.executeQuery();
			
			List<EntidadeDominio> resultado = new ArrayList<>();
			
			FichaTecnicaDAO fichaDAO = new FichaTecnicaDAO();
			
			while(resultSet.next()) {
				FichaTecnica idFicha = new FichaTecnica(resultSet.getInt("id_ficha"));
				List fichas = fichaDAO.consultar(idFicha);
				FichaTecnica ficha = null;
				if(fichas!=null && !fichas.isEmpty()) {
					ficha = (FichaTecnica)fichas.get(0);
				}
				LinhaProduto linha = new LinhaProduto(resultSet.getString("linha"));
				sql = new StringBuilder();
				sql.append("SELECT a.* FROM `cadastro`.`acessorio` a join `cadastro`.`produto_acessorio` pa on a.`idacessorio` = pa.`id_acessorio` WHERE pa.`id_produto` = ?");

				pst = connection.prepareStatement(sql.toString());
				pst.setInt(1, resultSet.getInt("id"));
				ResultSet resultSetAcessorioProduto = pst.executeQuery();
				List<Acessorio> acessorios = new ArrayList<>();
				while (resultSetAcessorioProduto.next()) {
					Acessorio acessorio = new Acessorio(resultSetAcessorioProduto.getString("nome"), resultSetAcessorioProduto.getString("descricao"));
					acessorio.setId(resultSetAcessorioProduto.getInt("idacessorio"));
					acessorios.add(acessorio);
				}
				linha.setAcessorio(acessorios);				
				
				Produto produtoResultado = new Produto(resultSet.getString("nome"), ficha, linha);
				produtoResultado.setCodigo(resultSet.getInt("codigo"));
				produtoResultado.setId(resultSet.getInt("id"));
				produtoResultado.setAtivo(resultSet.getBoolean("ativo"));
				Compra compra= new Compra(resultSet.getString("comprador"), 0);
				ItemDeCompra itemCompra = new ItemDeCompra(0, resultSet.getDouble("valor_compra"));
				itemCompra.setCompra(compra);
				produtoResultado.setItemDeCompra(itemCompra);
				
				ItemEmEstoque itemEstoque = new ItemEmEstoque(resultSet.getInt("quantidade"), null, 0);
				produtoResultado.setItemEmEstoque(itemEstoque);
				resultado.add(produtoResultado);
			}
			
			return resultado;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} finally {
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
