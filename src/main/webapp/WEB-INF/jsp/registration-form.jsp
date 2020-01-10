<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!doctype html>
<html lang="en">
  	<head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
	<body>
   
		<div id="login-background" class="container-fluid">
			<div id="login-container" class="container min-vh-100 d-flex flex-column justify-content-center">	
				<div class="card">
				  <div class="card-header">
				    <ul class="nav nav-tabs card-header-tabs">
				      <li class="nav-item">
				        <a class="nav-link" href="${pageContext.request.contextPath}/showLogin">Login</a>
				      </li>
				      <li class="nav-item">
				        <a class="nav-link active" href="${pageContext.request.contextPath}/register/showRegistrationForm">
				        Register</a>
				      </li>
				    </ul>
				  </div>
				  <div class="card-body">
				  		<c:if test="${param.error == null && param.logout == null}">
				    		<h5 class="card-title">Hello!</h5>
				    	</c:if>
				    	
				    	<form:form action="${pageContext.request.contextPath}/register/processRegistrationForm"
							modelAttribute="userDTO">
							
							<c:if test="${registrationError != null}">
								<i>${registrationError}</i>
							</c:if>
							
							<form:errors path="username" cssClass="error" />
							<form:input path="username" placeholder="Username" class="form-control"/>
							
							<form:errors path="email" cssClass="error" />
							<form:input path="email" placeholder="Email" class="form-control" />
							
							<form:errors path="password" cssClass="error" />
							<form:password path="password" placeholder="Password" class="form-control" autocomplete="new-password"/>
							
							<form:errors path="matchingPassword" cssClass="error" />
							<form:password path="matchingPassword" placeholder="Repeat password" class="form-control" />
			
							<button class="btn btn-primary"  type="submit">Register </button>
						</form:form>
				  </div>
				</div>	
			</div>
		</div>
	
		
		
		<%@ include file="footer.jsp" %>
	</body>
</html>