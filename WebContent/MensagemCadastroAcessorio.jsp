<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.Acessorio" %>
<%@page import="model.domain.Componente" %>
<%@page import="model.domain.TipoComponente" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>:::: CADASTRO DE ACESSÓRIO ::::</title>
</head>
<body>
	<p>Acessório cadastrado com sucesso.</p>
	<br />
	<input type="button" value="OK" onclick="redirecionar()" />
	<script type="text/javascript">
	function fecharJanela() {
		var acessorio =
			<%
			Acessorio acessorio = (Acessorio) request.getAttribute("acessorioCadastrado");
				out.print("{");
				out.print("id:'"+acessorio.getId()+"',");
				out.print("nome: '"+acessorio.getNomeAcessorio()+"', ");
				out.print("descricao: '"+acessorio.getDescricaoAcessorio()+"'");
				out.print("};");
			%>
		window.opener.adicionarAcessorio(acessorio);
		window.close();
	}
	function redirecionar() {
		window.location = "<%=request.getContextPath()%>";
	}

	<% if(request.getAttribute("janelaPai") != null && request.getAttribute("janelaPai").equals("FICHA")) { %>
		fecharJanela();
	<% } %>
	</script>
</body>
</html>
