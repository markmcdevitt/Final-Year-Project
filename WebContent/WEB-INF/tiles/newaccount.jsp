<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div class="row centered-form">
		<div 
			class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4" >

			<div class="panel panel-default" >
				<div class="panel-heading">
					<h3 class="panel-title">Register</h3>
				</div>

				<div class="panel-body" >
					<sf:form id="details" method="post"
						action="${pageContext.request.contextPath}/createaccount" commandName="user">
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<sf:input name="username" class="form-control input-sm" path="username"
										placeholder="Name" type="text"/><sf:errors path="username" cssClass="error"></sf:errors>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<sf:input class="form-control input-sm" path ="email" name="email" type="text"
										placeholder="Email" /><sf:errors path="email" cssClass="error"></sf:errors>
								</div>
							</div>
						</div>


						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="password" name="password" id="password"
										class="form-control input-sm" placeholder="Password">

								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="password" name="confirmpass" id="confirmpass"
										class="form-control input-sm" placeholder="Confirm Password">
									<div id=matchpass></div>
								</div>
							</div>
						</div>


						<div class="form-group">
							<div class="col-md-12 text-center">
								<button type="submit" class="btn btn-primary btn-lg">Submit</button>
							</div>
						</div>

					</sf:form>
				</div>
			</div>
		</div>
	</div>
</div>
<style>
#outer {
	position: relative;
}

#inner {
	margin: auto;
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
}
</style>

