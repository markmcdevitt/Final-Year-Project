<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
	$(document).ready(function() {
		document.f.j_username.focus();
	});
</script>
<br>
<br>
<div class="container" style="margin-top:30px">
	<form name='f'
		action='${pageContext.request.contextPath}/j_spring_security_check'
		method='POST'>
		<div class="row">
			<div class="col-md-offset-4 col-md-3">
				<div class="form-login">
					<h4>Welcome back</h4>

					<input type='text' name='j_username'
						class="form-control input-sm chat-input" placeholder="Username">
					<br> <input type='password' name='j_password'
						class="form-control input-sm chat-input" placeholder="Password" />
					<br>
					<c:if test="${param.error !=null}">

						<p class="error">Login Failed. Check your username or password
							is correct</p>
					</c:if>
					<div class="checkbox">
						<label> <input name="remember" type="checkbox"
							name='_spring_security_remember_me' value="Remember Me"
							checked="checked"> Remember Me
						</label>
					</div>
					<div class="wrapper">
						<span class="group-btn">
							<button type="submit" value="login"
								class="btn btn-primary btn-md">
								Login <i class="fa fa-sign-in"></i>
							</button>
						</span>

					</div>

				</div>
				<br />
				<p>
					<a href="<c:url value="/newaccount"/>">Don't have an account?</a>
				</p>
			</div>
		</div>
	</form>
</div>

