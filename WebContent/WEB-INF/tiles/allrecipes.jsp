<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<head>
	<style>
		.myStars {
			width:80px;
		}
		.test {
			width:80px;
		}
	</style>
</head>

<div class="container" id="outer">
	<div class="pricing-table pricing-four-column row" id="inner">
		<c:forEach var="recipe" items="${recipe}">
			<a href="${pageContext.request.contextPath}/recipe/${recipe.titleParse}">
			<div class="plan col-xs-6">
					<div>
						<img src="${recipe.imageURLParse}" class="img-thumbnail"
							alt="Responsive image">
					</div>
					<ul>
						<li class="plan-feature"><c:out value="${recipe.titleParse}"></c:out></li>
						<li class="plan-feature"><small><c:out value="${recipe.descriptionParse}"></c:out></small></li>
						<li class="plan-feature">
							<span class="stars" >
									<c:out value="${recipe.totalRating}"></c:out>
								
							</span>
						</li>
					</ul>
				</div>
			</a>
		</c:forEach>
	</div>
</div>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script>
		$.fn.stars = function() {
			return this.each(function(i, e) {
				$(e).html($('<span/>').width($(e).text() * 16).css("float", "left"));
			});
		};
		$('.stars').stars();
	</script>
<style>
body {
	padding-top: 20px
}

.pricing-table .plan {
	width: 310px;
	height: 500px;
	margin-left: 0px;
	border-radius: 5px;
	text-align: center;
	background-color: #f3f3f3;
	-moz-box-shadow: 0 0 6px 2px #b0b2ab;
	-webkit-box-shadow: 0 0 6px 2px #b0b2ab;
	box-shadow: 0 0 6px 2px #b0b2ab;
}

.plan:hover {
	background-color: #fff;
	-moz-box-shadow: 0 0 12px 3px #b0b2ab;
	-webkit-box-shadow: 0 0 12px 3px #b0b2ab;
	box-shadow: 0 0 12px 3px #b0b2ab;
}

.plan {
	padding: 20px;
	margin-left: 0px;
	color: #ff;
	background-color: #5e5f59;
	-moz-border-radius: 5px 5px 0 0;
	-webkit-border-radius: 5px 5px 0 0;
	border-radius: 5px 5px 0 0;
}

.pricing-table .plan .plan-name span {
	font-size: 20px;
}

.pricing-table .plan ul {
	list-style: none;
	margin: 0;
	-moz-border-radius: 0 0 5px 5px;
	-webkit-border-radius: 0 0 5px 5px;
	border-radius: 0 0 5px 5px;
}

.pricing-table .plan ul li.plan-feature {
	padding: 2px;
	border-top: 1px solid #c5c8c0;
	margin-right: 35px;
	width: auto;
}

.pricing-three-column {
	margin: 0 auto;
	width: 80%;
}

.pricing-variable-height .plan {
	float: none;
	margin-left: 2%;
	vertical-align: bottom;
	display: inline-block;
	zoom: 1;
	*display: inline;
}

.plan-mouseover .plan-name {
	background-color: #4e9a06 !important;
}

.btn-plan-select {
	padding: 8px 25px;
	font-size: 18px;
}
#outer {
  width: 80%;
  text-align: center;
}

#inner {
  display: inline-block;
}

</style>