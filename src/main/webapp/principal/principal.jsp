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
											<h1>Conteúdo das páginas do Sistema</h1>
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
</body>

</html>