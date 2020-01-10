<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>    
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
	<body>
		<h1>Hello</h1>
		<security:authorize access="isAnonymous()">
			<a href="${pageContext.request.contextPath}/showLogin"> Login</a>
			<br>
			<a href="${pageContext.request.contextPath}/register/showRegistrationForm">
				Register
			</a>
		</security:authorize>
		<%@ include file="footer.jsp" %>
	</body>
</html>