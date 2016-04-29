<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">

<style>
.row-sep {
	padding-top: 10px;
	padding-bottom: 10px;
}

.mybutton {
	margin: 5px;
}
</style>

<div class="leftDiv">
	<div class="row">
		<div class="col-xs-12 col-md-10">
			<c:forEach var="user" items="${userList}">
				<c:if test="${user.shoppingList.size()==0}">
					<p>You have no ingredients in your Shopping List</p>
					<p>Get started by finding a recipe you like.</p>
					<p>
						<a href="${pageContext.request.contextPath}/allrecipes">Recipes</a>
					</p>
				</c:if>
				<c:if test="${user.shoppingList.size()>0}">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Your Shopping List</h3>
						</div>
						<c:forEach var="shoppingList" items="${user.shoppingList}"
							varStatus="i">

							<ul class="list-group">
								<li class="list-group-item">
									<div class="row toggle" id="dropdown-detail-1"
										data-toggle="detail-${i.index}">
										<div class="col-xs-12 col-md-8">
											<c:out value="${shoppingList.quantity}"></c:out>
											<c:out value="${shoppingList.ingredient}"></c:out>
										</div>
										<div class="col-xs-2">
											<i class="fa fa-chevron-down"></i>
										</div>
									</div>
									<form
										action="${pageContext.request.contextPath}/deleteShoppingList/${shoppingList.id}">
										<div id="detail-${i.index}">
											<hr></hr>
											<div class="container">
												<div class="row">
													<div class="col-xs-12 col-md-4">
														<div class="fluid-row">
															<div>
																<button type="submit" class="mybutton btn btn-warning">Delete</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</form>
								</li>
							</ul>
						</c:forEach>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>

<div class="rightDiv">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-md-6">
				<div class="panel panel-default .col-xs-5">
					<div class="panel-heading">
						<h3 class="panel-title">Edit Shopping List</h3>
					</div>
					<ul class="list-group">
						<li class="list-group-item">
							<div class="row toggle" id="dropdown-detail-1"
								data-toggle="detail-100">
								<div class="col-xs-10">Add An Ingredient</div>
								<div class="col-xs-2">
									<i class="fa fa-chevron-down pull-right"></i>
								</div>
							</div>
							<div id="detail-100">
								<hr></hr>
								<div class="container">
									<div class="row">
										<div class="col-xs-12 col-md-5">
											<form class="form-horizontal"
												onsubmit="return validateForm()" name="myForm"
												action="${pageContext.request.contextPath}/addToShoppingList"
												method="post">
												<div class="form-group" align="right">
													<label class="col-md-3 control-label">Ingredients</label>
													<div class="col-md-9">
														<input class="form-control" placeholder="Quantity"
															name="ingredientQuantity" onkeypress='validate(event)'type="text" width="2"></input>
														<input class="form-control" name="ingredientName"
															placeholder="Measurement and Name" type="text"></input>
														<button type="submit" class="mybutton btn ">Add
															To Your Shopping List</button>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</li>
						<li class="list-group-item">
							<div class="row toggle" id="dropdown-detail-1"
								data-toggle="detail-101">
								<div class="col-xs-10">Empty Basket</div>
								<div class="col-xs-2">
									<i class="fa fa-chevron-down pull-right"></i>
								</div>
							</div>
							<div id="detail-101">
								<hr></hr>
								<div class="container">

									<c:forEach var="user" items="${userList}">
										<c:if test="${user.shoppingList.size()>0}">
											<form
												action="${pageContext.request.contextPath}/deleteEntireShoppingList">
												<button type="submit" class="mybutton btn btn-danger">Empty
													Shopping List</button>
											</form>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<button onclick="myFunction()" class="btn btn-info">Print
					Shopping List</button>
			</div>
		</div>
	</div>


</div>



<script>
	$(document).ready(function() {
		$('[id^=detail-]').hide();
		$('.toggle').click(function() {
			$input = $(this);
			$target = $('#' + $input.attr('data-toggle'));
			$target.slideToggle();
		});
	});

	function myFunction() {
		window.print();
	}
	function validateForm() {
		var x = document.forms["myForm"]["ingredientQuantity"].value;
		var y = document.forms["myForm"]["ingredientName"].value;

		if (x == null || x == "") {
			alert("Must add ingredient amount");
			return false;
		}
		if (y == null || y == "") {
			alert("Must add ingredient measurement and name");
			return false;
		}
	}

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
</script>

<style>
.leftDiv {
	float: left;
	width: 50%;
}

.rightDiv {
	width: 50%;
	margin-left: 50%;
}
</style>