<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="header.jsp"%>
<title></title>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="top-container">
		<h1>Sveiki</h1>
		<security:authorize access="hasRole('ADMIN')">
			<p>Jūs naudojatės administratoriaus teisėmis</p>
		</security:authorize>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>