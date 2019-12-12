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
		<form:form action="${pageContext.request.contextPath}/authenticateUser"
			method="POST">
			<h1>Login form</h1>
			<c:if test="${param.error != null}">
				<i>Neteisingas vartotojo vardas arba slaptažodis</i>
			</c:if>
			<c:if test="${param.logout != null}">
				<i>Atsijungėte</i>
			</c:if>
			<p>
				Vartotojo vardas: <input type="text" name="username" />
			</p>
			<p>
				Slaptažodis: <input type="password" name="password" />
			</p>
			<input type="submit" value="Prisijungti"/>
		</form:form>
		<%@ include file="footer.jsp" %>
	</body>
</html>