<%@page import="model.domain.Acessorio"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.FichaTecnica" %>
<%@page import="model.domain.Componente" %>
<%@page import="model.domain.TipoComponente" %>
<%@page import="model.domain.Acessorio" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>:::: CADASTRO DE PRODUTOS ::::</title>
</head>
<body>
	<p><%=request.getAttribute("mensagem") %></p>
	<br />
	<input type="button" value="OK" onclick="redirecionar()" />
	<script type="text/javascript">
	function redirecionar() {
		window.location = '<%= request.getContextPath() %>'
	}
	</script>
</body>
</html>
