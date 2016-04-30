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
						Pick A Date For Your Meal<small> And Enjoy!</small>
					</h3>
				</div>
				<div class="panel-body">
					<sf:form method="post"
						action="${pageContext.request.contextPath}/saveToWeeklyPlan"
						modelAttribute="weeklyPlan">
						<div class="row">
							<div class="col-xs-8 col-sm-8 col-md-8">
								<div class="form-group">
									<input id="dateformat" type="date" class="form-control"
										name="date" required="required"></input><br />
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
							 <input type="submit" id="submit" value="Submit" class="btn btn-primary btn-lg" >
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; //January is 0!
	var yyyy = today.getFullYear();

	if (dd < 10) {
		dd = '0' + dd
	}

	if (mm < 10) {
		mm = '0' + mm
	}

	today = dd + '-' + mm + '-' + yyyy;
	document.getElementById('dateformat').setAttribute("min", "today");

</script>