<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
	$(document).ready(function() {
		document.f.j_username.focus();
	});
</script>

<div class="container" style="margin-top: 30px">
	<form name='f'
		action='${pageContext.request.contextPath}/j_spring_security_check'
		method='POST'>
		<div class="row centered-form">
			<div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">

				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Login</h3>
					</div>
					<div class="panel-body">
						<input type='text' name='j_username'
							class="form-control input-sm chat-input" placeholder="Username">
						<br> <input type='password' name='j_password'
							class="form-control input-sm chat-input" placeholder="Password" />
						<br>
						<c:if test="${param.error !=null}">

							<p class="error">Login Failed. Check your username or
								password is correct</p>
						</c:if>
						<div class="col-md-12 text-center">
						<div class="checkbox">
							<label> <input name="remember" type="checkbox"
								name='_spring_security_remember_me' value="Remember Me"
								checked="checked"> Remember Me
							</label>
						</div>
							<span class="group-btn">
								<button type="submit" value="login"
									class="btn btn-primary btn-md">
									Login <i class="fa fa-sign-in"></i>
								</button>
							</span>
							<p>
								<a href="<c:url value="/newaccount"/>">Don't have an
									account?</a>
							</p>
						</div>
					</div>
			
				<br />

			</div>
		</div>
	</form>
</div>

