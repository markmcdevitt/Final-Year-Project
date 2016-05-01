<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="row centered-form">
		<div
			class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						Enter a Link to Scrape From or Use the Default Button</small>
					</h3>
				</div>
				<div class="panel-body">
					<sf:form method="post"
						action="${pageContext.request.contextPath}/scrapeRecipes"
						modelAttribute="weeklyPlan">

						<div class="row">
							<div class="col-xs-8 col-sm-8 col-md-8">
								<div class="form-group">
									<input placeholder="Enter the link to scrape" type="text"
										class="form-control" name="link"></input><br />
										<div class="symbol">
									<span style ="float:right;"class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								</div>
								</div>
								
							</div>
						</div>

						<button type="submit" class="btn btn-primary">Scrape Link</button>

					</sf:form>

					<a href="${pageContext.request.contextPath}/scrapeRecipesDefault">
						<br>
						<button type="button" class="btn btn-primary">
							<c:out value="Default Scrape"></c:out>
						</button>
					</a>
				</div>

			</div>
		</div>
	</div>
</div>
<style>
.symbol .glyphicon glyphicon-ok {
	float: right;
	color: green;
	margin-left: 50%;
}
</style>