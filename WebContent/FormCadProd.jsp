<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.Produto" %>
<%@page import="model.domain.Acessorio" %>
<%@page import="model.domain.Componente" %>
<%@page import="model.domain.TipoComponente" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE PRODUTO::::</title>
</head>
<body>
	<%
	if(request.getAttribute("mensagem") != null) {
		out.print(request.getAttribute("mensagem"));
	}
	%>
	<form action="ControleProduto" method="post">		
		<input type="hidden" id="txtIdProduto" name="txtIdProduto" />
		
		<label for="txtCodigo">Código:</label>
		<input type="text" id="txtCodigo" name="txtCodigo" />
		<br />
		
		<label for="txtNomeProduto">Nome do produto:</label>
		<input type="text" id="txtNomeProduto" name="txtNomeProduto" />
		<br />
		
		<label for="txtComprador">Comprador:</label>
		<input type="text" id="txtComprador" name="txtComprador"/>
		<br />
		
		<label for="txtQuantidade">Quantidade:</label>
		<input type="text" id="txtQuantidade" name="txtQuantidade"/>
		<br />	
		
		<label for="txtValor">Valor:</label>
		<input type="text" id="txtValor" name="txtValor"/>
		<br /> <br />
		
		<input type="button" value="Incluir Linha de Produto" id="btnIncluirLinha" onclick="abrirLinhaDeProduto()">
		<div id="divLinhaProduto" style="display: none">
			<br/>
			<label for="txtLinhaProduto">Linha de produto:</label>
			<input type="text" id="txtLinhaProduto" name="txtLinhaProduto" readonly/>
			<br/>
			<table border="1"  id="tableListaAcessoriosLinha">
				<tr>
					<th>Nome</th>
					<th>Descrição</th> 
				</tr>
			</table>
		</div>
		<br />
		<br />
		
		<input type="button" value="Incluir Ficha Técnica" id="btnIncluiFicha" onclick="abrirFichaTecnica()">
		
		<div id="dadosFichaTecnica" style="display: none">11
			<input type="hidden" id="txtIdFicha" name="txtIdFicha"/>
			<label for="txtNomeFicha">Nome da Ficha Técnica:</label>
			<input type="text" id="txtNomeFicha" name="txtNomeFicha" readonly/>
			<br/>
			<label for="txtDescricaoFicha">Descrição da Ficha Técnica:</label>
			<input type="text" id="txtDescricaoFicha" name="txtDescricaoFicha" readonly/>
			<br/>
			<label for="txtObservacoesFicha">Observações da Ficha Técnica:</label>
			<input type="text" id="txtObservacoesFicha" name="txtObservacoesFicha" readonly/>
			<br/>
			<label for="txtCategoria">Categoria:</label>
			<input type="text" id="txtCategoria" name="txtCategoria" readonly/>
			<br/>
			<label for="txtSubcategoria">Sub-Categoria:</label>
			<input type="text" id="txtSubcategoria" name="txtSubcategoria" readonly/>
			<br/>
			<label for="txtComponenteBasico">Componente Básico:</label>
			<input type="text" id="txtComponenteBasico" name="txtComponenteBasico" readonly/>
			<br/>
			<label for="txtComponentePrimario">Componente Primário:</label>
			<input type="text" id="txtComponentePrimario" name="txtComponentePrimario" readonly/>
			<br/>
			<label for="txtComponenteSecundario">Componente Secundário:</label>
			<input type="text" id="txtComponenteSecundario" name="txtComponenteSecundario" readonly/>
			<br/>
			<table id="tbAcessoriosFicha" border="1">
			<tr>
			<th>Nome</th>
			<th>Descrição</th>
			</tr>
			</table>
		</div>
		<br />	<br />
		
