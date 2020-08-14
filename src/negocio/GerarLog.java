package negocio;

import java.lang.reflect.Field;

import model.Log;

import model.domain.EntidadeDominio;

public class GerarLog implements IStrategy{
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Log log = new Log();
		
		System.err.println("Log gerado...");
		
		return null;
	}
}
