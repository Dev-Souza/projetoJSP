<%@page import="com.fasterxml.jackson.annotation.JsonCreator.Mode"%>
<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<!-- Aonde está nosso head com suas configurações -->
<jsp:include page="head.jsp"></jsp:include>

<body>

	<!-- Arquivo do theme-loader -->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<!-- Barra de navegação -->
			<jsp:include page="navBar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- Nav Bar Main Menu -->
					<jsp:include page="navBarMainMenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<!-- Arquivo do Header do meu dashboard -->
						<jsp:include page="page-header.jsp"></jsp:include>


						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro usuário</h4>
														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>

															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	<c:if
																		test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
																		<a
																			href="<%= request.getContextPath()%>/ServletUsuarioControlleer?acao=downloadFoto&id=${modelLogin.id}">
																			<img alt="Imagem Usuário" id="fotoembase64"
																			src="${modelLogin.fotouser}" width="70px">
																		</a>
																	</c:if>
																	<c:if
																		test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}">
																		<img alt="Imagem Usuário" id="fotoembase64"
																			src="./assets/images/imagensMinha/usuarioSemFoto.jpg"
																			width="70px">
																	</c:if>
																</div>
																<input type="file" id="fileFoto" name="fileFoto"
																	accept="image/*"
																	onchange="visualizarImg('fotoembase64', 'fileFoto');"
																	class="form-control-file"
																	style="margin-top: 15px; margin-left: 5px;">
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="datanascimento" id="datanascimento" class="form-control" required="required" value="${modelLogin.datanascimento}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Nascimento:</label>
                                                            </div> 
                                                            <div class="form-group form-default form-static-label">
																<input type="text" name="rendamensal" id="rendamensal"
																	class="form-control" required="required"
																	value="${modelLogin.rendamensal}"> <span
																	class="form-bar"></span> <label class="float-label">Renda Mensal:</label>
															</div>                                                
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocapitalize="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">Email: (exa@gmail.com)</label>
															</div>
															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option selected disabled="disabled">[Selecione
																		o perfil]</option>
																	<option value="ADMIN"
																		<%ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
	out.print("  ");
	out.print("selected=\"selected\"");
	out.print("  ");
}%>>Administrador</option>
																	<option value="SECRETARIA"
																		<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")) {
	out.print("  ");
	out.print("selected=\"selected\"");
	out.print("  ");
}%>>Secretária</option>
																	<option value="AUXILIAR"
																		<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")) {
	out.print("  ");
	out.print("selected=\"selected\"");
	out.print("  ");
}%>>Auxiliar</option>
																</select> <span class="form-bar"></span> <label
																	class="float-label">Perfil:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" onblur="pesquisarCep();" name="cep"
																	id="cep" class="form-control" required="required"
																	value="${modelLogin.cep}"> <span
																	class="form-bar"></span> <label class="float-label">Cep:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro"
																	class="form-control" required="required"
																	value="${modelLogin.logradouro}"> <span
																	class="form-bar"></span> <label class="float-label">Logradouro:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro"
																	class="form-control" required="required"
																	value="${modelLogin.bairro}"> <span
																	class="form-bar"></span> <label class="float-label">Bairro:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade"
																	class="form-control" required="required"
																	value="${modelLogin.localidade}"> <span
																	class="form-bar"></span> <label class="float-label">Localidade:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf"
																	class="form-control" required="required"
																	value="${modelLogin.uf}"> <span
																	class="form-bar"></span> <label class="float-label">UF:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	value="${modelLogin.numero}"> <span
																	class="form-bar"></span> <label class="float-label">Número:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	value="${modelLogin.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocapitalize="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="radio" name="sexo" value="MASCULINO"
																	<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")) {
	out.print("  ");
	out.print("checked=\"checked\"");
	out.print("  ");
}%>>Masculino</>
																<input type="radio" name="sexo" value="FEMININO"
																	<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
