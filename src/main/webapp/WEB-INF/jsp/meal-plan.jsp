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
	<div id="plan-container" class="container-fluid top-container">
		
		<div class="row">
			<div class="col">
				<div class="link-to-settings m-4">
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#settingsModal">
						<img class="icon-sm" src="/images/settings-white.svg"> Nustatymai
					</button>
				</div>
				<c:if test="${planStyle == 'table' }">
					<%@ include file="plan-table.jsp"%>
				</c:if>
				<c:if test="${planStyle == 'list' }">
					<%@ include file="plan-list.jsp"%>
				</c:if>
				<button class="btn btn-primary m-2" onclick="printPlan()">Spausdinti planą</button>
			</div>
			<div id="plan-side-container" class="col-3">
				<div id="shopping-list-container">
					<h3>Pirkinių sąrašas:</h3>
					<hr/>
					<c:if test="${shoppingList.size() == 0 }">
						Tuščia
					</c:if>
					<div id="shopping-not-done"></div>
					<hr/>
					<div id="shopping-done"></div>
					<button class="btn btn-primary m-2" onclick="printShoppingList()">Spausdinti pirkinių sąrašą</button>				
				</div>
				<div id ="hide-meal-recipes-container-icon" class="d-none">&#10006;</div>
				<div id="meal-recipes-container" class="d-none">
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
				<input type="hidden" name="servings"/>		
			</form:form>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="settingsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Plano nustatymai:</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
		        <div class="row pl-3">
				<form:form action="plan/changePlanSettings" method="POST">
					<label>Pasirinkite kelių dienų maisto planą norite sudaryti:</label>
					<input type="number" name="quantity" min="1" max="31" value="${planDays}">
					<br>
					<label>Pasirinkite norimą plano atvaizdavimo būdą:</label>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="planStyle" id="plan-table" value="table" ${planStyle == 'table' ? 'checked' : '' }>
					  <label class="form-check-label" for="plan-table">
					    Lentelė
					  </label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="planStyle" id="plan-list" value="list" ${planStyle == 'list' ? 'checked' : '' }>
					  <label class="form-check-label" for="plan-list">
					    Sąrašas
					  </label>
					</div>
					<br>
	  				<button class="btn btn-primary"  type="submit">Išsaugoti</button>
	  				<button type="button" class="btn btn-secondary" data-dismiss="modal">Atšaukti</button>
				</form:form>
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
		
	<%@ include file="footer.jsp"%>
</body>
</html>