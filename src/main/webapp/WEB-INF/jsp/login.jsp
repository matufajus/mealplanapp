<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!doctype html>
<html lang="en">
  	<head>
		<%@ include file="header.jsp" %>
		<%@ include file="navbar.jsp" %>
		<title></title>
	</head>
	<body>
		<div id="login-background" class="container-fluid">
			<div id="login-container" class="container min-vh-100 d-flex flex-column justify-content-center">	
				<div class="card">
				  <div class="card-header">
				    <ul class="nav nav-tabs card-header-tabs">
				      <li class="nav-item">
				        <a class="nav-link active" href="${pageContext.request.contextPath}/showLogin">Prisijungti</a>
				      </li>
				      <li class="nav-item">
				        <a class="nav-link" href="${pageContext.request.contextPath}/register/showRegistrationForm">
				        Registruotis</a>
				      </li>
				    </ul>
				  </div>
				  <div class="card-body">
				  		<c:if test="${param.error == null && param.logout == null}">
				    		<h5 class="card-title">Sveiki!</h5>
				    	</c:if>
				    	<form:form action="${pageContext.request.contextPath}/authenticateUser"
						method="POST">
						
						<c:if test="${param.error != null}">
							<h5 class="card-title">Neteisingas vartotojo vardas arba slaptažodis</h5>
						</c:if>
						<c:if test="${param.logout != null}">
							<h5 class="card-title">Atsijungėte.</h5>
						</c:if>
						<div class="form-group">
							<label for="usernameInput">Vartotojo vardas</label>
							<input type="text" name ="username" class="form-control" 
							id="usernameInput" placeholder="Įveskite vartotojo vardą"
							required>
						</div>
						<div class="form-group">
							<label for="passwordInput">Slaptažodis</label>
							<input type="password" name="password" class="form-control" 
							id="passwordInput" placeholder="Įveskite slaptažodį"
							required>
						</div>
						<input class="btn btn-primary"  type="submit" value="Prisijungti"/>
					</form:form>
				  </div>
				</div>	
			</div>
		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>