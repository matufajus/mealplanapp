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
	<div class="container top-container">
		<c:if test="${recipe.id eq 0}">
			<h1>Create recipe</h1>
		</c:if>
		<c:if test="${recipe.id ne 0}">
			<h1>Edit recipe</h1>
		</c:if>
	 	<form:form action="saveRecipe" cssClass="form-horizontal"
      method="post" modelAttribute="recipe" enctype="multipart/form-data">
      	<form:hidden path="id" />
      	<form:hidden path="image" />
	      	<div class=form-group>
	      		<form:input type = "file" class="form-control" path="imageFile" id="recipe-image-input"/>
	        </div>
	        <c:if test="${recipe.image ne null}">
	        	<img id="recipe-form-image" src="${recipe.image}" alt="Image cannot be loaded"/>
	        </c:if>
		  <div class="form-group">
		    <label for="title">Title</label>
	    	<form:input type="text" class="form-control" path="title" required="true"/>
		  	<form:errors path="title" class="alert-danger"/>
		  </div>
		  <div class="form-group">
		  	Meal type:
            <form:select path="mealTypes" class="selectpicker" multiple="multiple" data-live-search="true" required="true">
			  <form:options items="${mealTypes}" itemLabel="label"/>
			</form:select>
			<form:errors path="mealTypes" class="alert-info"/>
		  </div>
		  <div class="row">
		  	<div id="ingredient-container" class ="col">
		  	  <label for="ingredients">Ingredients</label>
		  	  <div class="row">
		  	  	<div class="col-4">
		  	  		Quantity
		  	  	</div>
		  	  	<div class="col-4">
		  	  		Name
		  	  	</div>
		  	  </div>
			  <c:forEach var="ingredient" items="${recipe.ingredients}" varStatus="status">
					<div id ="ingredient-${status.index}" class = "ingredient-container row form-group">
						<div class = "col-4">
							<form:input type="text"  class="form-control" path="ingredients[${status.index}].ammount" required="true"/>
						</div>
						<div class = "col-4">
							<form:input type="text" class="form-control" path="ingredients[${status.index}].name" required="true"/>
						</div>
						<form:hidden path="ingredients[${status.index}].id" />
						<form:hidden path="ingredients[${status.index}].recipe" />
						<a class ="remove-ingredient" type="button"><i class="fas fa-minus-circle fa-2x"></i></a>
					</div>
				</c:forEach>
				<div id ="add-ingredient-container"></div>
				<form:errors path="ingredients" class="alert-info"/>
				<br/>
				<button id="add-ingredient-button" type="button">Add</button>
			  	<br/><br/>
		  	</div>
		  	<div id="preparation-container" class ="col">
		  		<label for="preparations">Preparation:</label>
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
				<button id="add-preparation-button" type="button">Add</button>
		  	</div>
		  </div>
		  
		  <form:button type="submit" class="btn btn-primary">Save</form:button>
	  		<a href="list" class="btn btn-secondary">Cancel</a>
		  <c:if test="${recipe.id ne 0 }">
		  	<a href="#deleteRecipeModal" class="btn btn-danger" data-toggle="modal">Delete</a>
		  </c:if>
		  
		</form:form>
	</div>
	
	
	<div id="deleteRecipeModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Confirm</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p>Are you sure you want to delete this recipe?</p>
	      </div>
	      <div class="modal-footer">
	        <a href="delete?recipeId=${recipe.id}" class="btn btn-danger">Delete</a>
	        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	<%@ include file="footer.jsp" %>
  </body>
</html>