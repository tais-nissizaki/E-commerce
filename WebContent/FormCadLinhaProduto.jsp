<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.LinhaProduto" %>
<%@page import="model.domain.Acessorio" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE LINHA DE PRODUTO::::</title>
</head>
<body>
	<form action="ControleLinhaProduto" method="post">		
		<span id="spanSelecionarLinha" style="display: none">
			<h4>Selecione uma linha de produto já cadastrada</h4>
			<select name="linhaJaCadastradada" id="linhaJaCadastradada">
			</select>
			<br />
			<br />
			<input type="button" value="Selecionar" onclick="selecionarLinha()"/>
			<h4>Ou cadastre uma nova</h4>
		</span>
		
		<label for="txtNomeLinha">Nome da Linha:</label>
		<input type="text" id="txtNomeLinha" name="txtNomeLinha"/>
		<br />
		
		<input type="button" Value="Adicionar acessório" onclick="abrirAdicionarAcessorio()" />
		<span id="spanAcessoriosAdicionados" style="display: none">
			<h4>Acessórios</h4>
			<table border="1"  id="tableListaAcessorios">
				<tr>
					<th>Nome</th>
					<th>Descrição</th> 
				</tr>
			</table>
		</span>
		<br/>
		<br/>
		
		<input type="submit" id="operacao" name="operacao" value="SALVAR"/>	
	</form>
	
	<script type="text/javascript">

	var janelaPai = window.opener;
	if(janelaPai) {
		var linhasProduto = [
		<%
		if(request.getAttribute("linhasCadastradas") != null) {
			for(Object object: (List)request.getAttribute("linhasCadastradas")) {
				LinhaProduto linhaProduto = (LinhaProduto) object;
				out.print("{");
				out.print("nome: '"+linhaProduto.getNomeLinhaProduto()+"', ");
				out.print("acessorios: [");
				for(Acessorio acessorio: linhaProduto.getAcessorio()) {
					out.print("{");
					out.print("id: '"+acessorio.getId() + "', ");
					out.print("nome: '"+acessorio.getNomeAcessorio() + "', ");
					out.print("descricao: '"+acessorio.getDescricaoAcessorio() + "'");
					out.print("}, ");
				}
				out.print("]");
				out.print("},");
			}
		}
		%>
		]; 
		if(linhasProduto.length > 0) {
			for(var i=0; i< linhasProduto.length; i++) {
				var linha = linhasProduto[i];
				var option = document.createElement("option");
				option.text = linha.nome;
				option.value = linha.nome;
				document.querySelector("#linhaJaCadastradada").add(option);
			}
		
			document.querySelector("#spanSelecionarLinha").style.display = 'block';
		}
	}

	
	function selecionarLinha() {
		var linhaSelecionada = document.querySelector("#linhaJaCadastradada").value;
		var linhaComboSelecionada;
		for(var i=0; i<linhasProduto.length; i++) {
			if(linhaSelecionada == linhasProduto[i].nome) {
				linhaComboSelecionada = linhasProduto[i];
			}
		}
		
		if(linhaComboSelecionada) {
			window.opener.atribuirLinha(linhaComboSelecionada);
		}
		window.close();
	}
	function abrirAdicionarAcessorio() {
		var win = window.open("ControleAcessorio?from=FICHA", "_blank", "modal=yes,toolbar=no,scrollbars=yes,resizable=yes,top=50,left=650,width=700,height=400");
	}
	
	function adicionarAcessorio(acessorio) {
		var trAcessorio = document.querySelector("#tableListaAcessorios").insertRow(-1);
		var tdNome = trAcessorio.insertCell(-1);
		tdNome.innerHTML = acessorio.nome;
		var tdDescricao = trAcessorio.insertCell(-1);
		tdDescricao.innerHTML = acessorio.descricao;

		var inputIdAcessorioLinha = document.createElement("input");
		inputIdAcessorioLinha.name="idAcessorioLinha";
		inputIdAcessorioLinha.type="hidden";
		inputIdAcessorioLinha.value=acessorio.id;
		document.querySelector("#spanAcessoriosAdicionados").append(inputIdAcessorioLinha);

		var inputNomeAcessorioLinha = document.createElement("input");
		inputNomeAcessorioLinha.name="nomeAcessorioLinha";
		inputNomeAcessorioLinha.type="hidden";
		inputNomeAcessorioLinha.value=acessorio.nome;
		document.querySelector("#spanAcessoriosAdicionados").append(inputNomeAcessorioLinha);

		var inputDescricaoAcessorioLinha = document.createElement("input");
		inputDescricaoAcessorioLinha.name="descricaoAcessorioLinha";
		inputDescricaoAcessorioLinha.type="hidden";
		inputDescricaoAcessorioLinha.value=acessorio.descricao;
		document.querySelector("#spanAcessoriosAdicionados").append(inputDescricaoAcessorioLinha);

		document.querySelector("#spanAcessoriosAdicionados").style.display = "block";
		
	}
	</script>
</body>
</html>