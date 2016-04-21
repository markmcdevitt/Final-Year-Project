<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<nav class="navbar navbar-findcond navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/'/>">Recipe</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false">Categories
				</a>
					<ul class="dropdown-menu" role="menu">
						<li><a
							href="${pageContext.request.contextPath}/getMainDishes"><i
								class="fa fa-fw fa-tag"></i>Main Dishes</a></li>
						<li><a
							href="${pageContext.request.contextPath}/getVegetarian"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Vegetarian</a></li>
						<li><a
							href="${pageContext.request.contextPath}/getAppetisers"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Appetisers</a></li>
						<li><a href="${pageContext.request.contextPath}/getDessert"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Desserts</a></li>
					</ul></li>


				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false">Hungry?
				</a>
					<ul class="dropdown-menu" role="menu">
						<li><a
							href="${pageContext.request.contextPath}/viewyourweeklyplan"><i
								class="fa fa-fw fa-tag"></i> Weekly Plan</a></li>
						<li><a
							href="${pageContext.request.contextPath}/viewshoppinglist"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Shopping List</a></li>
						<li><a
							href="${pageContext.request.contextPath}/foodgenerator"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Food Generator</a></li>
						<li><a href="${pageContext.request.contextPath}/allrecipes"><i
								class="fa fa-fw fa-thumbs-o-up">All Recipes</i></a></li>
						<li><a href="${pageContext.request.contextPath}/profile"><i
								class="fa fa-fw fa-thumbs-o-up"></i>Your Profile</a></li>
					</ul></li>

				<li class="active"><a
					href="${pageContext.request.contextPath}/createrecipe">Add
						Recipe<span class="sr-only">(current)</span>
				</a></li>

				<sec:authorize access="!isAuthenticated()">
					<li class="active"><a
						href="${pageContext.request.contextPath}/newaccount">Register<span
							class="sr-only">(current)</span>
					</a></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Admin
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="<c:url value='/admin'/>">See The Users</a></li>
						</ul></li>
				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="<c:url value='/login'/>"><span
								class="glyphicon glyphicon-log-in"></span>Log in</a></li>
					</ul>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<ul class="nav navbar-nav navbar-right ">
						<li><a href="<c:url value='/j_spring_security_logout'/>"><span
								class="glyphicon glyphicon-log-in"></span>Log out</a></li>
					</ul>
				</sec:authorize>
			</ul>

			<form class="navbar-form navbar-right search-form"
				action="${pageContext.request.contextPath}/search" role="search">

				<div class="input-group" id="adv-search">

					<div class="input-group-btn">
						<div class="btn-group" role="group">
							<div class="dropdown dropdown-lg">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search"
										name="search"> <input type="button"
										class="form-control" data-toggle="dropdown"
										aria-expanded="false" value="Allergies">

									<div class="dropdown-menu dropdown-menu-right" role="menu">
										<div class="form-group">
											<table class='table borderless' style="width: 30%"
												style="width: 50%">
												<thead>
													<tr>
														<th>Allergy</th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Nuts</td>
														<td><input id="nuts" name="nuts" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true">
															<input id="nutsHidden" name="nuts" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Milk</td>
														<td><input id="milk" name="milk" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true">
															<input id="milkHidden" name="milk" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Peanuts</td>
														<td><input id="peanuts" name="peanuts"
															type="checkbox" data-toggle="checkbox-x"
															data-enclosed-label="true"> <input
															id="peanutsHidden" name="peanuts" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Eggs</td>
														<td><input id="eggs" name="eggs" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true"><input
															id="eggsHidden" name="eggs" type="hidden" value="no"></td>
													</tr>
													<tr>
														<td>Fish</td>
														<td><input id="fish" name="fish" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true">
															<input id="fishHidden" name="fish" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Shellfish</td>
														<td><input id="shellfish" name="shellfish"
															type="checkbox" data-toggle="checkbox-x"
															data-enclosed-label="true"> <input
															id="shellfishHidden" name="shellfish" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Wheat</td>
														<td><input id="wheat" name="wheat" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true">
															<input id="wheatHidden" name="wheat" type="hidden"
															value="no"></td>
													</tr>
													<tr>
														<td>Soy</td>
														<td><input id="soy" name="soy" type="checkbox"
															data-toggle="checkbox-x" data-enclosed-label="true">
															<input id="soyHidden" name="soy" type="hidden" value="no"></td>
													</tr>
												</tbody>
												<tr>
													<th>Exclude Allergies</th>
													<td><input id="exclude" name="exclude" type="checkbox"
														data-toggle="checkbox-x" data-enclosed-label="true">
														<input id="excludeHidden" name="exclude" type="hidden"
														value="no"></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</nav>
