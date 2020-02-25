<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!doctype html>
<html lang="en">
  	<head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
	<body>
		<%@ include file="navbar.jsp"%>
		<div id="recipe-container" class="container top-container">			
			<div class="row">
				<div class="col-6">
					<img id="recipe-image" src="${recipe.image}">
				</div>
				<div class="col-6">
					<h2>${recipe.title}</h2>
					<h3>${recipe.description }</h3>
					<c:forEach var="mealType" items="${recipe.mealTypes}">
					<span>${mealType.label} </span>
					<br>
					</c:forEach>
					<c:forEach var="ingredient" items="${recipe.ingredients}">
						<div class = "row">
							<div class = "col">
								${ingredient.name}
							</div>
							<div class = "col">
								${ingredient.ammount}
							</div>
							<div class = "col">
								${ingredient.unit}
							</div>
						</div>
					</c:forEach>
		 		</div>
		 	</div>
		 	<div class="row">
		 		<div class="col">
		 			<br>
		 			<c:forEach var="preparation" items="${recipe.preparations}" varStatus="i">
		 				<div class = "row">
		 					<div class = "col-1">
		 						<c:out value="${i.index+1}"/>
		 					</div>
		 					<div class = "col-11">
		 						${preparation.description}
		 					</div>
		 				</div>
		 			</c:forEach>
		 		</div>
	 		</div>
	 		<br>
			<security:authorize access="hasRole('ADMIN')">
				<a class="btn btn-primary" href="updateForm?recipeId=${recipe.id}">Redaguoti</a>
				<c:if test="${recipe.shared && !recipe.inspected}">
					<a class="btn btn-primary" href="approveRecipe?recipeId=${recipe.id}">Patvirtinti</a>
					<a class="btn btn-primary" href="rejectRecipe?recipeId=${recipe.id}">Atmesti</a>
				</c:if>	
			</security:authorize>
			<security:authorize access="!hasRole('ADMIN')">
				<c:if test="${isOwner}">
					<a class="btn btn-primary" href="updateForm?recipeId=${recipe.id}">Redaguoti</a>
				</c:if>
			</security:authorize>
 		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>