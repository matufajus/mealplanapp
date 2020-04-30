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
	<div class="container-fluid top-container mx-auto">
		<div id="plan-today">
			<img id="home-cover" src="/images/calendar.jpg">
			<h1 class="text-center my-5">Dienos planas</h1>
			<div class="row">
				<c:forEach var="mealType" items="${mealTypes}">
					<div class="col"><h2 class="text-center">${mealType.label}</h2></div>
				</c:forEach>
			</div>
			<div class="row">
				<c:forEach var="mealType" items="${mealTypes}">
					<c:set var="hasMeal" value="false"/>
					<c:forEach var="meal" items="${meals}">
						<c:if test="${mealType == meal.mealType}">
							<div class="col text-center">
								<img class='img-thmbnl' src="${meal.recipe.image}">
								<h3 class="text-center">${meal.recipe.title}</h3>
							</div>
							<c:set var="hasMeal" value="true"/>
						</c:if>
					</c:forEach>
					<c:if test="${hasMeal == false}">
						<div class="col">
						</div>
					</c:if>
				</c:forEach>
			</div>
			<br>
		</div>
	</div>
	<%@ include file="kitchen.jsp"%>
	<%@ include file="footer.jsp"%>
	
	<script src="/js/home.js"></script>
	
</body>
</html>