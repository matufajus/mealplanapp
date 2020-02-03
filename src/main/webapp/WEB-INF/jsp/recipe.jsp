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
		<%@ include file="navbar.jsp"%>
		<div class="container-fluid">
			<h1>${recipe.title}</h1>
			<div class="row">
				<div class="col-4">
					<img id = "recipe-image" src = ${recipe.image}>
					<br><br>
					<c:forEach var="ingredient" items="${recipe.ingredients}">
						<div class = "row">
							<div class = "col">
								${ingredient.ammount}
							</div>
							<div class = "col">
								${ingredient.name}
							</div>
						</div>
					</c:forEach>
		 		</div>
		 		<div class="col">
		 			<c:set var = "i" scope = "page" value = "1"/>
		 			<br>
		 			<c:forEach var="preparation" items="${recipe.preparations}">
		 				<div class = "row">
		 					<div class = "col-1">
		 						<c:out value="${i}"/>
		 					</div>
		 					<div class = "col-8">
		 						${preparation.description}
		 					</div>
		 					<c:set var="i" value="${i + 1}" scope="page"/>
		 				</div>
		 			</c:forEach>
		 		</div>
	 		</div>
 		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>