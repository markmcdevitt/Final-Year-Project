<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">
	<div class="col-xs-12 col-md-10">
		<c:forEach var="user" items="${userList}">
			<c:if test="${user.weeklyPlan.size()==0}">
				<p>You have not added any recipes to the weekly plan</p>
				<p>Get started by finding a recipe you like.</p>
				<p>
					<a href="${pageContext.request.contextPath}/allrecipes">Recipes</a>
				</p>
			</c:if>
			<c:if test="${user.weeklyPlan.size()>0}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Your Recipe Planner</h3>
					</div>
					<c:forEach var="weeklyplan" items="${user.weeklyPlan}"
						varStatus="i">


						<ul class="list-group">
							<li class="list-group-item">
								<div class="row toggle" id="dropdown-detail-1"
									data-toggle="detail-${i.index}">
									<div class="col-xs-12 col-md-8">
										<c:out value="${weeklyplan.date}"></c:out>
									</div>
									<div class="col-xs-2">
										<i class="fa fa-chevron-down"></i>
									</div>
								</div>
								<div id="detail-${i.index}">
									<hr></hr>
									<div class="container">
										<div class="row">
											<div class="col-xs-12 col-md-4">
												<div class="fluid-row">
													<div>
														<c:forEach var="recipe" items="${weeklyplan.recipe}">
															<table>
															<tr>
																<td><a href="${pageContext.request.contextPath}/recipe/${recipe.id}">
																<c:out value="${recipe.titleParse}"></c:out> </a></td>
																<td>&nbsp;&nbsp;</td>
																<td><a href="${pageContext.request.contextPath}/deleteRecipeFromPlan/${weeklyplan.id}/${recipe.titleParse}">
																<span><div class="red-x">&#10006;</div></span>
																</a></td>
															</tr>	
															</table>
														</c:forEach>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</c:forEach>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<script>
	$(document).ready(function() {
		$('[id^=detail-]').hide();
		$('.toggle').click(function() {
			$input = $(this);
			$target = $('#' + $input.attr('data-toggle'));
			$target.slideToggle();
		});
	});
</script>
<style>
.red-x {
	color: red;
}
</style>