<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<div class="container">
	<div class="row">
		<div class="col-sm-10">
			<h1>
				<c:out value="${user.username}"></c:out>
			</h1>
		</div>

	</div>
	<div class="row">
		<div class="col-sm-4">
			<ul class="list-group">
				<li class="list-group-item text-muted">Profile</li>
				<li class="list-group-item text-right"><span class="pull-left"><strong>Email</strong></span>
					<c:out value="${user.email}"></c:out></li>
				<li class="list-group-item text-right"><span class="pull-left"><strong>Level</strong></span>
					<c:out value="${user.userLevel}"></c:out></li>
				<c:if test="${empty user.usersAllergys}">
					<li class="list-group-item text-left"><a
						href="${pageContext.request.contextPath}/editAllergy"><small><font
								color="black">You Dont Have Any Allergies</font></small></a></li>
				</c:if>
				<c:if test="${not empty user.usersAllergys}">
					<li class="list-group-item text-right"><span class="pull-left"><strong><a
								href="${pageContext.request.contextPath}/editAllergy">Your
									Allergies</a></strong></span> <c:forEach var="allergys"
							items="${user.usersAllergys}">
							<c:out value="${allergys.allergy}"></c:out>
							<br>
						</c:forEach></li>

				</c:if>


			</ul>
		</div>
		<div class="col-sm-11">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#home" data-toggle="tab">Your
						Recipes</a></li>
				<li><a href="#settings" data-toggle="tab">User Details</a></li>
				<li><a href="#ingredients" data-toggle="tab">Your
						Ingredients</a></li>
				<li><a href="#favourite" data-toggle="tab">Favourite</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="home">
					<div class="table-responsive">
						<c:if test="${empty recipeList}">
							<br>
							<p>You have not created a recipe yet.</p>
							<a href="${pageContext.request.contextPath}/createrecipe"><p>click
									here to make your first</p></a>
						</c:if>
						<c:if test="${not empty recipeList}">
							<table class="table table-hover">
								<thead>
									<tr>
										<td><b>Recipe Name</b></td>
										<td><b>Description</b></td>
										<td><b>Rating</b></td>
										<td><b>Calories</b></td>
										<td><b>Serves</b></td>
									</tr>
								</thead>
								<tbody id="items">
									<c:forEach var="recipe" items="${recipeList}">
										<tr>
											<td><a
												href="${pageContext.request.contextPath}/recipe/${recipe.id}"><c:out
														value="${recipe.titleParse}"></c:out></a></td>
											<c:if test="${not empty recipe.totalRating}">
												<td><span class="stars"><span><c:out
																value="${recipe.totalRating}"></c:out></span></span></td>
											</c:if>
											<c:if test="${empty recipe.totalRating}">
												<td>Not Rated Yet</td>
											</c:if>
											<td><c:out value="${recipe.descriptionParse}"></c:out></td>
											<td><c:out value="${recipe.calories}"></c:out></td>
											<td><c:out value="${recipe.peopleFed}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<hr>
						<div class="row">
							<div class="col-md-4 col-md-offset-4 text-center">
								<ul class="pagination" id="myPager"></ul>
							</div>
						</div>
					</div>
					<hr>
				</div>

				<div class="tab-pane" id="settings">
					<hr>
					<form id ="details" class="form"
						action="${pageContext.request.contextPath}/editDetails"
						method="post">
						<div class="form-group">

							<div class="col-xs-6">
								<label for="username"><h4>Username</h4></label> <input
									type="text" class="form-control" name="username" id="username"
									value="<c:out value="${user.username}"></c:out>" readonly>
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="last_name"><h4>Email</h4></label> <input type="email"
									class="form-control" name="email" id="email" style='width:250px'
									value="<c:out value="${user.email}"></c:out>">
							</div>
						</div>

						<div class="form-group">

							<div class="col-xs-6">
								<label for="phone"><h4>Password</h4></label> <input
									type="password" class="form-control" name="password"
									id="password" placeholder="New Password" required="required">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6">
								<label for="mobile"><h4>Confirm Password</h4></label> <input
									type="password" class="form-control" name="confirmpass"
									id="confirmpass" placeholder="Confirm Password" required="required">
								<div id=matchpass></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<br>
								<button class="btn btn-lg btn-success" type="submit">
									<i class="glyphicon glyphicon-ok-sign"></i> Save
								</button>
								<button class="btn btn-lg" type="reset">
									<i class="glyphicon glyphicon-repeat"></i> Reset
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="tab-pane" id="ingredients">
					<hr>
					<div class="leftDiv">
						<form class="form"
							action="${pageContext.request.contextPath}/createingredientsowned"
							method="post">
							<label for="last_name"><h4>Ingredients You Own</h4></label>
