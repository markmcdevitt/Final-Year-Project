<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<nav class="navbar navbar-findcond navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/'/>">Repice</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar">
			<ul class="nav navbar-nav navbar-right">
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
						<li><a
							href="${pageContext.request.contextPath}/profile"><i
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
				<input type="text" class="form-control" placeholder="Search"
					name="search" />
			</form>
		</div>
	</div>
</nav>
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