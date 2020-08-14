package negocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.domain.EntidadeDominio;
import model.domain.Usuario;

public class Criptografar implements IStrategy{
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		char ascii;
	    char x, y;
	    String mensagemCriptografada ="";
	    int chave = 19;    
	    for (int i = 0; i < usuario.getSenha().length(); i++) {
	     //Tratamento  
	        if (usuario.getSenha().charAt(i) >= 33 && usuario.getSenha().charAt(i) <= 122) {// de acordo com a tabela ASCII
	            if ((int) (usuario.getSenha().charAt(i) + chave) > 122) {
	                x = (char) (usuario.getSenha().charAt(i) + chave);
	                y = (char) (x - 122);
	                ascii = (char) (96 + y);
	                mensagemCriptografada = mensagemCriptografada + ascii;
	            } else {
	                ascii = (char) (usuario.getSenha().charAt(i) + chave);
	                mensagemCriptografada = mensagemCriptografada + ascii;
	            }
	        }
	    }
	    usuario.setSenha(mensagemCriptografada);
		return null;
	}
}
