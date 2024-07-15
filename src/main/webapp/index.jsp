<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<title>Curso JSP</title>

<style type="text/css">
	form {
		position: absolute;
		top: 40%;
		left: 33%;
		right: 33%;
	}
	
	h5 {
		position: absolute;
		top: 30%;
		left: 33%;
		right: 33%;
	}
	
	h6 {
		position: absolute;
		top: 65%;
		left: 33%;
		right: 33%;
		font-size: 15px;
		color: red;
	}
</style>
</head>
<body>

	<h5>Bem vindo ao curso de JSP</h5>

	<form action="ServletLoginn" method="post" class="row g-3 needs-validation" novalidate>
		<input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">
		<div class="col-md-6">
			<label class="form-label" for="login">Login: </label> 
			<input name="login" id="login" type="text" class="form-control" required="required">
			<div class="valid-feedback">
      			OK
    		</div>
    		<div class="invalid-feedback">
      			Informe o login.
    		</div>
		</div>

		<div class="col-md-6">
			<label class="form-label" for="senha">Senha: </label> 
			<input name="senha" type="password" class="form-control" id="senha" required="required">
			<div class="valid-feedback">
      			OK
    		</div>
    		<div class="invalid-feedback">
      			Informe a senha.
    		</div>
		</div>
		
		<input class="btn btn-primary" type="submit" value="Enviar">

	</form>

	<h6>${msg}</h6>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	<script type="text/javascript">
		(() => {
		  'use strict'

		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  const forms = document.querySelectorAll('.needs-validation')

		  // Loop over them and prevent submission
		  Array.from(forms).forEach(form => {
		    form.addEventListener('submit', event => {
		      if (!form.checkValidity()) {
		        event.preventDefault()
		        event.stopPropagation()
		      }

		      form.classList.add('was-validated')
		    }, false)
		  })
		})()	
	</script>
</body>
</html>
