<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.row-sep {
	padding-top: 10px;
	padding-bottom: 10px;
}

.mybutton {
	margin: 5px;
}
</style>
</head>
<c:forEach var="user" items="${userList}">
<c:if test="${user.shoppingList.size()>0}" var='0'>
<form
	action="${pageContext.request.contextPath}/deleteEntireShoppingList">
	<button type="submit" class="mybutton btn btn-warning">Empty Shopping List</button>
</form>
</c:if>
</c:forEach>

<body>
	<table class="table table-bordered table-striped">
		<c:forEach var="user" items="${userList}">
			<c:if test="${user.shoppingList.size()==0}">
				<p>You have no ingredients in your Shopping List</p>
				<p>Get started by finding a recipe you like.</p>
				<p>
					<a href="${pageContext.request.contextPath}/allrecipes">Recipes</a>
				</p>
			</c:if>
			<c:if test="${user.shoppingList.size()>0}" var='0'>
			<tr>
				<td><b>Quantity</b></td>
				<td><b>Ingredient</b></td>
				<td></td>
			</tr>
			<c:forEach var="shoppingList" items="${user.shoppingList}">
			
				<tr class="row-sep">
					<td><c:out value="${shoppingList.quantity}"></c:out></td>
					<td><c:out value="${shoppingList.ingredient}"></c:out></td>
					<td><form
							action="${pageContext.request.contextPath}/deleteShoppingList/${shoppingList.id}">
							<div style="text-align: center;">
								<button type="submit" class="mybutton btn btn-warning">Delete</button>
							</div>
						</form></td>
				</tr>
			</c:forEach>
			</c:if>
		</c:forEach>
	</table>

</body>
</html>