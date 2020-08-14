<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.Acessorio" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE ACESSÓRIO::::</title>
</head>
<body>
	<form action="ControleAcessorio" method="post">		
		<span id="spanSelecionarAcessorio" style="display: none">
			<h4>Selecione um acessório já cadastrado</h4>
			<select name="acessorioJaCadastradado" id="acessorioJaCadastradado"></select>
			<br />
			<br />
			<input type="button" value="Adicionar" onclick="adicionarAcessorio()"/>
			<h4>Ou cadastre uma nova</h4>
		</span>
		
		<label for="txtNomeAcessorio">Nome do acessório:</label>
		<input type="text" id="txtNomeAcessorio" name="txtNomeAcessorio"/>
		<br />
		
		<label for="txtDescricaoAcessorio">Descrição do acessório:</label>
		<input type="text" id="txtDescricaoAcessorio" name="txtDescricaoAcessorio"/>
		<br />
		
		<input type="hidden" name="janelaPai" id="janelaPai" value=""/>
		<input type="submit" id="operacao" name="operacao" value="SALVAR"/>	
	</form>
	
	<script type="text/javascript">

	var janelaPai = window.opener;
	if(janelaPai) {

		document.querySelector("#janelaPai").value = "FICHA";
			
		var acessorios = [
			<%
			if(request.getAttribute("acessoriosCadastrados") != null) {
				for(Object object : (ArrayList)request.getAttribute("acessoriosCadastrados")) {
					Acessorio acessorio = (Acessorio) object;
					out.print("{");
					out.print("id:'"+acessorio.getId()+"', ");
					out.print("nome: '"+acessorio.getNomeAcessorio()+"', ");
					out.print("descricao: '"+acessorio.getDescricaoAcessorio()+"'},");
				}
			}
			%>
		];
		if(acessorios.length > 0) {
			for(var i=0; i< acessorios.length; i++) {
				var acessorio = acessorios[i];
				var option = document.createElement("option");
				option.text = acessorio.nome;
				option.value = acessorio.id;
				document.querySelector("#acessorioJaCadastradado").add(option);
			}
		
			document.querySelector("#spanSelecionarAcessorio").style.display = 'block';
		}
	}

	function adicionarAcessorio() {

		var idAcessorioSelecionado = document.querySelector("#acessorioJaCadastradado").value;
		var acessorioSelecionado;
		for(var i=0; i< acessorios.length; i++) {
			var acessorio = acessorios[i];
			if(acessorio.id == idAcessorioSelecionado) {
				acessorioSelecionado = acessorio;	
			}
		}
		if(acessorioSelecionado) {
			window.opener.adicionarAcessorio(acessorioSelecionado);
		}
		window.close();
	}
	</script>
</body>
</html>