package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.Acessorio;
import model.domain.Categoria;
import model.domain.Componente;
import model.domain.EntidadeDominio;
import model.domain.FichaTecnica;
import model.domain.Subcategoria;
import model.domain.TipoComponente;
import util.Conexao;

public class FichaTecnicaDAO implements IDAO {
	
	private Connection connection = null;
	
	@Override
	public boolean salvar(EntidadeDominio entidade) {
		
		FichaTecnica fichaTecnica = (FichaTecnica)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `cadastro`.`ficha_tecnica`(`nome`,`descricao`,`observacoes`,`categoria`,`subcategoria`,`comp_basico`,`comp_primario`,`comp_secundario`,`ativo`)VALUES(?,?,?,?,?,?,?,?,true)");		
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, fichaTecnica.getNomeFicha());
			pst.setString(2, fichaTecnica.getDescricao());
			pst.setString(3, fichaTecnica.getObservacoes());
			pst.setString(4, fichaTecnica.getCategoria().getNomeCategoria());
			pst.setString(5, fichaTecnica.getSubcategoria().getNome());
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.BASICO) {
					pst.setString(6, componente.getNomeComponente());
				}
			}
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.PRIMARIO) {
					pst.setString(7, componente.getNomeComponente());
				}
			}
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.SECUNDARIO) {
					pst.setString(8, componente.getNomeComponente());
				}
			}

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int idFicha = 0;
			if (rs.next())
				idFicha = rs.getInt(1);
			fichaTecnica.setId(idFicha);
			
			if(fichaTecnica.getAcessorios() != null && !fichaTecnica.getAcessorios().isEmpty()) {
				sql = new StringBuilder();
				sql.append("INSERT INTO `cadastro`.`ficha_acessorio`(`id_ficha`,`id_acessorio`)VALUES(?,?)");		
				pst = connection.prepareStatement(sql.toString(),
						Statement.RETURN_GENERATED_KEYS);
				for(Acessorio acessorio: fichaTecnica.getAcessorios()) {
					pst.setInt(1, idFicha);
					pst.setInt(2, acessorio.getId());
					pst.executeUpdate();
				}
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
		
		FichaTecnica fichaTecnica = (FichaTecnica)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`ficha_tecnica` SET `nome`= ?,`descricao`= ?,`observacoes`= ?,`categoria`= ?,`subcategoria`= ?,`comp_basico`= ?,`comp_primario`= ?,`comp_secundario`= ? WHERE `idficha_tecnica` = ?");		
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, fichaTecnica.getNomeFicha());
			pst.setString(2, fichaTecnica.getDescricao());
			pst.setString(3, fichaTecnica.getObservacoes());
			pst.setString(4, fichaTecnica.getCategoria().getNomeCategoria());
			pst.setString(5, fichaTecnica.getSubcategoria().getNome());
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.BASICO) {
					pst.setString(6, componente.getNomeComponente());
				}
			}
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.PRIMARIO) {
					pst.setString(7, componente.getNomeComponente());
				}
			}
			for(Componente componente:fichaTecnica.getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.SECUNDARIO) {
					pst.setString(8, componente.getNomeComponente());
				}
			}
			
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

		FichaTecnica fichaTecnica = (FichaTecnica)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `cadastro`.`ficha_tecnica` SET `ativo` = false WHERE `idficha_tecnica` = ?");
					
			pst = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, fichaTecnica.getId());
			
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

		FichaTecnica fichaTecnica = (FichaTecnica)entidade;
		
		PreparedStatement pst = null;		

		try {
			connection = Conexao.getConnectionMySQL();

			connection.setAutoCommit(false);
			
			List parametros = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT `ficha_tecnica`.`idficha_tecnica`,`ficha_tecnica`.`nome`,`ficha_tecnica`.`descricao`,`ficha_tecnica`.`observacoes`,`ficha_tecnica`.`categoria`,`ficha_tecnica`.`subcategoria`,`ficha_tecnica`.`comp_basico`,`ficha_tecnica`.`comp_primario`,`ficha_tecnica`.`comp_secundario` FROM `cadastro`.`ficha_tecnica` WHERE 1=1");
			if(fichaTecnica.getNomeFicha()!= null && !fichaTecnica.getNomeFicha().equals("")) {
				sql.append(" and `ficha_tecnica`.`nome`=?");
				parametros.add(fichaTecnica.getNomeFicha());
			}
			if(fichaTecnica.getDescricao()!= null && !fichaTecnica.getDescricao().equals("")) {
				sql.append(" and `ficha_tecnica`.`descricao`=?");
				parametros.add(fichaTecnica.getDescricao());
			}
			if(fichaTecnica.getObservacoes()!= null && !fichaTecnica.getObservacoes().equals("")) {
				sql.append(" and `ficha_tecnica`.`observacoes`=?");
				parametros.add(fichaTecnica.getObservacoes());
			}
			if(fichaTecnica.getCategoria() != null && fichaTecnica.getCategoria().getNomeCategoria()!= null) {
				sql.append(" and `ficha_tecnica`.`categoria`=?");
				parametros.add(fichaTecnica.getCategoria().getNomeCategoria());
			}
			if(fichaTecnica.getSubcategoria() != null && fichaTecnica.getSubcategoria().getNome()!= null) {
				sql.append(" and `ficha_tecnica`.`subcategoria`=?");
				parametros.add(fichaTecnica.getSubcategoria().getNome());
			}
			if(fichaTecnica.getComponentes() !=null) {
				for(Componente componente:fichaTecnica.getComponentes()) {
					if(componente != null && componente.getTipoComponente() != null && componente.getTipoComponente().getNomeTipoComponente()!= null) {
						if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.BASICO) {
							sql.append(" and `ficha_tecnica`.`comp_basico`=?");
							parametros.add(componente.getNomeComponente());
						}
						if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.PRIMARIO) {
							sql.append(" and `ficha_tecnica`.`comp_primario`=?");
							parametros.add(componente.getNomeComponente());
						}
						if(componente.getTipoComponente().getNomeTipoComponente() == TipoComponente.SECUNDARIO) {
							sql.append(" and `ficha_tecnica`.`comp_secundario`=?");
							parametros.add(componente.getNomeComponente());
						}
					}
						
				}
			}
			if(fichaTecnica.getId()> 0) {
				sql.append(" and `ficha_tecnica`.`idficha_tecnica`=?");
				parametros.add(Integer.valueOf(fichaTecnica.getId()));
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
				Subcategoria subcategoria = new Subcategoria(resultSet.getString("subcategoria"));
				Categoria categoria = new Categoria(resultSet.getString("categoria"),subcategoria);
				Componente compBasico = new Componente(resultSet.getString("comp_basico"), new TipoComponente(TipoComponente.BASICO));
				Componente compPrimario = new Componente(resultSet.getString("comp_primario"), new TipoComponente(TipoComponente.PRIMARIO));
				Componente compSecundario = new Componente(resultSet.getString("comp_secundario"), new TipoComponente(TipoComponente.SECUNDARIO));
				FichaTecnica fichaTecnicaResultado = new FichaTecnica(resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getString("observacoes"), categoria, subcategoria, compBasico, compPrimario, compSecundario);
				fichaTecnicaResultado.setId(resultSet.getInt("idficha_tecnica"));
				sql = new StringBuilder();
				sql.append("SELECT a.* FROM `cadastro`.`ficha_acessorio` fa " + 
						"join `cadastro`.`acessorio` a on fa.`id_acessorio` = a.`idacessorio` " + 
						"where fa.`id_ficha` = ?;");
				pst = connection.prepareStatement(sql.toString());
				pst.setInt(1, resultSet.getInt("idficha_tecnica"));
				ResultSet rsFichaAcessorio = pst.executeQuery();
				while(rsFichaAcessorio.next()) {
					Acessorio acessorio = new Acessorio(rsFichaAcessorio.getString("nome"), rsFichaAcessorio.getString("descricao"));
					acessorio.setId(rsFichaAcessorio.getInt("idacessorio"));
					fichaTecnicaResultado.adicionarAcessorio(acessorio);
				}
				
				
				resultado.add(fichaTecnicaResultado);
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