if (modelLogin != null && modelLogin.getSexo().equals("FEMININO")) {
	out.print("  ");
	out.print("checked=\"checked\"");
	out.print("  ");
}%>>Feminino</>
															</div>
															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">Novo</button>
															<button type="submit"
																class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="excluirUsuario();">Excluir</button>
															<c:if test="${modelLogin.id > 0}">
																<a href="<%= request.getContextPath()%>/ServletTelefonee?idUser=${modelLogin.id}" class="btn btn-primary waves-effect waves-light">Telefone</a>
															</c:if>
															<button type="button" class="btn btn-secundary"
																data-toggle="modal" data-target="#exampleModalUsuario">
																Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>
										<div style="height: 350px; overflow: scroll;">
											<table class="table" id="tabelaResultadosView">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelLogins}' var='ml'>
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-primary"
																href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<!-- Paginação da minha linha de usuários -->
										<nav aria-label="Page navigation example">
											<ul class="pagination">
												<%
												int totalPagina = (int) request.getAttribute("totalPagina");

												for (int i = 0; i < totalPagina; i++) {
													String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (i * 5);
													out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (i + 1) + "</a></li>");
												}
												%>

											</ul>
										</nav>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome"
							aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-primary" type="button"
								onclick="buscarUsuarioComAjax();">Buscar</button>
						</div>
					</div>
				</div>
				<div style="height: 350px; overflow: scroll;">
					<table class="table" id="tabelaResultados">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Nome</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>

				<nav aria-label="Page navigation example">
					<ul class="pagination" id="ulPaginacaoUserAjax">

					</ul>
				</nav>

				<span id="totalResultados" style="padding: 10px 0px 5px 5px"></span>
				<div class="modal-footer">

					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Arquivo aonde estão os links do js -->
	<jsp:include page="javaScriptFile.jsp"></jsp:include>
	<script type="text/javascript">
		$("#rendamensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",", thousands:"."});
	
		const formatter = new Intl.NumberFormat('pt-BR', {
			currency : 'BRL',
			minimumFractionDigits : 2
		});
		
		$("#rendamensal").val(formatter.format($("#rendamensal").val()));
		
		$("#rendamensal").focus();
		
		var dataNascimento = $("#datanascimento").val();
		
		if(dataNascimento != null && dataNascimento != '') {
			var dateFormat = new Date(dataNascimento);
			$("#datanascimento").val(dateFormat.toLocaleDateString('pt-BR', {timeZone: 'UTC'}));			
		}
		
		$("#nome").focus();
		
		$( function() {
			  $("#datanascimento").datepicker({
				    dateFormat: 'dd/mm/yy',
				    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
				    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
				    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
				    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
				    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
				    nextText: 'Próximo',
				    prevText: 'Anterior'
				});
		} );
	
		$("#numero, #cep").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
		
		function pesquisarCep() {
			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#cep").val(dados.cep);
							$("#logradouro").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#localidade").val(dados.localidade);
							$("#uf").val(dados.uf);
						}
					});
		}

		function visualizarImg(fotoembase64, filefoto) {
			var preview = document.getElementById(fotoembase64);
			if (!preview) {
				console.error("Elemento de visualização não encontrado");
				return;
			}

			var fileInput = document.getElementById(filefoto);
			if (!fileInput) {
				console.error("Elemento de entrada de arquivo não encontrado");
				return;
			}

			var fileUser = fileInput.files[0];
			if (!fileUser) {
				console.error("Nenhum arquivo selecionado");
				preview.src = '';
				return;
			}

			var reader = new FileReader();
			reader.onloadend = function() {
				preview.src = reader.result; // Carrega a foto na tela
			};

			reader.readAsDataURL(fileUser); // Preview da imagem
		}

		// Adiciona a função ao escopo global
		window.visualizarImg = visualizarImg;
		window.pesquisarCep = pesquisarCep;

		function visualizarEditar(id) {
			var urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
		}

		function buscarUsuarioComAjax() {
			var nomeBusca = document.getElementById('nomeBusca').value;
			if (nomeBusca != null && nomeBusca != ''  && nomeBusca.trim() != '') {
				var urlAction = document.getElementById('formUser').action;
				$
						.ajax(
								{
									method : "get",
									url : urlAction,
									data : "nomeBusca=" + nomeBusca
											+ "&acao=buscarajax",
									success : function(response, textStatus,
											xhr) {
										var json = JSON.parse(response);
										$('#tabelaResultados > tbody > tr')
												.remove();
										$("#ulPaginacaoUserAjax > li").remove();

										for (var i = 0; i < json.length; i++) {
											$('#tabelaResultados > tbody')
													.append(
															'<tr><td>'
																	+ json[i].id
																	+ '</td><td>'
																	+ json[i].nome
																	+ '</td><td><button type="button" class="btn btn-info" onclick="visualizarEditar('
																	+ json[i].id
																	+ ')">Visualizar</button></td></tr>');
										}
										document
												.getElementById('totalResultados').textContent = 'Resultados: '
												+ json.length;

										var totalPagina = xhr.getResponseHeader("totalPagina");

										for (var i = 0; i < totalPagina; i++) {

											var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (i * 5);
											$("#ulPaginacaoUserAjax").append(
													'<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPageAjax(\'' + url + '\')">' + (i + 1)
															+ '</a></li>');
										}
									}
								}).fail(
								function(xhr, status, errorThrown) {
									alert('Erro ao buscar usuário por nome!'
											+ xhr.responseText);
								});
			}
		}

		function buscarUserPageAjax(url) {
			var urlAction = document.getElementById('formUser').action;
			var nomeBusca = document.getElementById('nomeBusca').value;
			$.ajax({
				method : "get",
				url : urlAction,
				data : url,
				success : function(response, textStatus, xhr) {
				var json = JSON.parse(response);
				
				$('#tabelaResultados > tbody > tr').remove();
				$("#ulPaginacaoUserAjax > li").remove();

				for (var i = 0; i < json.length; i++) {
					$('#tabelaResultados > tbody').append('<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td><button type="button" class="btn btn-info" onclick="visualizarEditar(' + json[i].id + ')">Visualizar</button></td></tr>');
									}
									document.getElementById('totalResultados').textContent = 'Resultados: '
											+ json.length;

									var totalPagina = xhr.getResponseHeader("totalPagina");

									for (var i = 0; i < totalPagina; i++) {
										var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (i * 5);
										$("#ulPaginacaoUserAjax").append(
												'<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPageAjax(\'' + url + '\')">' + (i + 1)
														+ '</a></li>');
									}
								}
							}).fail(
							function(xhr, status, errorThrown) {
								alert('Erro ao buscar usuário por nome!'
										+ xhr.responseText);
							});
		}

		function excluirUsuarioComAjax() {
			if (confirm('Deseja realmente excluir?')) {
				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;
				$.ajax({
					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=deletarajax",
					success : function(response) {
						limparForm();
						alert(response);
					}
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro ao deletar usuário!' + xhr.responseText);
				});
			}
		}

		function excluirUsuario() {
			if (confirm('Deseja realmente excluir?')) {
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function limparForm() {
			var elementos = document.getElementById("formUser").elements;
			for (var p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>
</body>

</html>