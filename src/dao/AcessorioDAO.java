package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Acessorio;
import model.domain.EntidadeDominio;
import util.Conexao;

public class AcessorioDAO implements IDAO {
	
	private Connection connection = null;
	
	@Override
	public boolean salvar(EntidadeDominio entidade) {
		
		Acessorio acessorio = (Acessorio)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`acessorio`(`nome`,`descricao`,`quantidade`,`ativo`)VALUES(?,?,?,true)");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, acessorio.getNomeAcessorio());
			pst.setString(2, acessorio.getDescricaoAcessorio());
			pst.setInt(3, acessorio.getItemEmEstoque().getQuantidadeAcessorio());

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int idAce = 0;
			if (rs.next())
				idAce = rs.getInt(1);
			acessorio.setId(idAce);
			
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
		
		Acessorio acessorio = (Acessorio)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`acessorio` SET `nome` = ?,`descricao` = ?,`quantidade` = ? WHERE `idacessorio` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, acessorio.getNomeAcessorio());
			pst.setString(2, acessorio.getDescricaoAcessorio());
			pst.setInt(3, acessorio.getItemEmEstoque().getQuantidadeAcessorio());
			pst.setInt(4, acessorio.getId());
			
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

		Acessorio acessorio = (Acessorio)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`acessorio` SET `ativo` = false WHERE `idacessorio` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, acessorio.getId());
			
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

		Acessorio acessorio = (Acessorio)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			List parametros = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT `acessorio`.`idacessorio`,`acessorio`.`nome`,`acessorio`.`descricao` FROM `cadastro`.`acessorio` WHERE 1=1");
			if(acessorio.getNomeAcessorio()!= null) {
				sql.append(" and `acessorio`.`nome`=?");
				parametros.add(acessorio.getNomeAcessorio());
			}
			if(acessorio.getDescricaoAcessorio()!= null) {
				sql.append(" and `acessorio`.`descricao`=?");
				parametros.add(acessorio.getDescricaoAcessorio());
			}
			if(acessorio.getId()> 0) {
				sql.append(" and `acessorio`.`idacessorio`=?");
				parametros.add(Integer.valueOf(acessorio.getId()));
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
				Acessorio acessorioResultado = new Acessorio(resultSet.getString("nome"), resultSet.getString("descricao"));
				acessorioResultado.setId(resultSet.getInt("idacessorio"));
				resultado.add(acessorioResultado);
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
