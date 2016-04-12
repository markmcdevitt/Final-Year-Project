<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Weekly Plan</title>
</head>
<div class="container">
	<div class="row centered-form">
		<div
			class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						Pick A Date For Your Meal<small> And Enjoy!</small>
					</h3>
				</div>
				<div class="panel-body">
					<sf:form method="post" action="${pageContext.request.contextPath}/saveToWeeklyPlan" modelAttribute="weeklyPlan">
						<div class="row">
							<div class="col-xs-8 col-sm-8 col-md-8">
								<div class="form-group">
									<input id="dateformat" type="date" class="form-control" name="date"></input><br />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<label class="control">${recipe.titleParse}</label><br />
								</div>
							</div>
						</div>
						<div class="col-md-12 text-right">
							<button type="submit" class="btn btn-primary btn-lg">Submit</button>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>