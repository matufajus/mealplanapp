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
		<%@ include file="navbar.jsp" %>
		<h1>Hello</h1>
<!-- 		User: <security:authentication property="principal.username"/> --%>
<!-- 		<br> -->
<!-- 		Role(s): <security:authentication property="principal.authorities"/> --%>
<!-- 		<br> -->
		<security:authorize access="hasRole('ADMIN')">
		<p>We can see that you're an admin</p>
		</security:authorize>
		<%@ include file="footer.jsp" %>
	</body>
</html>