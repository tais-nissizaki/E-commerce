package util;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
	
	public static String status;

	public static void main(String[] args) {
		System.out.println("TESTE");
		try {
			Connection connection = Conexao.getConnectionMySQL();
			// Testa sua conex�o//

					if (connection != null) {

						status = ("STATUS--->Conectado com sucesso!");

					} else {

						status = ("STATUS--->N�o foi possivel realizar conex�o");

					}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

	}

}

