<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<c:forEach var="recipe" items="${recipe}">
	<h3 align="center">
		<c:out value="${recipe.titleParse}"></c:out>

	</h3>

	<div align="right">
		<a href="${pageContext.request.contextPath}/favourite/${recipe.id}"
			class="btn btn-default"><span class="glyphicon glyphicon-star"></span>
		</a>
	</div>

	<center>
		<img src="${recipe.imageURLParse}" class="img-rounded" width="250"
			height="250" alt="Responsive image">
	</center>

	<br>
	<div align="right">
		<h5>How many do you want to feed?</h5>
		<form
			action="${pageContext.request.contextPath}/adjustRecipe/${recipe.id}"
			method="post">
			<input type='button' value='-' class='qtyminus' field='quantity' />
			<input type='text' name='quantity' value='${recipe.peopleFed}'
				class='qty' style="width: 20px" /> <input type='button' value='+'
				class='qtyplus' field='quantity' /><br> <br>
			<button type="submit" class="btn btn-primary btn-sm">Submit</button>
		</form>
	</div>

	<h4>
		Recipe Rating: <span class="stars"><span><c:out
					value="${recipe.totalRating}"></c:out></span></span>
	</h4>
	Recipe Level: <c:out value="${recipe.level}"></c:out>
	<br>
	Calories Per Serving: <c:out value="${recipe.calories}"></c:out>
	<br>
	Serves: <c:out value="${recipe.peopleFed}"></c:out>

</c:forEach>
<table class="table table-bordered table-striped">
	<tr>
		<td><b>Instructions</b></td>
	</tr>
	<c:forEach var="recipe" items="${recipe}">
		<c:forEach var="instructions" items="${recipe.instructions}"
			varStatus="i">
			<tr>
				<td><c:out value="${(i.index)+1} )  ${instructions.steps}"></c:out></td>
			</tr>
		</c:forEach>
	</c:forEach>
</table>
<table class="table table-bordered table-striped">
	<tr>
		<td><b>Quantity</b></td>
		<td><b>Ingredient</b></td>
	</tr>
	<c:forEach var="recipe" items="${recipe}">
		<c:forEach var="ingredient" items="${recipe.ingredients}">
			<tr>
				<td><c:out value="${ingredient.ingredientAmount}"></c:out></td>
				<td><c:out value="${ingredient.ingredientName}"></c:out></td>
			</tr>
		</c:forEach>
	</c:forEach>
</table>
<c:forEach var="recipe" items="${recipe}">
	<a href="${pageContext.request.contextPath}/addToWeeklyPlan/${recipe.id}/${recipe.peopleFed}"><button
			type="button" class="btn btn-primary btn-sm">
			<c:out value="Add To Weekly Plan "></c:out>
		</button></a>
</c:forEach>
<br>
<br>
<table class="table table-bordered table-striped">
	<c:forEach var="recipe" items="${recipe}">
		<c:if test="${empty recipe.review}" var='0'>
			<p>There are no reviews yet</p>
			<p>Be the first!</p>
		</c:if>
	</c:forEach>
	<c:forEach var="recipe" items="${recipe}">
		<c:if test="${recipe.review.size()>0}" var='0'>
			<tr>
				<td width="50"><b>Users Rating</b></td>
				<td><b>Message</b></td>
				<td><b>Reviewed By</b></td>
			</tr>
			<c:forEach var="review" items="${recipe.review}">
				<tr>
					<td><span class="stars"><span><c:out
									value="${review.rating}"></c:out></span></span></td>
					<td><c:out value="${review.message}"></c:out></td>
					<td><c:out value="${review.user.username}"></c:out></td>

				</tr>
			</c:forEach>
		</c:if>
	</c:forEach>
</table>
<c:forEach var="recipe" items="${recipe}">
	<form id="form2"
		action="${pageContext.request.contextPath}/createreview/${recipe.titleParse}"
		method="post">
		<div class="form-group">
			<label for="comment">Your Review:</label>
			<textarea name="message" class="form-control" rows="5" id="comment"></textarea>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#myModal">Submit Review</button>

		</div>

		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Give The Recipe A Rating</h4>
					</div>
					<div class="modal-body">
						<span class="rating"> <input type="radio"
							class="rating-input" id="rating-input-1-5" name="rating-input-1"
							value="5"> <label for="rating-input-1-5"
							class="rating-star"></label> <input type="radio"
							class="rating-input" id="rating-input-1-4" name="rating-input-1"
							value="4"> <label for="rating-input-1-4"
							class="rating-star"></label> <input type="radio"
							class="rating-input" id="rating-input-1-3" name="rating-input-1"
							value="3"> <label for="rating-input-1-3"
							class="rating-star"></label> <input type="radio"
							class="rating-input" id="rating-input-1-2" name="rating-input-1"
							value="2"> <label for="rating-input-1-2"
							class="rating-star"></label> <input type="radio"
							class="rating-input" id="rating-input-1-1" name="rating-input-1"
							value="1"> <label for="rating-input-1-1"
							class="rating-star"></label>
						</span>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary btn-lg">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</c:forEach>

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
<style>
body {
	font-family: helvetica, arial, verdana, sans-serif;
}

label {
	display: block;
	font-size: .8em;
}

.rating {
	overflow: hidden;
	display: inline-block;
	font-size: 0;
	position: relative;
}

.rating-input {
	float: right;
	width: 16px;
	height: 16px;
	padding: 0;
	margin: 0 0 0 -16px;
	opacity: 0;
}

.rating:hover .rating-star:hover, .rating:hover .rating-star:hover ~
	.rating-star, .rating-input:checked ~ .rating-star {
	background-position: 0 0;
}

.rating-star, .rating:hover .rating-star {
	position: relative;
	float: right;
	display: block;
	width: 16px;
	height: 16px;
	background: url('http://kubyshkin.ru/samples/star-rating/star.png') 0
		-16px;
}

body {
	margin: 20px;
}
</style>
<script>
	jQuery(document).ready(
			function() {
				// This button will increment the value
				$('.qtyplus').click(
						function(e) {
							// Stop acting like a button
							e.preventDefault();
							// Get the field name
							fieldName = $(this).attr('field');
							// Get its current value
							var currentVal = parseInt($(
									'input[name=' + fieldName + ']').val());
							// If is not undefined
							if (!isNaN(currentVal)) {
								// Increment
								$('input[name=' + fieldName + ']').val(
										currentVal + 1);
							} else {
								// Otherwise put a 0 there
								$('input[name=' + fieldName + ']').val(0);
							}
						});
				// This button will decrement the value till 0
				$(".qtyminus").click(
						function(e) {
							// Stop acting like a button
							e.preventDefault();
							// Get the field name
							fieldName = $(this).attr('field');
							// Get its current value
							var currentVal = parseInt($(
									'input[name=' + fieldName + ']').val());
							// If it isn't undefined or its greater than 0
							if (!isNaN(currentVal) && currentVal > 0) {
								// Decrement one
								$('input[name=' + fieldName + ']').val(
										currentVal - 1);
							} else {
								// Otherwise put a 0 there
								$('input[name=' + fieldName + ']').val(0);
							}
						});
			});
</script>
<script>
	function reduce(numerator, denominator) {
		var gcd = function gcd(a, b) {
			return b ? gcd(b, a % b) : a;
		};
		gcd = gcd(numerator, denominator);
		return [ numerator / gcd, denominator / gcd ];
	}
</script>