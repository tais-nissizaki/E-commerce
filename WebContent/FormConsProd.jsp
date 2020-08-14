<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.Produto" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE PRODUTO::::</title>
</head>
<body>
	<form action="ControleProduto" method="get">		
		
		<label for="txtCodigoProduto">Código:</label>
		<input type="text" id="txtCodigoProduto" name="txtCodigoProduto"/>
		<br />
		<label for="txtNomeProduto">Produto:</label>
		<input type="text" id="txtNomeProduto" name="txtNomeProduto"/>
		<br />
		<label for="txtDescricao">Descrição:</label>
		<input type="text" id="txtDescricao" name="txtDescricao"/>
		<br />
		<label for="txtCategoria">Categoria:</label>
		<input type="text" id="txtCategoria" name="txtCategoria"/>
		<br />
		<label for="txtSubCategoria">SubCategoria:</label>
		<input type="text" id="txtSubCategoria" name="txtSubCategoria"/>
		<br />
		<label for="txtLinhaProduto">Linha:</label>
		<input type="text" id="txtLinhaProduto" name="txtLinhaProduto"/>
		<br />
		
		<label for="txtComponenteBasico">Componente Básico:</label>
		<input type="text" id="txtComponenteBasico" name="txtComponenteBasico"/>
		<br />
		<label for="txtComponentePrimario">Componente Primario:</label>
		<input type="text" id="txtComponentePrimario" name="txtComponentePrimario"/>
		<br />
		<label for="txtComponenteSecundario">Componente Secundário:</label>
		<input type="text" id="txtComponenteSecundario" name="txtComponenteSecundario"/>
		<br />
		<input type="hidden" id="txtIdProduto" name="txtIdProduto"/>
		
		<input type="submit" id="operacao" name="operacao" value="CONSULTAR"/>
		<input type="submit" id="operacaoInativar" name="operacao" value="INATIVAR" style="display: none"/>
		<input type="submit" id="operacaoCarregarAlterar" name="operacao" value="CARREGARALTERAR" style="display: none"/>
	</form>
	<table border="1">
		<tr>
			<td>Nome</td>
			<td>Linha de Produto</td>
			<td>Categoria</td>
			<td>Subcategoria</td>
			<td>Ativo</td>
			<td>Ações</td>
		</tr>
		
	<%
	if(request.getAttribute("produtosConsultados") != null) {
		List<Produto> produtos = (List<Produto>)request.getAttribute("produtosConsultados");
		for(Produto produto: produtos) { 
			out.print("<tr>");
			out.print("<td>");out.print(produto.getNomeProduto());out.print("</td>");
			out.print("<td>");out.print(produto.getLinhaProduto().getNomeLinhaProduto());out.print("</td>");
			out.print("<td>");out.print(produto.getFichaTecnica().getCategoria().getNomeCategoria());out.print("</td>");
			out.print("<td>");out.print(produto.getFichaTecnica().getSubcategoria().getNome());out.print("</td>");
			out.print("<td>");out.print(produto.isAtivo()? "ATIVO":"INATIVO");out.print("</td>");
			out.print("<td>");
				out.print("<input type=\"button\" value=\"INATIVAR\" onclick=\"inativar(");out.print(produto.getId());out.print(")\" />");
				out.print("<input type=\"button\" value=\"ALTERAR DADOS DO PRODUTO\" onclick=\"carregarAlterar(");out.print(produto.getCodigo());out.print(")\" />");
			out.print("</td>");
			out.print("<tr/>");
		}
	}
	%>
	</table>
	<script type="text/javascript">
	function inativar(idProduto) {
		document.querySelector("#txtIdProduto").value = idProduto;
		document.querySelector("#operacaoInativar").click()
	}

	function carregarAlterar(idProduto) {
		document.querySelector("#txtIdProduto").value = idProduto;
		document.querySelector("#operacaoCarregarAlterar").click()
	}
	</script>
</body>
</html>