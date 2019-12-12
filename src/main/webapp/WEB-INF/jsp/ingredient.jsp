<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
	<body>
		<div class="container-fluid">
		
		<c:forEach var="ingredient" items="${ingredients}">
			${ingredient.name}
		</c:forEach>
 		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>