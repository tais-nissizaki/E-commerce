package view;

import java.util.List;

import controle.SalvarUsuarioCommand;
import model.domain.Usuario;

public class CtrlLogin {
	public void salvar(Usuario usuario) {
		try {
			SalvarUsuarioCommand sUsuCmd = new SalvarUsuarioCommand();
			sUsuCmd.executar(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
