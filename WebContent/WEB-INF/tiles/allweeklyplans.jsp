<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>All Your Weekly Plans</title>
</head>
<body>
	<table class="table table-bordered table-striped">
		<c:forEach var="user" items="${userList}">
			<c:if test="${user.weeklyPlan.size()==0}">
				<p>You have not added any recipes to the weekly plan</p>
				<p>Get started by finding a recipe you like.</p>
				<p>
					<a href="${pageContext.request.contextPath}/allrecipes">Recipes</a>
				</p>
			</c:if>
			<c:if test="${user.weeklyPlan.size()>0}" var='0'>
				<tr>
					<td></td>
					<td><b>Date</b></td>
					<td><b>Recipe</b></td>
				</tr>
				<c:forEach var="weeklyplan" items="${user.weeklyPlan}">
					<tr>
					</tr>
					<td><form action="${pageContext.request.contextPath}/deleteUsersDay/${weeklyplan.id}">
							<input type="submit" value="Delete">
						</form></td>
					<td><c:out value="${weeklyplan.date}"></c:out></td>
					<c:forEach var="recipe" items="${weeklyplan.recipe}">
						<td><a href="${pageContext.request.contextPath}/recipe/${recipe.titleParse}">
								<c:out value="${recipe.titleParse}"></c:out>
						</a>
							<form action="${pageContext.request.contextPath}/deleteRecipeFromPlan/${weeklyplan.id}/${recipe.titleParse}">
								<button type="submit" id="button" class="btn btn-secondary btn-sm" value="">&#10006</button>
							</form></td>
					</c:forEach>
				</c:forEach>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>