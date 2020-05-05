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
					<input id="plan-id" type="hidden" value="${plan.id}">
<%-- 					<p>Pavadinimas: ${plan.title}</p> --%>
<%-- 					<p>Dienos: ${plan.getDuration()}</p> --%>
<%-- 					<p>Kalorijos: ${plan.getCalories()}</p> --%>
				</div>
				<c:if test="${errorMessage ne null}">
					<div class="alert alert-danger" role="alert">
						${errorMessage}
					</div>
				</c:if>
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
		</div>
	</div>
	
	<!-- Modals -->
		
	<div class="modal dish-modal" id="chooseDishModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 <div id="dishes-container">
				<div class='recipes-section btn-group my-4 mx-5' role='group'>
					<a class='btn btn-light active' data-section="public">Visi receptai</a>							
					<a class='btn btn-light' data-section="private"> Mano receptai</a>
					<a class='btn btn-light' data-section="products"> Maisto produktai</a>
				</div>
				<div id="dishes-list-container">
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
		
	<div class="modal dish-modal" id="recipeDishModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 <div class='row'>
	      	 	<div class='col-4'>
	      	 		<img class='modal-img'>
      	 		</div>
      	 		<div class='col-8'>
						<div id='description' class='row my-2'>
							<div class="col">
								Aprašymas:<span class='ml-3'></span>
							</div>
						</div>
						<div id='servings' class='row my-2'>
							<div class="col">
								Porcijos: <input type='number' id='servingsInput' min='1' max='100' class='ml-3'></input>
								<span class='ml-3'></span>
							</div>
						</div>
						<div id="nutrition" class='row my-2'>
							<div class="col-4">Maistingumas:</div>
							<div class="col">
								<div id='kcal' class = "row">
									Kalorijos: <span class='mx-3'></span> kcal
								</div>
								<div id='protein' class = "row">
									Baltymai: <span class='mx-3'></span> g
								</div>
								<div id='fat' class = "row">
									Riebalai: <span class='mx-3'></span> g
								</div>
								<div id='carbs' class = "row">
									Angliavandeniai: <span class='mx-3'></span> g
								</div>
							</div>
						</div>
						<div id='buttons' class='row m-2'>
							<a class='remove btn btn-danger'>Pašalinti iš plano</a>
							<a class='add btn btn-success'>Pridėti prie plano</a>
						</div>
					</div>
				</div>
				<div class='row'>
		    		<div class='col' id='ingredients'></div>
		    		<div class='col' id='preparations'></div> 
				</div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal" id="singleDishModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	 <div class='row'>
      	 		<div class='col-8'>
						<div id='amount' class='row my-2'>
							<div class="col">
								Kiekis: <input type='number' id='amountInput' step='0.25' value='1' class='ml-3'></input>
								<select id="unitInput">
									<c:forEach var='unitType' items="${unitTypes}">
										<option value="${unitType.name}">${unitType.label}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div id="nutrition" class='row my-2'>
							<div class="col-4">Maistingumas: <span id="label"></span></div>
							<div class="col">
								<div id='kcal' class = "row">
									Kalorijos: <span class='mx-3'></span> kcal
								</div>
								<div id='protein' class = "row">
									Baltymai: <span class='mx-3'></span> g
								</div>
								<div id='fat' class = "row">
									Riebalai: <span class='mx-3'></span> g
								</div>
								<div id='carbs' class = "row">
									Angliavandeniai: <span class='mx-3'></span> g
								</div>
							</div>
						</div>
						<div id='buttons' class='row m-2'>
							<a class='remove btn btn-danger'>Pašalinti iš plano</a>
							<a class='add btn btn-success'>Pridėti prie plano</a>
						</div>
					</div>
				</div>
	      </div>
	    </div>
	  </div>
	</div>
		
	<%@ include file="footer.jsp"%>
	<script src="/js/plan.js"></script>
</body>
</html>