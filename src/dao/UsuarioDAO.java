package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.EntidadeDominio;
import model.domain.Usuario;
import util.Conexao;

public class UsuarioDAO implements IDAO {
	
	private Connection connection = null;
	
	@Override
	public boolean salvar(EntidadeDominio entidade) {
		
		Usuario usuario = (Usuario)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`usuario`(`login`,`senha`,`ativo`)VALUES(?,?,true)");

			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, usuario.getLogin());
			pst.setString(2, usuario.getSenha());
			
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int idAce = 0;
			if (rs.next())
				idAce = rs.getInt(1);
			usuario.setId(idAce);
			
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
		
		Usuario usuario = (Usuario)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`usuario` SET `login` = ?,`senha` = ? WHERE `idusuario` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, usuario.getLogin());
			pst.setString(2, usuario.getSenha());
			pst.setInt(3, usuario.getId());
			
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

		Usuario usuario = (Usuario)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`usuario` SET `ativo` = false WHERE `idusuario` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, usuario.getId());
			
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

		Usuario usuario = (Usuario)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			List parametros = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT `usuario`.`idusuario`,`usuario`.`login` FROM `cadastro`.`usuario` WHERE 1=1");
			if(usuario.getLogin()!= null) {
				sql.append(" and `usuario`.`login`=?");
				parametros.add(usuario.getLogin());
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
				Usuario usuarioResultado = new Usuario(resultSet.getString("login"));
				usuarioResultado.setId(resultSet.getInt("idacessorio"));
				resultado.add(usuarioResultado);
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
