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
	<title>:::: CADASTRO DE FICHA TECNICA ::::</title>
</head>
<body>
	<p>Ficha técnica cadastrada com sucesso.</p>
	<br />
	<input type="button" value="OK" onclick="redirecionar()" />
	<script type="text/javascript">
	function fecharJanela() {
		var ficha =
			<%
				FichaTecnica ficha = (FichaTecnica) request.getAttribute("fichaCadastrada");
				out.print("{");
				out.print("id:'"+ficha.getId()+"',");
				out.print("nome: '"+ficha.getNomeFicha()+"', ");
				out.print("descricao: '"+ficha.getDescricao()+"', ");
				out.print("categoria: '"+ficha.getCategoria().getNomeCategoria()+"', ");
				out.print("subcategoria: '"+ficha.getSubcategoria().getNome()+"', ");
				out.print("observacoes: '"+ficha.getObservacoes()+"', ");
				for(Componente componente: ficha.getComponentes()) {
					out.print("componente"+componente.getTipoComponente().getNomeTipoComponente()+": '" + componente.getNomeComponente()+"',");
				}
				out.print("acessorios: [");
				for(Acessorio acessorio: ficha.getAcessorios()) {
					out.print("{");
					out.print("id: '"+acessorio.getId() + "', ");
					out.print("nome: '"+acessorio.getNomeAcessorio() + "', ");
					out.print("descricao: '"+acessorio.getDescricaoAcessorio() + "'");
					out.print("}, ");
				}
				out.print("]");
				out.print("};");
			%>
		window.opener.atribuirDadosFicha(ficha);
		window.close();
	}

	function redirecionar() {
		window.location = "<%=request.getContextPath()%>";
	}
	<% if(request.getAttribute("janelaPai") != null && request.getAttribute("janelaPai").equals("PRODUTO")) { %>
		fecharJanela();
	<% } %>
	</script>
</body>
</html>