<br>
							<c:forEach var="ingredientsOwned"
								items="${user.ingredientsOwned}">

								<div class="form-group">
									<div class="leftDiv">
										<div class="col-xs-6">

											<input name="ingredientName" type="text" class="form-control"
												value="<c:out value="${ingredientsOwned.ingredientOwned}" ></c:out>">
										</div>
									</div>
								</div>

								<br>
							</c:forEach>

							<div class="leftDiv">
								<div class="form-group">
									<div class="col-xs-6">
										<div id="container2"></div>
										<input type="button" onclick="createTextBox(1)"
											value="Add Ingredient" class="mybutton btn">

									</div>
								</div>
							</div>
							<div class="form-group">

								<div class="col-xs-12">
									<br>
									<button class="btn btn-lg btn-success" type="submit">
										<i class="glyphicon glyphicon-ok-sign"></i> Update List
									</button>
									<button class="btn btn-lg" type="reset">
										<i class="glyphicon glyphicon-repeat"></i> Reset
									</button>

								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="tab-pane" id="favourite">
					<div class="table-responsive">
						<table class="table table-hover">
							<c:if test="${empty user.usersFavorites}">
								<br>
								<p>You dont have any favourites.</p>
								<a href="${pageContext.request.contextPath}/allrecipes">Click
									here to find a recipe that you like</a>
							</c:if>

							<c:if test="${not empty user.usersFavorites}">
								<thead>
									<tr>
										<td><b>Recipe Name</b></td>
										<td><b>Description</b></td>
										<td><b>Rating</b></td>
										<td><b>Calories</b></td>
										<td><b>Serves</b></td>
									</tr>
								</thead>
								<tbody id="items">
									<c:forEach var="favouriteList" items="${user.usersFavorites}">
										<tr>
											<td><a
												href="${pageContext.request.contextPath}/recipe/${favouriteList.recipe.id}"><c:out
														value="${favouriteList.recipe.titleParse}"></c:out></a></td>
											<c:if test="${not empty favouriteList.recipe.totalRating}">
												<td><span class="stars"><span><c:out
																value="${favouriteList.recipe.totalRating}"></c:out></span></span></td>
											</c:if>
											<c:if test="${empty favouriteList.recipe.totalRating}">
												<td>Not Rated Yet</td>
											</c:if>
											<td><c:out
													value="${favouriteList.recipe.descriptionParse}"></c:out></td>
											<td><c:out value="${favouriteList.recipe.calories}"></c:out></td>
											<td><c:out value="${favouriteList.recipe.peopleFed}"></c:out></td>
											<td><a
												href="<c:out value="${pageContext.request.contextPath}/deleteFavourite/${favouriteList.recipe.id}"></c:out>"
												class="btn btn-default"><span
													class="glyphicon glyphicon-floppy-remove"></span> </a></td>
										</tr>
									</c:forEach>
								</tbody>
							</c:if>
						</table>
						<hr>
						<div class="row">
							<div class="col-md-4 col-md-offset-4 text-center">
								<ul class="pagination" id="myPager2"></ul>
							</div>
						</div>
					</div>
					<hr>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$.fn.pageMe = function(opts) {
		var $this = this, defaults = {
			perPage : 7,
			showPrevNext : false,
			numbersPerPage : 1,
			hidePageNumbers : false
		}, settings = $.extend(defaults, opts);

		var listElement = $this;
		var perPage = settings.perPage;
		var children = listElement.children();
		var pager = $('.pagination');

		if (typeof settings.childSelector != "undefined") {
			children = listElement.find(settings.childSelector);
		}

		if (typeof settings.pagerSelector != "undefined") {
			pager = $(settings.pagerSelector);
		}

		var numItems = children.size();
		var numPages = Math.ceil(numItems / perPage);

		pager.data("curr", 0);

		if (settings.showPrevNext) {
			$('<li><a href="#" class="prev_link">«</a></li>').appendTo(pager);
		}

		var curr = 0;
		while (numPages > curr && (settings.hidePageNumbers == false)) {
			$('<li><a href="#" class="page_link">' + (curr + 1) + '</a></li>')
					.appendTo(pager);
			curr++;
		}

		if (settings.numbersPerPage > 1) {
			$('.page_link').hide();
			$('.page_link').slice(pager.data("curr"), settings.numbersPerPage)
					.show();
		}

		if (settings.showPrevNext) {
			$('<li><a href="#" class="next_link">»</a></li>').appendTo(pager);
		}

		pager.find('.page_link:first').addClass('active');
		if (numPages <= 1) {
			pager.find('.next_link').hide();
		}
		pager.children().eq(1).addClass("active");

		children.hide();
		children.slice(0, perPage).show();

		pager.find('li .page_link').click(function() {
			var clickedPage = $(this).html().valueOf() - 1;
			goTo(clickedPage, perPage);
			return false;
		});
		pager.find('li .prev_link').click(function() {
			previous();
			return false;
		});
		pager.find('li .next_link').click(function() {
			next();
			return false;
		});

		function previous() {
			var goToPage = parseInt(pager.data("curr")) - 1;
			goTo(goToPage);
		}

		function next() {
			goToPage = parseInt(pager.data("curr")) + 1;
			goTo(goToPage);
		}

		function goTo(page) {
			var startAt = page * perPage, endOn = startAt + perPage;

			children.css('display', 'none').slice(startAt, endOn).show();

			if (page >= 1) {
				pager.find('.prev_link').show();
			} else {
				pager.find('.prev_link').hide();
			}

			if (page < (numPages - 1)) {
				pager.find('.next_link').show();
			} else {
				pager.find('.next_link').hide();
			}

			pager.data("curr", page);

			if (settings.numbersPerPage > 1) {
				$('.page_link').hide();
				$('.page_link').slice(page, settings.numbersPerPage + page)
						.show();
			}

			pager.children().removeClass("active");
			pager.children().eq(page + 1).addClass("active");
		}
	};

	$('#items').pageMe({
		pagerSelector : '#myPager',
		childSelector : 'tr',
		showPrevNext : true,
		hidePageNumbers : false,
		perPage : 5
	});
	$('#items2').pageMe({
		pagerSelector : '#myPager2',
		childSelector : 'tr',
		showPrevNext : true,
		hidePageNumbers : false,
		perPage : 5
	});
