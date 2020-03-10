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
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container-fluid top-container">
		<h1>Jou jou jou, refridgerator meg here</h1>
		<div class="row">
			<div class="col">
				<div class="mx-5 my-2">
					<a class="btn btn-primary" href="/plan/simple/newPlan">Naujas planas</a>
					<button class="btn btn-primary m-2" onclick="printPlan()">
						<img class="icon-sm" src="/images/printer-white.svg"> Spausdinti
					</button>
				</div>
				<table id="plan" class="table table-bordered table-striped">
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
							 	<td class="w-20 text-center"><h3 class="py-0">
							 		<fmt:parseDate  value="${date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
									<fmt:formatDate value="${parsedDate}" type="date" pattern="MM.dd E" var="stdDatum" />
									${stdDatum}
							 	</h3></td>
							 	<c:forEach var="mealType" items="${mealTypes}">	
						 			<c:set var="hasMeal" value="false"/>
									<c:forEach var="meal" items="${meals}">	
								 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
								 			<c:set var="hasMeal" value="true"/>
								 			<td class="w-20 text-center" data-test="${meal }" data-meal-type="${mealType}" data-date="${date}">
									 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right" data-html="true" 
									 				data-content="<div><img class='img-thmbnl' src='${meal.recipe.image}'></div>">
													<a class="open-edit-meal-modal" data-toggle="modal" href="#editMealModal" data-recipe-id="${meal.recipe.id }" data-meal-id="${meal.id }">${meal.recipe.title}</a>
												</p>
								 			</td>
								 		</c:if> 		
							 		</c:forEach>
							 		<c:if test="${hasMeal == false}">
							 			<td class="w-20 text-center" data-meal-type="${mealType}" data-date="${date}"><a class="add-meal-button" data-toggle="modal" href="#chooseRecipeModal"><img class="icon-m" src="/images/plus-sign.svg"></a></td>
							 		</c:if>
								</c:forEach>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="plan-side-container" class="col-lg-3">
				<div id="shopping-list-container">
					<h3>Pirkinių sąrašas</h3>
					<div id="shopping-items" class="collapse"></div>
       				 <a id="read-more-shopping-list" class="collapsed" data-toggle="collapse" href="#shopping-items" aria-expanded="false" aria-controls="shopping-items"></a>
					<button class="btn btn-primary m-2" onclick="printShoppingList()"><img class="icon-sm" src="/images/printer-white.svg"> Spausdinti</button>				
				</div>
				<div id="shopping-list-settings">
					<div class="custom-control custom-switch" data-toggle="tooltip" data-placement="left" 
							title="Nerodyti varnele pažymėtų pirkinių sąraše.">
						<input type="checkbox" class="custom-control-input" id="hideDoneItems">
						<label class="custom-control-label" for="hideDoneItems">					
							Slėpti atliktus pirkinius.
						</label>
					</div>
				</div>				
			</div>
			<form:form name="saveMeal" action="/plan/simple/createMeal" method="POST" modelAttribute="newMeal">
				<input type="hidden" name="recipeId"/>		
				<input type="hidden" name="date"/>		
				<input type="hidden" name="mealType"/>		
				<input type="hidden" name="servings"/>	
				<input type="hidden" name="addIngredients"/>		
			</form:form>
		</div>
	</div>
	
	<div class="modal" id="chooseRecipeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 <div id="meal-recipes-container">
				<div id="recipes-list-container">
				</div>
				<br/>
				<nav id="pagination-nav" aria-label="Page navigation">
				  <ul class="pagination justify-content-center">				    
				  </ul>
				</nav>
			</div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="editMealModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal" id="addMealModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 
	      </div>
	    </div>
	  </div>
	</div>
	
	
	
	<%@ include file="footer.jsp"%>
	<script src="/js/simple-plan.js"></script>
	<script src="/js/shopping-list.js"></script>
</body>
</html>