<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script type="text/javascript">
	$.fn.stars = function() {
		return this.each(function(i, e) {
			$(e).html($('<span/>').width($(e).text() * 16));
		});
	};
	$('.stars').stars();
</script>


<c:choose>
	<c:when test="${hasRecipe}">
		<p>
			<a href="${pageContext.request.contextPath}/createrecipe">Edit Or
				Delete Your Recipe</a>
		</p>
	</c:when>
	<c:otherwise>
		<p>
			<a href="${pageContext.request.contextPath}/createrecipe">Add A
				Recipe</a>
		</p>

	</c:otherwise>
</c:choose>

<p>
	<a href="${pageContext.request.contextPath}/allrecipes">Show All Recipes</a>
</p>





