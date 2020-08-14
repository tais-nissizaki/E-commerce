package negocio;

import java.util.Date;

import model.domain.EntidadeDominio;
import model.domain.Usuario;

public class ValidarUsuario implements IStrategy{
	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		String mensagem = "";
		if(!validarSenha(usuario)) {
			mensagem += "Sua senha não é válida, é necessário letras maiúsculas, minúsculas e caracteres especiais.";
		}
		if(!confirmarSenha(usuario)) {
			mensagem += "A senha não confirma.";
		}
		return mensagem;
	}
	
	private boolean validarSenha(Usuario usuario){
		String letrasMaiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
		String caractereEspecial = "!@#$%&*-+=^~?";
		if(usuario.getSenha().length()<8) {
			return false;
		}
		boolean temMaiuscula = false;
		for(char c: usuario.getSenha().toCharArray()) {
			if(letrasMaiusculas.indexOf(c)>0){
				temMaiuscula = true;
				break;
			}
		}
		if(!temMaiuscula) {
			return false;
		}
		boolean temMinuscula = false;
		for(char c: usuario.getSenha().toCharArray()) {
			if(letrasMinusculas.indexOf(c)>0){
				temMinuscula = true;
				break;
			}
		}
		if(!temMinuscula) {
			return false;
		}
		boolean temEspecial = false;
		for(char c: usuario.getSenha().toCharArray()) {
			if(caractereEspecial.indexOf(c)>0){
				temEspecial = true;
				break;
			}
		}
		if(!temEspecial) {
			return false;
		}
		return true;
	}
	
	private boolean confirmarSenha(Usuario usuario){
		return usuario.getSenha() == usuario.getConfirmaSenha();
	}
}
