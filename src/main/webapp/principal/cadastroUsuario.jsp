<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocapitalize="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">Email: (exa@gmail.com)</label>
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
															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">Novo</button>
															<button type="submit"
																class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="excluirUsuarioComAjax();">Excluir</button>
															<button type="button" class="btn btn-secundary"
																data-toggle="modal" data-target="#exampleModalUsuario">
																Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>
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

	<!-- Arquivo aonde estão os links do js -->
	<jsp:include page="javaScriptFile.jsp"></jsp:include>

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
							<button class="btn btn-primary" type="button" onclick="buscarUsuario();">Buscar</button>
						</div>
					</div>
				</div>
				<table class="table">
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
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
		function buscarUsuario() {
			var nomeBusca = document.getElementById('nomeBusca').value;
			
			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){ //Validando o que está vindo
				alert(nomeBusca);
			}
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
				})
			}
		}

		function excluirUsuario() {

			if (confirm('Deseja realmente excluir?')) {

				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		//Função que reseta formulário para criar um novo usuário
		function limparForm() {
			var elementos = document.getElementById("formUser").elements; //Retorna os elementos html dentro do form

			for (var p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>
</body>

</html>