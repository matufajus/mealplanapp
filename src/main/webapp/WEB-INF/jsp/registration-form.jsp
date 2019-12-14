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
		<div>
			<form:form action="${pageContext.request.contextPath}/register/processRegistrationForm"
				modelAttribute="userDTO">
				
				<h1>Registration form</h1>
				<c:if test="${registrationError != null}">
					<i>${registrationError}</i>
				</c:if>
				
				<form:errors path="username" cssClass="error" />
				<form:input path="username" placeholder="Vartotojo vardas" class="form-control" />
				
				<form:errors path="email" cssClass="error" />
				<form:input path="email" placeholder="Elektroninis paštas" class="form-control" />
				
				<form:errors path="password" cssClass="error" />
				<form:password path="password" placeholder="Slaptažodis" class="form-control" />
				
				<form:errors path="matchingPassword" cssClass="error" />
				<form:password path="matchingPassword" placeholder="Pakartoti slaptažodį" class="form-control" />

				<button class="btn btn-primary"  type="submit">Registruotis </button>
			</form:form>
		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>