</script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$.fn.stars = function() {
		return this.each(function(i, e) {
			$(e).html($('<span/>').width($(e).text() * 16));
		});
	};
	$('.stars').stars();
</script>

<script type="text/javascript">
	function createTextBox(n) {

		for (var i = 0; i < n; i++) {
			var textBox = document.createElement("input");

			textBox.setAttribute("name", "ingredientName");
			textBox.setAttribute("class", "form-control");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("align", "bottom");
			textBox.setAttribute("placeholder", "Ingredient Name");
			document.getElementById("container2").appendChild(textBox);

		}
	}
</script>
<script type="text/javascript">
	function onLoad() {

		$("#password").keyup(checkPasswordsMatch);
		$("#confirmpass").keyup(checkPasswordsMatch);

		$("#details").submit(canSubmit);
	}

	function canSubmit() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password != confirmpass) {
			alert("Passwords do not match!")
			return false;
		} else {
			return true;
		}
	}

	function checkPasswordsMatch() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (confirmpass.length > 0) {

			if (password == confirmpass) {
				$("#matchpass").text(
						"Passwords match");
				$("#matchpass").addClass("valid");
				$("#matchpass").removeClass("error");
			} else {
				$("#matchpass")
						.text("Passwords dont match");
				$("#matchpass").addClass("error");
				$("#matchpass").removeClass("valid");
			}
		}
	}

	$(document).ready(onLoad);
</script>
<style>
.leftDiv {
	float: left;
	width: 50%;
}
</style>