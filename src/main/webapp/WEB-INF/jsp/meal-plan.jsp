<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="header.jsp"%>
<title></title>
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container-fluid top-container">
		<div class="row">
			<div class="col">
				<table class="table table-bordered table-striped">
					<thead class="thead-light">
						<tr class="d-flex">
							<th scope="col" class="w-20"></th>
							<c:forEach var="mealType" items="${mealTypes}">
								<th scope="col" class="w-20 text-center">
									${mealType.label}
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="date" items="${dates}" varStatus="status">
							 <tr class="d-flex">
							 	<td class="w-20 text-center"><h3 class="py-4">${date }</h3></td>
							 	<c:forEach var="mealType" items="${mealTypes}">
							 		<c:set var="hasMeal" value="false"/>
									<c:forEach var="meal" items="${meals}">	
								 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
								 			<c:set var="hasMeal" value="true"/>
								 			<td class="w-20 text-center" data-meal-type="${mealType}" data-date="${date}">
									 			${meal.recipe.title}
									 			<div>
									 				<img onerror="this.onerror=null;this.src='/recipeImages/default.png';" src="${meal.recipe.image}" style="object-fit:cover; width:100px;">
									 			</div>
									 			<a href="plan/deleteMeal?mealId=${meal.id}" class="btn btn-danger mt-1" style="font-size:100%;">Pašalinti</a>
								 			</td>
								 		</c:if> 		
							 		</c:forEach>
							 		<c:if test="${hasMeal == false}">
							 			<td class="w-20 text-center" data-meal-type="${mealType}" data-date="${date}"><a class="add-meal-button"><i class="fas fa-plus-circle fa-3x py-4"></i></a></td>
							 		</c:if>
								</c:forEach>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="m-4">
					Nori sudaryti planą mažiau arba daugiau dienų? <a href="${pageContext.request.contextPath}/settings/">Nustatymai</a>.
				</div>
			</div>
			<div id="plan-side-container" class="col-2">
				<div id="shopping-list-container">
					<h3>Pirkinių sąrašas:</h3>
					<hr/>
					<div id="shopping-not-done">				
						<c:forEach var="item" items="${shoppingList}">
							<c:if test="${!item.done}">
								<div data-name="${item.name}"><i class="far fa-square check-item"></i> ${item.name}: ${item.ammount }</div>
							</c:if>
						</c:forEach>
					</div>
					<hr/>
					<div id="shopping-done">
						<c:forEach var="item" items="${shoppingList}">
							<c:if test="${item.done}">
								<div data-name="${item.name}"><i class="far fa-check-square uncheck-item"></i> ${item.name}: ${item.ammount }</div>
							</c:if>
						</c:forEach>
					</div>				
				</div>
				<div id="meal-recipes-container">
				</div>
				<br/>
				<nav id="pagination-nav" aria-label="Page navigation" class="d-none">
				  <ul class="pagination justify-content-center">				    
				  </ul>
				</nav>
			</div>
			<form:form name="saveMeal" action="plan/createMeal" method="POST" modelAttribute="newMeal">
				<input type="hidden" name="recipeId"/>		
				<input type="hidden" name="date"/>		
				<input type="hidden" name="mealType"/>		
			</form:form>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>