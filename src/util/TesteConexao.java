package util;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
	
	public static String status;

	public static void main(String[] args) {
		System.out.println("TESTE");
		try {
			Connection connection = Conexao.getConnectionMySQL();
			// Testa sua conexão//

					if (connection != null) {

						status = ("STATUS--->Conectado com sucesso!");

					} else {

						status = ("STATUS--->Não foi possivel realizar conexão");

					}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

	}

}

