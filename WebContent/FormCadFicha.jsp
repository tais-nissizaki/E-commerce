<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.domain.FichaTecnica" %>
<%@page import="model.domain.Componente" %>
<%@page import="model.domain.Acessorio" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE FICHA TECNICA::::</title>
</head>
<body>
	<form action="ControleFichaTecnica" method="post">
		<span id="spanSelecionarFicha" style="display: none">
			<h4>Selecione uma ficha já cadastrada</h4>
			<select name="fichaJaCadastradada" id="fichaJaCadastradada">
			</select>
			<br />
			<br />
			<input type="button" value="Selecionar" onclick="selecionarFicha()"/>
			<h4>Ou cadastre uma nova</h4>
		</span>
		
	
		<label for="txtNomeFicha">Nome:</label>
		<input type="text" id="txtNomeFicha" name="txtNomeFicha"/>
		<br/>
		<label for="txtDescricaoFicha">Descrição:</label>
		<input type="text" id="txtDescricaoFicha" name="txtDescricaoFicha"/>
		<br/>
		<label for="txtObservacoesFicha">Observações:</label>
		<input type="text" id="txtObservacoesFicha" name="txtObservacoesFicha"/>
		<br/>
		<label for="txtCategoria">Categoria:</label>
		<input type="text" id="txtCategoria" name="txtCategoria"/>
		<br/>
		<label for="txtSubcategoria">Sub-Categoria:</label>
		<input type="text" id="txtSubcategoria" name="txtSubcategoria"/>
		<br/>
		<label for="txtComponenteBasico">Componente Básico:</label>
		<input type="text" id="txtComponenteBasico" name="txtComponenteBasico"/>
		<br/>
		<label for="txtComponentePrimario">Componente Primário:</label>
		<input type="text" id="txtComponentePrimario" name="txtComponentePrimario"/>
		<br/>
		<label for="txtComponenteSecundario">Componente Secundário:</label>
		<input type="text" id="txtComponenteSecundario" name="txtComponenteSecundario"/>
		<br/>
		
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
		<input type="hidden" name="janelaPai" id="janelaPai" value=""/>
	</form>
	<script type="text/javascript">
		var janelaPai = window.opener;
		//Se existe janela pai, quer dizer que chegou aqui a partir do formulário de produtos
		if (janelaPai) {
			document.querySelector("#janelaPai").value = "PRODUTO";
			var fichas = [
				<%
				if(request.getAttribute("fichasCadastradas") != null) {
					for(Object object: (ArrayList)request.getAttribute("fichasCadastradas")) {
						FichaTecnica ficha = (FichaTecnica) object;
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
						if(ficha.getAcessorios() != null) {
							for(Acessorio acessorio: ficha.getAcessorios()) {
								out.print("{");
								out.print("id: '"+acessorio.getId() + "', ");
								out.print("nome: '"+acessorio.getNomeAcessorio() + "', ");
								out.print("descricao: '"+acessorio.getDescricaoAcessorio() + "'");
								out.print("}, ");
							}
						}
						out.print("]");
						out.print("},");
					}
				}
				%>
			];
			if(fichas.length > 0) {
				for(var i=0; i< fichas.length; i++) {
					var ficha = fichas[i];
					var option = document.createElement("option");
					option.text = ficha.nome;
					option.value = ficha.id;
					document.querySelector("#fichaJaCadastradada").add(option);
				}
			
				document.querySelector("#spanSelecionarFicha").style.display = 'block';
			}
			console.log(fichas);
		} else {
			document.querySelector("#janelaPai").value = "FICHA";
		}

		function selecionarFicha() {
			var idFichaSelecionada = document.querySelector("#fichaJaCadastradada").value;
			var fichaSelecionada;
			for(var i=0; i< fichas.length; i++) {
				var ficha = fichas[i];
				if(ficha.id == idFichaSelecionada) {
					fichaSelecionada = ficha;	
				}
			}
			if(fichaSelecionada) {
				window.opener.atribuirDadosFicha(fichaSelecionada);
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

			var inputIdAcessorioFicha = document.createElement("input");
			inputIdAcessorioFicha.name="idAcessorioFicha";
			inputIdAcessorioFicha.type="hidden";
			inputIdAcessorioFicha.value=acessorio.id;
			document.querySelector("#spanAcessoriosAdicionados").append(inputIdAcessorioFicha);

			var inputNomeAcessorioFicha = document.createElement("input");
			inputNomeAcessorioFicha.name="nomeAcessorioFicha";
			inputNomeAcessorioFicha.type="hidden";
			inputNomeAcessorioFicha.value=acessorio.nome;
			document.querySelector("#spanAcessoriosAdicionados").append(inputNomeAcessorioFicha);

			var inputDescricaoAcessorioFicha = document.createElement("input");
			inputDescricaoAcessorioFicha.name="descricaoAcessorioFicha";
			inputDescricaoAcessorioFicha.type="hidden";
			inputDescricaoAcessorioFicha.value=acessorio.descricao;
			document.querySelector("#spanAcessoriosAdicionados").append(inputDescricaoAcessorioFicha);

			document.querySelector("#spanAcessoriosAdicionados").style.display = "block";
			
		}
		
	</script>
</body>
</html>