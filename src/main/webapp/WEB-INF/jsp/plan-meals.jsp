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
	<div id="plan-container" class="container-fluid top-container blue-bg">		
		<div class="row">
			<div class="col">
				<div class="link-to-settings m-4">
<!-- 					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#settingsModal"> -->
<!-- 						<img class="icon-sm" src="/images/settings-white.svg"> Nustatymai -->
<!-- 					</button> -->
					<a class="btn btn-primary m-2" href="/plan/list">
						 Visi planai
					</a>
					<button class="btn btn-primary m-2" onclick="printPlan()">
						<img class="icon-sm" src="/images/printer-white.svg"> Spausdinti
					</button>
				</div>
				<div id="plan-info">
					<p>Pavadinimas: ${plan.title}</p>
					<p>Dienos: ${plan.getDuration()}</p>
					<p>Kalorijos: ${plan.getCalories()}</p>
				</div>
				<c:if test="${planStyle == 'table' }">
					<%@ include file="plan-style-table.jsp"%>
				</c:if>
				<c:if test="${planStyle == 'list' }">
					<%@ include file="plan-style-list.jsp"%>
				</c:if>
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
							title="Įveskite savo virtuvėje esančius produktus, kurie bus neįtraukiami į pirkinių sąrašą">
						<input type="checkbox" class="custom-control-input" id="checkKitchenProducts">
						<label class="custom-control-label" for="checkKitchenProducts">
							<a href="/home#kitchen" > 
							Mano virtuvėje</a> esančius produktus pažymėti kaip atliktus.
						</label>
					</div>
					<div class="custom-control custom-switch" data-toggle="tooltip" data-placement="left" 
							title="Nerodyti varnele pažymėtų pirkinių sąraše.">
						<input type="checkbox" class="custom-control-input" id="hideDoneItems">
						<label class="custom-control-label" for="hideDoneItems">					
							Slėpti atliktus pirkinius.
						</label>
					</div>
				</div>	
			</div>
			<form name="saveMeal" action="/plan/createMeal" method="POST">
				<input type="hidden" name="recipeId"/>		
				<input type="hidden" name="date"/>		
				<input type="hidden" name="mealType"/>		
				<input type="hidden" name="servings"/>	
				<input type="hidden" name="addIngredients"/>	
				<input type="hidden" name="planId" value="${plan.id}"/>	
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
			</form>
		</div>
	</div>
	
	<!-- Modals -->
		
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
				<div class='recipes-section btn-group my-4 mx-5' role='group'>
					<a class='btn btn-light active' data-section="public">Visi receptai</a>							
					<a class='btn btn-light' data-section="private"> Mano receptai</a>
				</div>
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
	<script src="/js/plan.js"></script>
</body>
</html>