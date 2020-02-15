<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!doctype html>
<html lang="en">
  <head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
  <body>
  <%@ include file="navbar.jsp"%>
	<div class="container top-container">
		<c:if test="${recipe.id eq 0}">
			<h1>Sukurti receptą</h1>
		</c:if>
		<c:if test="${recipe.id ne 0}">
			<h1>Redaguoti receptą</h1>
		</c:if>
	 	<form:form action="saveRecipe" cssClass="form-horizontal"
      method="post" modelAttribute="recipe" enctype="multipart/form-data">
      	<form:hidden path="id" />
      	<form:hidden path="image" />
	      	<div class=form-group>
	      	  	 <label for="imageFile">Paveikslėlis turi būti nedidesnis nei 128KB</label>
	      		<form:input type = "file" class="form-control" path="imageFile" id="recipe-image-input"/>
	        </div>
	        <c:if test="${recipe.image ne null}">
	        	<img id="recipe-form-image" src="${recipe.image}" alt="Paveikėlėlis nerastas"/>
	        </c:if>
		  <div class="form-group">
		  	Pavadinimas
	    	<form:input type="text" class="form-control" path="title" required="true"/>
		  	<form:errors path="title" class="alert-danger"/>
		  </div>
		  <div class="form-group">
		  	Aprašymas
	    	<form:input type="text" class="form-control" path="description" required="true"/>
		  	<form:errors path="description" class="alert-danger"/>
		  </div>
		  <div class="form-group">
		  	Patiekalo tipas:
            <form:select path="mealTypes" class="selectpicker" multiple="multiple" data-live-search="true" required="true">
			  <form:options items="${mealTypes}" itemLabel="label"/>
			</form:select>
			<form:errors path="mealTypes" class="alert-info"/>
		  </div>
		  <div class="row">
		  	<div id="ingredient-container" class ="col">
		  	  <label for="ingredients">Ingredientai</label>
		  	  <div class="row">
		  	  	<div class="col-4">
		  	  		Pavadinimas
		  	  	</div>
		  	  	<div class="col-2">
		  	  		Kiekis
		  	  	</div>
		  	  	<div class="col-2">
		  	  		Vienetai
		  	  	</div>
		  	  </div>
			  <c:forEach var="ingredient" items="${recipe.ingredients}" varStatus="status">
					<div id ="ingredient-${status.index}" class = "ingredient-container row form-group">
						<div class = "col-4">
							<form:input type="text" class="food-product-name form-control" path="ingredients[${status.index}].name" required="true"/>
						</div>
						<div class = "col-2">
							<form:input type="number"  class="form-control" path="ingredients[${status.index}].ammount" required="true" min="0.1" step="0.1"/>
						</div>
						<div class = "col-2">
							<form:select class="food-product-unit form-control" path="ingredients[${status.index}].unit" required="true">
							    <form:options items="${unitTypes}" itemLabel="label" />
							</form:select>
						</div>
						<form:hidden path="ingredients[${status.index}].id" />
						<form:hidden path="ingredients[${status.index}].recipe" />
						<a class ="remove-ingredient" type="button"><i class="fas fa-minus-circle fa-2x"></i></a>
					</div>
				</c:forEach>
				<div id ="add-ingredient-container"></div>
				<form:errors path="ingredients" class="alert-info"/>
				<br/>
				<button id="add-ingredient-button" type="button">Pridėti</button>
			  	<br/><br/>
		  	</div>
		  	<div id="preparation-container" class ="col">
		  		<label for="preparations">Paruošimo būdas:</label>
		  		<c:forEach var="preparation" items="${recipe.preparations}" varStatus="status">
		  			<div id ="preparation-${status.index}" class ="preparation-container row form-group">
		  				<p class= "preparation-index col-1">${status.index+1}</p>
		  				<form:textarea class="preparation-area form-control col" path="preparations[${status.index}].description" required="true"/>
		  				<form:hidden path="preparations[${status.index}].id" />
						<form:hidden path="preparations[${status.index}].recipe" />
						<a class ="remove-preparation" type="button"><i class="fas fa-minus-circle fa-2x"></i></a>
		  			</div>
		  		</c:forEach>
		  		<div id ="add-preparation-container"></div>
		  		<form:errors path="preparations" class="alert-info"/>
				<br/>
				<button id="add-preparation-button" type="button">Pridėti</button>
		  	</div>
		  </div>
		  
		  <form:button type="submit" class="btn btn-primary">Išsaugoti</form:button>
	  		<a href="javascript:history.back()" class="btn btn-secondary">Atšaukti</a>
		  <c:if test="${recipe.id ne 0 }">
		  	<a href="#deleteRecipeModal" class="btn btn-danger" data-toggle="modal">Ištrinti</a>
		  </c:if>
		  
		</form:form>
	</div>
	
	
	<div id="deleteRecipeModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Ištrynimo patvirtinimas</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p>Ar tikrai norite ištrinti šį receptą?</p>
	      </div>
	      <div class="modal-footer">
	        <a href="delete?recipeId=${recipe.id}" class="btn btn-danger">Ištrinti</a>
	        <button type="button" class="btn btn-primary" data-dismiss="modal">Atgal</button>
	      </div>
	    </div>
	  </div>
	</div>
	<%@ include file="footer.jsp" %>
  </body>
</html>