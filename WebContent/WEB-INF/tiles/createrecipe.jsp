<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">

<script type="text/javascript">
	function createTextBox(n) {
		for (var i = 0; i < n; i++) {
			var textBox = document.createElement("input");

			textBox.setAttribute("name", "instructions");
			textBox.setAttribute("class", "form-control");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("align", "bottom");
			textBox.setAttribute("placeholder", "Next Step");

			document.getElementById("container1").appendChild(textBox);
		}
	}
	function createTextBox2(n) {

		for (var i = 0; i < n; i++) {
			var textBox2 = document.createElement("input");

			textBox2.setAttribute("name", "ingredientQuantity");
			textBox2.setAttribute("class", "form-control");
			textBox2.setAttribute("type", "text");
			textBox2.setAttribute("width", "2");
			textBox2.setAttribute("placeholder", "Quantity");
			document.getElementById("container2").appendChild(textBox2);

			var textBox = document.createElement("input");

			textBox.setAttribute("name", "ingredientName");
			textBox.setAttribute("class", "form-control");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("align", "bottom");
			textBox.setAttribute("placeholder", "Measurement And Name");
			document.getElementById("container2").appendChild(textBox);

		}
	}
</script>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="well well-sm">
				<form class="form-horizontal"
					action="${pageContext.request.contextPath}/docreate" method="post">

					<fieldset>
						<legend class="text-center">Create Recipe</legend>

						<div class="form-group">
							<label class="col-md-3 control-label">Title</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Title"
									name="titleParse" type="text"></input><br />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Description</label>
							<div class="col-md-9">
								<textarea class="form-control" placeholder="Description"
									name="descriptionParse" rows="5" cols="5"></textarea>
								<br />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Category</label>
							<div class="col-md-7">
								<select name="type" class="form-control">
									<option value="Appetisers">Appetisers</option>
									<option value="Main Dish">Main Dish</option>
									<option value="Vegetarian">Vegetarian</option>
									<option value="Dessert">Dessert</option>
								</select> <br />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Calories</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="400" name="calories"
									type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Serves</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="4 People"
									name="peopleFed" type="text" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3 control-label">Image</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Picture"
									name="imageURLParse" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Instructions</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Next Step"
									name="instructions" type="text" />
								<div id="container1"></div>
								<input type="button" class="mybutton btn"
									onclick="createTextBox(1)" value="Add a Step"><br />

							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label" for="message">Ingredients</label>
							<div class="col-md-9">

								<input class="form-control" placeholder="Quantity"
									name="ingredientQuantity" type="text" width="2"></input> <input
									class="form-control" name="ingredientName"
									placeholder="measurement and name" type="text"></input>


								<div id="container2"></div>
								<input type="button" onclick="createTextBox2(1)"
									value="Add Ingredient" class="mybutton btn">
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-12 text-right">
								<button type="submit" class="btn btn-primary btn-lg">Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>