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
														<h4 class="sub-title">Cadastro telefone</h4>
														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletTelefonee"
															method="post" id="formFone">
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID
																	Usuário:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input readonly="readonly" type="text" name="nome"
																	id="nome" class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required" value="">
																<span class="form-bar"></span> <label
																	class="float-label">Número:</label>
															</div>
															<button type="submit"
																class="btn btn-success waves-effect waves-light">Salvar</button>
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
														<th scope="col">Número</th>
														<th scope="col">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelTelefones}' var='f'>
														<tr>
															<td><c:out value="${f.id}"></c:out></td>
															<td><c:out value="${f.numero}"></c:out></td>
															<td><a class="btn btn-danger"
																href="<%= request.getContextPath() %>/ServletTelefonee?acao=excluir&id=${f.id}&userpai=${modelLogin.id}">Excluir</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
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
	<script type="text/javascript">
		$("#numero").keypress(function(event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
	</script>
</body>

</html>