<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<table class="table table-bordered table-striped">
		<tr>
			<td><b>Title</b></td>
			<td><b>Description</b></td>
			<td><b>Rating</b></td>
		</tr>


		<c:forEach var="recipe" items="${recipe}">
			<tr>
				<td><a href="${pageContext.request.contextPath}/recipe/${recipe.id}"><c:out value="${recipe.titleParse}"></c:out></a></td>
				<td><c:out value="${recipe.descriptionParse}"></c:out></td>
				<td><span class="stars"><span><c:out value="${recipe.totalRating}"></c:out></span></span></td>	
			</tr>
		</c:forEach>
			
	</table>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script type="text/javascript">
	$.fn.stars = function() {
	    return this.each(function(i,e){$(e).html($('<span/>').width($(e).text()*16));});
	};
	$('.stars').stars();
</script>
