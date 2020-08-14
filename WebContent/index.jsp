<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="FormCadProd.jsp">Cadastro de Produto</a> <br/>
<a href="FormConsProd.jsp">Consultar Produtos</a> <br/>
<a href="FormCadFicha.jsp">Cadastrar Ficha Técnica</a> <br/>
<a href="FormCadAcessorio.jsp">Cadastrar Acessório</a> <br/>
<br />
<br />
<br />
<%
if(request.getAttribute("mensagem") != null) {
	out.print(request.getAttribute("mensagem"));
}
%>


</body>
</html>