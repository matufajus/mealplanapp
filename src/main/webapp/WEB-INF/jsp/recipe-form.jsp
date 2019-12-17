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
	<div class="container">
		<h1>Redaguoti receptą</h1>
	 	<form:form action="saveRecipe" cssClass="form-horizontal"
      method="post" modelAttribute="recipe" enctype="multipart/form-data">
      	<form:hidden path="id" />
      	<form:hidden path="preparations" />
	      	<div class=form-group>
	      		<form:input type = "file" class="form-control" path="imageFile" id="recipe-image-input"/>
	        </div>
	        <img id="recipe-form-image" src="${recipe.image}" alt="Nuotrauka" />
		  <div class="form-group">
		    <label for="title">Pavadinimas</label>
	    	<form:input type="text" class="form-control" path="title" required="true"/>
		  	<form:errors path="title" class="alert-danger"/>
		  </div>
		  <label for="ingredients">Ingredientai</label>
		  <c:forEach var="ingredient" items="${recipe.ingredients}" varStatus="status">
						<div id ="ingredient-${status.index}" class = "row form-group">
							<div class = "col-2">
								<form:input type="text"  class="form-control" path="ingredients[${status.index}].ammount"/>
							</div>
							<div class = "col-2">
								<form:input type="text" class="form-control" path="ingredients[${status.index}].name"/>
							</div>
							<form:hidden path="ingredients[${status.index}].id" />
							<form:hidden path="ingredients[${status.index}].recipe" />
							<button class ="remove-ingredient">Pašalinti</button>
						</div>
			</c:forEach>
		  
		  <form:button type="submit" class="btn btn-primary">Išsaugoti</form:button>
		</form:form>
	</div>
	<%@ include file="footer.jsp" %>
  </body>
</html>