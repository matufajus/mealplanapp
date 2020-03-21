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
		 	<form:form action="saveRecipe" cssClass="form-horizontal"
	      method="post" modelAttribute="myRecipe" enctype="multipart/form-data">
		      	<form:hidden path="id" value="0"/>
		      	<form:hidden path="image" />
		      	<div class="row">
			      	<div class="col-md-8 col-lg-6">
						<div class="form-group">
							Pavadinimas
						 	<form:input type="text" class="form-control" path="title" required="true" maxlength="50"/>
							
						
							Aprašymas
						 	<form:input type="text" class="form-control" path="description" required="true" maxlength="200"/>
				
							Patiekalo tipas<br>
					        <form:select path="mealTypes" class="selectpicker" multiple="multiple" data-live-search="true" required="true">
							<form:options items="${mealTypes}" itemLabel="label"/>
							</form:select>
							<br><br>	
							Porcijos
							<br>
<%-- 							<form:input type="number"  class="form-control" path="servings" required="true" min="1" step="1"/>4 --%>
							<div class="servings-radio">
							    <form:radiobutton class="d-none" path="servings" value="1" id="servings-1" checked="true"/>
							    <label for="servings-1">
							   		<img src="/images/x1.svg" />
							    </label>
							</div>
							<div class="servings-radio">
							   <form:radiobutton class="d-none" path="servings" value="2" id="servings-2"/>
							   <label for="servings-2">
							   		<img src="/images/x2.svg" />
							   </label>
							</div>
							<div class="servings-radio">
							   <form:radiobutton class="d-none" path="servings" value="3" id="servings-3"/>
							   <label for="servings-3">
							   		<img src="/images/x3.svg" />
							   </label>
							</div>
							<div class="servings-radio">
							   <form:radiobutton class="d-none" path="servings" value="4" id="servings-4"/>
							   <label for="servings-4">
							   		<img src="/images/x4.svg" />
							   </label>
							</div>
							<div class="custom-control custom-switch">
							<br>
								<form:checkbox path="shared" class="custom-control-input" id="sharedSwitch"/>
								<label class="custom-control-label" for="sharedSwitch">Dalintis receptu su kitais</label>
							</div>
							<br><br>
			      			
			      			<form:input type="hidden" path="author"/>
			      			<form:input type="hidden" path="owner"/>
			      			<security:authorize access="hasRole('ADMIN')">
				      			<form:input type="hidden" path="inspected"/>
				      			<form:input type="hidden" path="published"/>
			      			</security:authorize>
						</div>
					 </div>
					 <div class="form-group col-md-4 col-lg-6 text-center">	      			
			      		<c:if test="${myRecipe.image eq null}">
				        	<img id="recipe-form-image" src="/recipeImages/default.png" alt="Recepto paveikslėlis"/>
				        </c:if>	        
				        <c:if test="${myRecipe.image ne null}">
				        	<img id="recipe-form-image" src="${recipe.image}" alt="Recepto paveikslėlis"/>
				        </c:if>
		      			<form:input type = "file" class="form-control" path="imageFile" id="recipe-image-input"/>
		      		</div>
			  </div>
			  <div class="row">
			  	<div id="ingredient-container" class ="col-md-8 col-lg-6">
			  	  <label for="ingredients">Ingredientai</label>
			  	  <div class="row">
			  	  	<div class="col-6">
			  	  		Pavadinimas
			  	  	</div>
			  	  	<div class="col-3">
			  	  		Kiekis
			  	  	</div>
			  	  	<div class="col-2">
			  	  		Vienetai
			  	  	</div>
			  	  </div>			
						<div id ="ingredient-0" class = "ingredient-container row form-group">
							<div class = "col-6">
								<form:input type="text" class="food-product-name form-control" path="ingredients[0].name" required="true" maxlength="50"/>
							</div>
							<div class = "col-3">
								<form:input type="number"  class="form-control" path="ingredients[0].ammount" required="true" min="0.1" step="0.1"/>
							</div>
							<div class = "col-2">
								<form:select class="food-product-unit form-control" path="ingredients[0].unit" required="true">
								    <form:options items="${unitTypes}" itemLabel="label" />
								</form:select>
							</div>
							<form:hidden path="ingredients[0].id"/>
							<form:hidden path="ingredients[0].recipe" value="0"/>
							<div class = "col-1">
								
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
			  				<form:textarea class="preparation-area form-control col" path="preparations[0].description" required="true" maxlength="1000"/>
			  				<form:hidden path="preparations[0].id" />
							<form:hidden path="preparations[0].recipe" value="0"/>
							<div class = "col-1">
								
							</div>
			  			</div>
			  		<div id ="add-preparation-container"></div>
					<br/>
					<a id="add-preparation-button"><img class="icon-m mr-2" src="/images/plus-sign.svg"></a>
			  	</div>
			  </div>
			  <form:button type="submit" class="btn btn-primary">Išsaugoti</form:button>
		  		<a href="javascript:history.back()" class="btn btn-secondary">Atšaukti</a>			  
			</form:form>
		</div>
		
		
	
		
      </div>
    </div>
  </div>
</div>