<script>
	if (document.getElementById("nuts").checked) {
		document.getElementById('nutsHidden').disabled = true;
	}
	if (document.getElementById("milk").checked) {
		document.getElementById('milkHidden').disabled = true;
	}
	if (document.getElementById("peanuts").checked) {
		document.getElementById('peanutsHidden').disabled = true;
	}
	if (document.getElementById("eggs").checked) {
		document.getElementById('eggsHidden').disabled = true;
	}
	if (document.getElementById("fish").checked) {
		document.getElementById('fishHidden').disabled = true;
	}
	if (document.getElementById("shellfish").checked) {
		document.getElementById('shellfishHidden').disabled = true;
	}
	if (document.getElementById("wheat").checked) {
		document.getElementById('wheatHidden').disabled = true;
	}
	if (document.getElementById("soy").checked) {
		document.getElementById('soyHidden').disabled = true;
	}
	if (document.getElementById("exclude").checked) {
		document.getElementById('excludeHidden').disabled = true;
	}
</script>

<script>
	$(document).ready(function(e) {
		$('.search-panel .dropdown-menu').find('a').click(function(e) {
			e.preventDefault();
			var param = $(this).attr("href").replace("#", "");
			var concept = $(this).text();
			$('.search-panel span#search_concept').text(concept);
			$('.input-group #search_param').val(param);
		});
	});
</script>

<style>
.caret {
	height: 20px;
	display: block;
}
</style>
<style>
nav.navbar-findcond {
	background: #fff;
	border-color: #ccc;
	box-shadow: 0 0 2px 0 #ccc;
}

nav.navbar-findcond a {
	color: #f14444;
}

nav.navbar-findcond ul.navbar-nav a {
	color: #f14444;
	border-style: solid;
	border-width: 0 0 2px 0;
	border-color: #fff;
}

nav.navbar-findcond ul.navbar-nav a:hover, nav.navbar-findcond ul.navbar-nav a:visited,
	nav.navbar-findcond ul.navbar-nav a:focus, nav.navbar-findcond ul.navbar-nav a:active
	{
	background: #fff;
}

nav.navbar-findcond ul.navbar-nav a:hover {
	border-color: #f14444;
}

nav.navbar-findcond li.divider {
	background: #ccc;
}

nav.navbar-findcond button.navbar-toggle {
	background: #f14444;
	border-radius: 2px;
}

nav.navbar-findcond button.navbar-toggle:hover {
	background: #999;
}

nav.navbar-findcond button.navbar-toggle>span.icon-bar {
	background: #fff;
}

nav.navbar-findcond ul.dropdown-menu {
	border: 0;
	background: #fff;
	border-radius: 4px;
	margin: 4px 0;
	box-shadow: 0 0 4px 0 #ccc;
}

nav.navbar-findcond ul.dropdown-menu>li>a {
	color: #444;
}

nav.navbar-findcond ul.dropdown-menu>li>a:hover {
	background: #f14444;
	color: #fff;
}

nav.navbar-findcond span.badge {
	background: #f14444;
	font-weight: normal;
	font-size: 11px;
	margin: 0 4px;
}

nav.navbar-findcond span.badge.new {
	background: rgba(255, 0, 0, 0.8);
	color: #fff;
}
</style>