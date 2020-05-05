<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  

<!-- Modal -->
<div class="modal fade" id="recipeFormModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">
				Sukurti receptą
        </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
	        <div class="container">
		 	<form id="recipe-form" action="saveRecipe" method="post" enctype="multipart/form-data">
		      	<input type="hidden" name="id" id="modal-recipe-id" value="0"/>
		      	<div class="row">
			      	<div class="col-md-8 col-lg-6">
						<div class="form-group">
							Pavadinimas
						 	<input type="text" class="form-control" name="title" required maxlength="50"/>
							
							Aprašymas
						 	<input type="text" class="form-control" name="description" maxlength="200"/>
				
							Patiekalo tipas<br>
<%-- 					        <form:select path="mealTypes" class="selectpicker" multiple="multiple" data-live-search="true" required="true"> --%>
<%-- 							<form:options items="${mealTypes}" itemLabel="label"/> --%>
<%-- 							</form:select> --%>
							<c:forEach var="mealType" items="${mealTypes}">
                    			<div class="mealType-container">	                    					
	                    			<input type="checkbox" id="${mealType}-modal-checkbox" name="mealTypes" class="mealType-checkbox d-none" value="${mealType}"/>
	                    			<label class="mealType-image" for="${mealType}-modal-checkbox" >
		                    			<span>${mealType.label}</span>
		                    			<img src="/images/${mealType}.jpg" />
	                    			</label> 
                    			</div>
                    		</c:forEach>
							<br><br>	
							Porcijos
							<br>
<%-- 							<form:input type="number"  class="form-control" path="servings" required="true" min="1" step="1"/>4 --%>
							<div class="servings-radio">
							    <input type="radio" class="d-none" name="servings" value="1" id="servings-1" checked/>
							    <label for="servings-1">
							   		<img src="/images/x1.svg" />
							    </label>
							</div>
							<div class="servings-radio">
							   <input type="radio" class="d-none" name="servings" value="2" id="servings-2"/>
							   <label for="servings-2">
							   		<img src="/images/x2.svg" />
							   </label>
							</div>
							<div class="servings-radio">
							   <input type="radio" class="d-none" name="servings" value="3" id="servings-3"/>
							   <label for="servings-3">
							   		<img src="/images/x3.svg" />
							   </label>
							</div>
							<div class="servings-radio">
							   <input type="radio" class="d-none" name="servings" value="4" id="servings-4"/>
							   <label for="servings-4">
							   		<img src="/images/x4.svg" />
							   </label>
							</div>
							<div class="custom-control custom-switch">
							<br>
								<input type="checkbox" name="shared" class="custom-control-input" id="sharedSwitch" checked/>
								<label class="custom-control-label" for="sharedSwitch">Dalintis receptu su kitais</label>
							</div>
							<br><br>
			      			
			      			<input type="hidden" name="author"/>
			      			<input type="hidden" name="owner"/>
			      			<security:authorize access="hasRole('ADMIN')">
				      			<input type="hidden" name="inspected"/>
				      			<input type="hidden" name="published"/>
			      			</security:authorize>
						</div>
					 </div>
					 <div class="form-group col-md-4 col-lg-6 text-center">	      			
			        	<img id="recipe-form-image" src="/recipeImages/default.png" alt="Recepto paveikslėlis"/>
			        	<input type="hidden" name="image"/>
		      			<input type = "file" class="form-control" name="imageFile" id="recipe-image-input"/>
		      		</div>
			  </div>
			  <div class="row">
			  	<div id="ingredient-container" class ="col-md-8 col-lg-6">
			  	  <label for="ingredients">Ingredientai</label><br> <span class="alert alert-warning d-none">Įveskite ir pasirinkite iš sąrašo</span>
			  	  <div class="row">
			  	  	<div class="col-5">
			  	  		Pavadinimas
			  	  	</div>
			  	  	<div class="col-3">
			  	  		Kiekis
			  	  	</div>
			  	  	<div class="col-3">
			  	  		Vienetai
			  	  	</div>
			  	  </div>			
						<div id ="ingredient-0" class = "ingredient-container row form-group">
							<div class = "col-5">
								<input type="text" class="ingredient-name form-control" maxlength="50" required/>
							</div>
							<div class = "col-3">
								<input type="number"  class="ammount form-control" name="ingredients[0].ammount" required min="0" step="0.1"/>
							</div>
							<div>
								<input type="hidden"  class="food-product-id form-control" name="ingredients[0].foodProductId"/>
							</div>
							<div class = "col-3">
								<select class="ingredient-unit form-control" name="ingredients[0].unitType">
									<c:forEach var="unitType" items ="${unitTypes}">
										<option value="${unitType}">${unitType.label}</option>
									</c:forEach>
								</select>
							</div>
							<input class="ingredient-id" type="hidden" name="ingredients[0].id" value="0"/>
							<div class = "col-1 remove-ingredient-btn">
								<a class ="remove-ingredient d-none"><img class="icon-m mr-2" src="/images/minus-black.svg"></a>
							</div>
						</div>
					<div id ="add-ingredient-container"></div>
					<br/>
					<a id="add-ingredient-button"><img class="icon-m mr-2" src="/images/plus-sign.svg"></a>
				  	<br/><br/>
			  	</div>
			  	<div id="preparation-container" class ="col-md-4 col-lg-6">
			  		<label for="preparations">Paruošimo būdas:</label>
			  			<div id ="preparation-0" class ="preparation-container row form-group">
			  				<p class= "preparation-index col-1">1</p>
			  				<textarea class="preparation-area form-control col" name="preparations[0].description" required maxlength="1000"></textarea>
			  				<input class="preparation-id" type="hidden" name="preparations[0].id" value="0"/>
							<div class = "col-1 remove-preparation-button">
								<a class ="remove-preparation d-none"><img class="icon-m mr-2" src="/images/minus-black.svg"></a>
							</div>
			  			</div>
			  		<div id ="add-preparation-container"></div>
					<br/>
					<a id="add-preparation-button"><img class="icon-m mr-2" src="/images/plus-sign.svg"></a>
			  	</div>
			  </div>
			  <button type="submit" class="btn btn-primary">Išsaugoti</button>
		  		<a href="javascript:history.back()" class="btn btn-secondary">Atšaukti</a>			  
			</form>
		</div>
		
		
	
		
      </div>
    </div>
  </div>
</div>