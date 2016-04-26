<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/one-page-wonder.css">


<header class="header-image">
	<div class="headline">
		<div class="container">
			<h2>Let's Make </h2>
			<h3>a Meal</h3>
		</div>
	</div>
</header>

<div class="container">

	<hr class="featurette-divider">

	
	<a href="${pageContext.request.contextPath}/foodgenerator">
		<div class="featurette" id="about">
			<img class="featurette-image img-circle img-responsive pull-right"
				src="${pageContext.request.contextPath}/static/images/pic.jpg">
			<h2 class="featurette-heading">
				Dont Know What To Cook? <span class="text-muted">Try The
					Recipe Generator </span>
			</h2>
			<p class="lead">All you have to do is enter the ingredients you
				have at home, and the generator will pick the best suited recipes
				for you.</p>
		</div>
	</a>

	<hr class="featurette-divider">

	<!-- Second Featurette -->
	<a href="${pageContext.request.contextPath}/viewyourweeklyplan">
		<div class="featurette" id="services">
			<img class="featurette-image img-circle img-responsive pull-left"
				src="${pageContext.request.contextPath}/static/images/meal planner.jpg">
			<h2 class="featurette-heading">
				Plan Your Meals <span class="text-muted">With The Meal
					Planner </span>
			</h2>
			<p class="lead">To help orgainise your meals for the week.
				Planning your meals will help you eat healthier, shop more
				efficently and will save you money.</p>
		</div>
	</a>

	<hr class="featurette-divider">

	<!-- Third Featurette -->
	<a href="${pageContext.request.contextPath}/viewshoppinglist">
		<div class="featurette" id="contact">
			<img class="featurette-image img-circle img-responsive pull-right"
				src="${pageContext.request.contextPath}/static/images/sl	.jpg">
			<h2 class="featurette-heading">
				Save Money <span class="text-muted">By Using the Shopping
					List.</span>
			</h2>
			<p class="lead">Using your meal planner will add the
				ingredients of any recipe saved to your shopping list. Using the
				shopping list will save you time, reduce waste and will help you eat
				healthier.</p>
		</div>
	</a>

	<hr class="featurette-divider">

</div>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

<style>
div.header {
	position: relative;
	text-align: center;
	width: 100%;
	height: 0px;
	background-color: #ffffff;
}
.header-image {
	display: block;
	width: 100%;
	text-align: center;
	background:
		url('${pageContext.request.contextPath}/static/images/home2.jpg')
		no-repeat center center scroll;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}

a {
	color: inherit;
}

a:hover {
	color: inherit;
	text-decoration: none;
	cursor: pointer;
}
</style>

