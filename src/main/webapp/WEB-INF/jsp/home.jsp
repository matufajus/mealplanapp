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
			<picture id="home-cover">
		  		<source srcset="/images/calendar.webp" type="image/webp">
		  		<source srcset="/images/calendar.jpg" type="image/jpeg"> 
		  		<img src="/images/calendar.jpg" alt="Alt Text!">
			</picture>
			<h1 class="text-center my-5">Dienos planas</h1>
			<div class="row">
				<c:forEach var="mealType" items="${mealTypes}">
					<div class="col"><h2 class="text-center">${mealType.label}</h2></div>
				</c:forEach>
			</div>
			<div class="row">
				<c:forEach var="mealType" items="${mealTypes}">
					<div class="col text-center">
					<c:forEach var="meal" items="${meals}">
						<c:if test="${mealType == meal.mealType}">
							<c:forEach var="mealDish" items="${meal.mealDishes}">
								<c:if test="${mealDish.dish['class'].simpleName == 'Recipe'}">
									<a class='recipe-modal-link' data-toggle='modal' href='#recipeModal' data-recipe-id='${mealDish.dish.id}'><h3 class="text-center">${mealDish.dish.title}</h3></a>
								</c:if>
								<c:if test="${mealDish.dish['class'].simpleName == 'SingleDishProduct'}">
									<h4 class="text-center">${mealDish.dish.title}</h4>	
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
					</div>
				</c:forEach>
			</div>
			<div class="w-100 text-center">
				<a class="btn btn-primary px-5 mt-5" href='plan/meals?id=${planId}'>Žiūrėti visą planą</a>
			</div>
			<br>
		</div>
	</div>
	<%@ include file="kitchen.jsp"%>
	<%@ include file="recipe-modal.jsp" %>
	<%@ include file="footer.jsp"%>
	
	<script src="/js/home.js"></script>
	
</body>
</html>