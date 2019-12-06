<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
  	<%@ include file="header.jsp" %>
	<body>
		<div class="container-fluid">
		<h1>${recipe.title}</h1>
		</div>
		<c:forEach var="ingredient" items="${recipe.ingredients}">
			${ingredient.name}
		</c:forEach>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>