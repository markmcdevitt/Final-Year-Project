<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table table-bordered table-striped">

	<td><a
		href="${pageContext.request.contextPath}/admin/alphabetical">Username:</a></td>

	<td>Email:</td>
	<td>Role:</td>
	<td>Enabled:</td>
	</tr>
	<tbody id="items">
		<c:forEach var="user" items="${users}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/user/${user.username}"><c:out
							value="${user.username}"></c:out></a></td>

				<td><c:out value="${user.email}"></c:out></td>

				<td><c:out value="${user.authority}"></c:out></td>

				<td><c:out value="${user.enabled}"></c:out></td>
			</tr>

		</c:forEach>
	</tbody>
</table>