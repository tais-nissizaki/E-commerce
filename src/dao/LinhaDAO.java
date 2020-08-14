package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.Acessorio;
import model.domain.EntidadeDominio;
import model.domain.LinhaProduto;
import util.Conexao;

public class LinhaDAO implements IDAO {
	
	private Connection connection = null;
	
	@Override
	public boolean salvar(EntidadeDominio entidade) {
		
		LinhaProduto linha = (LinhaProduto) entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`produto`(`linha`)VALUES(?)");

			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, linha.getNomeLinhaProduto());
						
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int idAce = 0;
			if (rs.next())
				idAce = rs.getInt(1);
			linha.setId(idAce);
			
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
		
		LinhaProduto linha = (LinhaProduto) entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`produto` SET `linha` = ? WHERE `idusuario` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, linha.getNomeLinhaProduto());
			pst.setInt(2, linha.getId());
			
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

		LinhaProduto linha = (LinhaProduto) entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`usuario` SET `ativo` = false WHERE `idusuario` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, linha.getId());
			
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

		LinhaProduto linha = (LinhaProduto) entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			List parametros = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct `produto`.`linha` FROM `cadastro`.`produto` WHERE 1=1");
			if(linha.getNomeLinhaProduto()!= null && !linha.getNomeLinhaProduto().equals("")) {
				sql.append(" and `produto`.`linha`=?");
				parametros.add(linha.getNomeLinhaProduto());
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
			
			while(resultSet.next()) {
				LinhaProduto linhaResultado = new LinhaProduto(resultSet.getString("linha"));
				sql = new StringBuilder();
				sql.append("SELECT distinct p.`linha`, a.* FROM `cadastro`.`produto` p "); 
				sql.append("JOIN `cadastro`.`produto_acessorio` pa on p.id = pa.id_produto "); 
				sql.append("JOIN `cadastro`.`acessorio` a on pa.id_acessorio = a.idacessorio ");
				sql.append("WHERE p.`linha` = ? ");
				
				pst = connection.prepareStatement(sql.toString());
				pst.setString(1, resultSet.getString("linha"));
				ResultSet rsAcessorios = pst.executeQuery();
				List<Acessorio> acessorios = new ArrayList<>();
				while(rsAcessorios.next()) {
					Acessorio acessorio = new Acessorio(rsAcessorios.getString("nome"), rsAcessorios.getString("descricao"));
					acessorio.setId(rsAcessorios.getInt("idacessorio"));
					acessorios.add(acessorio);
				}
				linhaResultado.setAcessorio(acessorios);
				
				resultado.add(linhaResultado);
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
