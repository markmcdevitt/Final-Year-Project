<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<script type="text/javascript">
	function createTextBox(n) {

		for (var i = 0; i < n; i++) {
			var textBox = document.createElement("input");

			textBox.setAttribute("name", "ingredientName");
			textBox.setAttribute("class", "form-control");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("align", "bottom");
			textBox.setAttribute("placeholder", "Name");
			document.getElementById("container2").appendChild(textBox);

		}
	}
</script>
<br>
<br>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="well well-sm">
				<form id="details" method="post"
					action="${pageContext.request.contextPath}/findrecipe">
					<fieldset>
						<legend class="text-center">Recipe Generator</legend>

						<div class="form-group">
							<label class="col-md-3 control-label">Ingredient</label>
							<div class="col-md-9">
							<input class="form-control" name="ingredientName" placeholder="Name" type="text"></input>
							<div id="container2"></div>
							<input type="button" onclick="createTextBox(1)" value="Add Ingredient" class="mybutton btn">
							<div class="form-group">
								<div class="col-md-12 text-right">
									<button type="submit" class="btn btn-primary btn-lg">Submit</button>
								</div>
							</div>
						</div>
						</div>
					</fieldset>
				</form>
			</div>	
		</div>
	</div>
</div>

