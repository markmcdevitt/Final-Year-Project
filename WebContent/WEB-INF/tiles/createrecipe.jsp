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
			textBox.setAttribute("required", "required");
			document.getElementById("container1").appendChild(textBox);
		}
	}
	function createTextBox2(n) {

		for (var i = 0; i < n; i++) {
			var textBox2 = document.createElement("input");

			textBox2.setAttribute("name", "ingredientQuantity");
			textBox2.setAttribute("class", "form-control");
			textBox2.setAttribute("type", "text");
			textBox2.setAttribute("align", "left");
			textBox2.setAttribute("style", "width:80px");
			textBox2.setAttribute("placeholder", "Quantity");
			textBox2.setAttribute("required", "required");
			document.getElementById("container").appendChild(textBox2);

			var textBox = document.createElement("input");

			textBox.setAttribute("name", "ingredientName");
			textBox.setAttribute("class", "form-control");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("align", "right");
			textBox.setAttribute("style", "width:170px");
			textBox.setAttribute("placeholder", "Measurement & Name");
			document.getElementById("container2").appendChild(textBox);

		}
	}
</script>

<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="well well-sm">
				<sf:form class="form-horizontal" onsubmit="return validateForm()"
					name="myForm" action="${pageContext.request.contextPath}/docreate"
					method="post" >

					<fieldset>
						<legend class="text-center">Create Recipe</legend>

						<div class="form-group">
							<label class="col-md-3 control-label">Title</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Title"
									name="titleParse" type="text"  maxlength="200" required="required">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">Description</label>
							<div class="col-md-9">
								<textarea class="form-control" placeholder="Description" style="width:63%"
									 name="descriptionParse" rows="5" required="required"
									cols="5" maxlength="200"></textarea>
								
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Category</label>
							<div class="col-md-7">
								<select style="width:83%;" name="type" class="form-control">
									<option value="Appetisers">Appetisers</option>
									<option value="Main Dish">Main Dish</option>
									<option value="Vegetarian">Vegetarian</option>
									<option value="Dessert">Dessert</option>
								</select> 
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Calories</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="400" name="calories"
									type="number" style="width: 63%;"/> <input id="caloriesHidden" name="calories"
									type="hidden" value="no">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Serves</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="4 People"
									name="peopleFed" type="text" onkeypress='validate(event)'required="required" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3 control-label">Image</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Picture"
									path="imageURLParse" name="imageURLParse" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label">Instructions</label>
							<div class="col-md-9">
								<input class="form-control" placeholder="Next Step"
									name="instructions" type="text"
									data-fv-notempty-message="An instruction is required" required="required"/>
								<div id="container1"></div>

								<br> <input type="button" class="btn btn-info"
									onclick="createTextBox(1)" value="Add a Step"><br />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label" for="message">Ingredients</label>
							<div class="col-md-9">
								<div class="leftDiv">
									<input class="form-control" placeholder="Quantity"
										name="ingredientQuantity"  type="text" style="width: 80px;"
										 required="required"></input>
								</div>
								<div class="rightDiv">
									<input class="form-control" name="ingredientName"
										placeholder="Measurement & Name" type="text" style="width: 170px;" required="required"></input>
								</div>

								<div class="leftDiv" id="container" ></div>
								<div class="rightDiv" id="container2"></div>
								<br><input type="button" onclick="createTextBox2(1)"
									value="Add Ingredient" class="btn btn-info">
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-12 text-center">
								<button type="submit" class="btn btn-primary btn-lg">Submit</button>
							</div>
						</div>
					</fieldset>
				</sf:form>
			</div>
		</div>
	</div>
</div>
<style>
.leftDiv {
	float: left;
	width:20px;
}

.rightDiv {
	margin-left: 20%;
}
</style>
<script>
	function validateForm() {
		var x = document.forms["myForm"]["titleParse"].value;
		var y = document.forms["myForm"]["descriptionParse"].value;
		var s = document.forms["myForm"]["peopleFed"].value;
		var p = document.forms["myForm"]["imageURLParse"].value;
		var i = document.forms["myForm"]["instructions"].value;
		var n = document.forms["myForm"]["ingredientName"].value;
		var q = document.forms["myForm"]["ingredientQuantity"].value;

		if (x == null || x == "") {
			alert("Title must be filled out");
			return false;
		}
		if (y == null || y == "") {
			alert("Description must be filled out");
			return false;
		}
		if (s == null || s == "") {
			alert("Serves must be filled out");
			return false;
		}
		if (p == null || p == "") {
			alert("Image must be filled out");
			return false;
		}

	}
</script>
<script>
	function validate(evt) {
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		key = String.fromCharCode(key);
		var regex = /[0-9]|\./;
		if (!regex.test(key)) {
			theEvent.returnValue = false;
			if (theEvent.preventDefault)
				theEvent.preventDefault();
		}
	}

	if (document.getElementById("calories").checked) {
		document.getElementById('caloriesHidden').disabled = true;
	}
</script>