<!-- 		<input type="button" value="Incluir Acessorio" onclick="abrirAcessorio()"> -->
<!-- 		<span id="spanAcessoriosAdicionados" style="display: none"> -->
<!-- 			<span style="font-weight: bold">Acessórios</span> -->
<!-- 			<table border="1"  id="tableListaAcessorios"> -->
<!-- 				<tr> -->
<!-- 					<th>Nome</th> -->
<!-- 					<th>Descrição</th>  -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</span> -->
<!-- 		<br />	<br /> -->
		
		<input type="submit" id="operacaoSalvar" name="operacao" value="SALVAR"/>
		<input type="submit" id="operacaoAlterar" name="operacao" value="ALTERAR" style="display:none"/>
	
	</form>
	<script type="text/javascript">
	function abrirFichaTecnica() {
		var win = window.open("ControleFichaTecnica?from=produto", "_blank", "modal=yes,toolbar=no,scrollbars=yes,resizable=yes,top=50,left=650,width=700,height=600");
	}

	function atribuirDadosFicha(dadosFicha) {
		console.log(dadosFicha);
		
		document.querySelector("#dadosFichaTecnica").style.display = 'block';
		document.querySelector("#txtIdFicha").value = dadosFicha.id;
		document.querySelector("#txtNomeFicha").value = dadosFicha.nome;
		document.querySelector("#txtDescricaoFicha").value = dadosFicha.descricao;
		document.querySelector("#txtCategoria").value = dadosFicha.categoria;
		document.querySelector("#txtSubcategoria").value = dadosFicha.subcategoria;
		document.querySelector("#txtComponenteBasico").value = dadosFicha.componenteBASICO;
		document.querySelector("#txtComponentePrimario").value = dadosFicha.componentePRIMARIO;
		document.querySelector("#txtComponenteSecundario").value = dadosFicha.componenteSECUNDARIO;
		document.querySelector("#txtObservacoesFicha").value = dadosFicha.observacoes;

		for(var i=0; i< dadosFicha.acessorios.length; i++) {

			var acessorio = dadosFicha.acessorios[i];

			var trAcessorio = document.querySelector("#tbAcessoriosFicha").insertRow(-1);
			var tdNome = trAcessorio.insertCell(-1);
			tdNome.innerHTML = acessorio.nome;
			var tdDescricao = trAcessorio.insertCell(-1);
			tdDescricao.innerHTML = acessorio.descricao;

			
			var inputIdAcessorioFicha = document.createElement("input");
			inputIdAcessorioFicha.name="idAcessorioFicha";
			inputIdAcessorioFicha.type="hidden";
			inputIdAcessorioFicha.value=acessorio.id;
			document.querySelector("#dadosFichaTecnica").append(inputIdAcessorioFicha);

			var inputNomeAcessorioFicha = document.createElement("input");
			inputNomeAcessorioFicha.name="nomeAcessorioFicha";
			inputNomeAcessorioFicha.type="hidden";
			inputNomeAcessorioFicha.value=acessorio.nome;
			document.querySelector("#dadosFichaTecnica").append(inputNomeAcessorioFicha);

			var inputDescricaoAcessorioFicha = document.createElement("input");
			inputDescricaoAcessorioFicha.name="descricaoAcessorioFicha";
			inputDescricaoAcessorioFicha.type="hidden";
			inputDescricaoAcessorioFicha.value=acessorio.descricao;
			document.querySelector("#dadosFichaTecnica").append(inputDescricaoAcessorioFicha);
		} 
			
	}

	function abrirAcessorio() {
		var win = window.open("ControleAcessorio?from=PRODUTO", "_blank", "modal=yes,toolbar=no,scrollbars=yes,resizable=yes,top=50,left=650,width=700,height=400");
	}

	function adicionarAcessorio(acessorio) {
		var trAcessorio = document.querySelector("#tableListaAcessorios").insertRow(-1);
		var tdNome = trAcessorio.insertCell(-1);
		tdNome.innerHTML = acessorio.nome;
		var tdDescricao = trAcessorio.insertCell(-1);
		tdDescricao.innerHTML = acessorio.descricao;

		var inputIdAcessorioFicha = document.createElement("input");
		inputIdAcessorioFicha.name="idAcessorioLinha";
		inputIdAcessorioFicha.type="hidden";
		inputIdAcessorioFicha.value=acessorio.id;
		document.querySelector("#spanAcessoriosAdicionados").append(inputIdAcessorioFicha);

		var inputNomeAcessorioFicha = document.createElement("input");
		inputNomeAcessorioFicha.name="nomeAcessorioLinha";
		inputNomeAcessorioFicha.type="hidden";
		inputNomeAcessorioFicha.value=acessorio.nome;
		document.querySelector("#spanAcessoriosAdicionados").append(inputNomeAcessorioFicha);

		var inputDescricaoAcessorioFicha = document.createElement("input");
		inputDescricaoAcessorioFicha.name="descricaoAcessorioLinha";
		inputDescricaoAcessorioFicha.type="hidden";
		inputDescricaoAcessorioFicha.value=acessorio.descricao;
		document.querySelector("#spanAcessoriosAdicionados").append(inputDescricaoAcessorioFicha);

		document.querySelector("#spanAcessoriosAdicionados").style.display = "block";
	}

	function abrirLinhaDeProduto() {
		var win = window.open("ControleLinhaProduto?from=PRODUTO", "_blank", "modal=yes,toolbar=no,scrollbars=yes,resizable=yes,top=50,left=650,width=700,height=400");
	}

	function atribuirLinha(linha) {
		document.querySelector("#txtLinhaProduto").value = linha.nome;
		for(var i=0; i< linha.acessorios.length; i++) {
			var acessorio = linha.acessorios[i];
			
			var trAcessorio = document.querySelector("#tableListaAcessoriosLinha").insertRow(-1);
			var tdNome = trAcessorio.insertCell(-1);
			tdNome.innerHTML = acessorio.nome;
			var tdDescricao = trAcessorio.insertCell(-1);
			tdDescricao.innerHTML = acessorio.descricao;

			var inputIdAcessorioFicha = document.createElement("input");
			inputIdAcessorioFicha.name="idAcessorioLinha";
			inputIdAcessorioFicha.type="hidden";
			inputIdAcessorioFicha.value=acessorio.id;
			document.querySelector("#divLinhaProduto").append(inputIdAcessorioFicha);

			var inputNomeAcessorioFicha = document.createElement("input");
			inputNomeAcessorioFicha.name="nomeAcessorioLinha";
			inputNomeAcessorioFicha.type="hidden";
			inputNomeAcessorioFicha.value=acessorio.nome;
			document.querySelector("#divLinhaProduto").append(inputNomeAcessorioFicha);

			var inputDescricaoAcessorioFicha = document.createElement("input");
			inputDescricaoAcessorioFicha.name="descricaoAcessorioLinha";
			inputDescricaoAcessorioFicha.type="hidden";
			inputDescricaoAcessorioFicha.value=acessorio.descricao;
			document.querySelector("#divLinhaProduto").append(inputDescricaoAcessorioFicha);
				
		}
		
		
		document.querySelector("#divLinhaProduto").style.display = "block";
	}

	<%
	if(request.getAttribute("produtoAlteracao") != null) {
		Produto produto = (Produto)request.getAttribute("produtoAlteracao");
	%>
		document.querySelector("#txtIdProduto").value = "<%=produto.getId()%>";
		document.querySelector("#txtCodigo").value = "<%=produto.getCodigo()%>";
		document.querySelector("#txtNomeProduto").value = "<%=produto.getNomeProduto()%>";
		document.querySelector("#txtComprador").value = "<%=produto.getItemDeCompra().getCompra().getComprador()%>";
		document.querySelector("#txtValor").value = "<%=produto.getItemDeCompra().getValorDeCompra()%>";
		document.querySelector("#txtQuantidade").value = "<%=produto.getItemEmEstoque().getQuantidadeProduto()%>";
		document.querySelector("#txtLinhaProduto").value = "<%=produto.getLinhaProduto().getNomeLinhaProduto()%>";
	<%
		if(produto.getLinhaProduto().getAcessorio() != null) {
			for(Acessorio acessorio: produto.getLinhaProduto().getAcessorio()) {
	%>
				var trAcessorio = document.querySelector("#tableListaAcessoriosLinha").insertRow(-1);
				var tdNome = trAcessorio.insertCell(-1);
				tdNome.innerHTML = "<%=acessorio.getNomeAcessorio()%>";
				var tdDescricao = trAcessorio.insertCell(-1);
				tdDescricao.innerHTML = "<%=acessorio.getDescricaoAcessorio()%>";
			
				var inputIdAcessorioFicha = document.createElement("input");
				inputIdAcessorioFicha.name="idAcessorioLinha";
				inputIdAcessorioFicha.type="hidden";
				inputIdAcessorioFicha.value="<%=acessorio.getId()%>";
				document.querySelector("#divLinhaProduto").append(inputIdAcessorioFicha);
			
				var inputNomeAcessorioFicha = document.createElement("input");
				inputNomeAcessorioFicha.name="nomeAcessorioLinha";
				inputNomeAcessorioFicha.type="hidden";
				inputNomeAcessorioFicha.value="<%=acessorio.getNomeAcessorio()%>";
				document.querySelector("#divLinhaProduto").append(inputNomeAcessorioFicha);
			
				var inputDescricaoAcessorioFicha = document.createElement("input");
				inputDescricaoAcessorioFicha.name="descricaoAcessorioLinha";
				inputDescricaoAcessorioFicha.type="hidden";
				inputDescricaoAcessorioFicha.value="<%=acessorio.getDescricaoAcessorio()%>";
				document.querySelector("#divLinhaProduto").append(inputDescricaoAcessorioFicha);
	<%		} %>
			document.querySelector("#divLinhaProduto").style.display = "block"
	<%	} %>

	
	document.querySelector("#txtIdFicha").value = "<%=produto.getFichaTecnica().getId()%>";
	document.querySelector("#txtNomeFicha").value = "<%=produto.getFichaTecnica().getNomeFicha()%>";
	document.querySelector("#txtDescricaoFicha").value = "<%=produto.getFichaTecnica().getDescricao()%>";
	document.querySelector("#txtCategoria").value = "<%=produto.getFichaTecnica().getCategoria().getNomeCategoria()%>";
	document.querySelector("#txtSubcategoria").value = "<%=produto.getFichaTecnica().getSubcategoria().getNome()%>";
	document.querySelector("#txtObservacoesFicha").value = "<%=produto.getFichaTecnica().getObservacoes()%>";

	<%
		if(produto.getFichaTecnica().getComponentes() != null) {
			for(Componente componente: produto.getFichaTecnica().getComponentes()) {
				if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.BASICO)) { %>
					document.querySelector("#txtComponenteBasico").value = "<%=componente.getNomeComponente()%>";
	<%			}%>
	<%			if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.PRIMARIO)) { %>
					document.querySelector("#txtComponentePrimario").value = "<%=componente.getNomeComponente()%>";
	<%			}%>
	<%			if(componente.getTipoComponente().getNomeTipoComponente().equals(TipoComponente.SECUNDARIO)) { %>
					document.querySelector("#txtComponenteSecundario").value = "<%=componente.getNomeComponente()%>";
	<%			}%>
	<%		} %>
	<%	} %>
		
	<%
		if(produto.getFichaTecnica().getAcessorios() != null) {
			for(Acessorio acessorio: produto.getFichaTecnica().getAcessorios()) {
	%>
			var trAcessorio = document.querySelector("#tbAcessoriosFicha").insertRow(-1);
			var tdNome = trAcessorio.insertCell(-1);
			tdNome.innerHTML = "<%=acessorio.getNomeAcessorio()%>";
			var tdDescricao = trAcessorio.insertCell(-1);
			tdDescricao.innerHTML = "<%=acessorio.getDescricaoAcessorio()%>";
		
			
			var inputIdAcessorioFicha = document.createElement("input");
			inputIdAcessorioFicha.name="idAcessorioFicha";
			inputIdAcessorioFicha.type="hidden";
			inputIdAcessorioFicha.value="<%=acessorio.getId()%>"
			document.querySelector("#dadosFichaTecnica").append(inputIdAcessorioFicha);
		
			var inputNomeAcessorioFicha = document.createElement("input");
			inputNomeAcessorioFicha.name="nomeAcessorioFicha";
			inputNomeAcessorioFicha.type="hidden";
			inputNomeAcessorioFicha.value="<%=acessorio.getNomeAcessorio()%>";
			document.querySelector("#dadosFichaTecnica").append(inputNomeAcessorioFicha);
		
			var inputDescricaoAcessorioFicha = document.createElement("input");
			inputDescricaoAcessorioFicha.name="descricaoAcessorioFicha";
			inputDescricaoAcessorioFicha.type="hidden";
			inputDescricaoAcessorioFicha.value="<%=acessorio.getDescricaoAcessorio()%>"
			document.querySelector("#dadosFichaTecnica").append(inputDescricaoAcessorioFicha);
	<%		} %>
	<%	} %>

	document.querySelector("#dadosFichaTecnica").style.display = 'block';
	document.querySelector("#btnIncluiFicha").style.display = 'none';
	document.querySelector("#btnIncluiFicha").style.display = 'none';	

	document.querySelector("#operacaoSalvar").style.display = 'none';
	document.querySelector("#operacaoAlterar").style.display = 'block';
	document.querySelector("#txtLinhaProduto").readOnly  = false;
	
	<%
	}
	%>
	</script>
</body>
</html>