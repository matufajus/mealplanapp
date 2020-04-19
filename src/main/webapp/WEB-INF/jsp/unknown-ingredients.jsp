<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>   
<!doctype html>
<html lang="en">
  <head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
  <body>
  <%@ include file="navbar.jsp"%>
	<div class="container top-container">
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Pavadinimas</th>
		      <th scope="col">Vienetai</th>
		      <th scope="col">Recepto pavadinimas</th>
		      <th></th>
		    </tr>
		  </thead>
		  <tbody>
	 		<c:forEach var="ingredient" items="${ingredients}" varStatus="status">
				<tr>
					<th>${status.index+1 }</th>
					<td>
						${ingredient.name} 
						<a id="editIngredientLink" data-toggle="modal" href="#editIngredientModal" 
						data-id="${ingredient.id}" data-name="${ingredient.name}"><img class="icon-sm" src="/images/edit-black.svg"></a>
					</td>
					<td>${ingredient.unit.label}</td>
					<td> ${ingredient.recipe.title}</td> 
					<td>
						<a id="addFoodProductLink" data-toggle="modal" href="#addFoodProductModal" data-name="${ingredient.name }">Pridėti</a>
					</td>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
	</div>
	
	<div class="modal fade" id="editIngredientModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Redaguoti ingredientą:</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">	
	      	<form action="editIngredient" method="POST">
	      		Pavadinimas: <input type="text" name="name">
	      		<input type="hidden" name="id">
	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	 			<button class="btn btn-primary"  type="submit">Išsaugoti</button>
  				<button type="button" class="btn btn-secondary" data-dismiss="modal">Atšaukti</button>
	      	</form>	       
	      </div>
	    </div>
	  </div>
	</div>
	<div class="modal fade" id="addFoodProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pridėti produktą į sistemą:</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">	
	      	<form:form action="addFoodProduct" cssClass="form-horizontal"
      method="post" modelAttribute="foodProduct" enctype="multipart/form-data">
      			Pavadinimas
      			<form:input type="text" class="form-control" path="name" required="true"/>
      			Kategorija:
      			<form:select class="form-control" path="foodType" required="true">
				    <form:options items="${foodTypes}" itemLabel="label" />
				</form:select>
				Vienetai:
				<form:select class="form-control" path="unitType" required="true">
				    <form:options items="${unitTypes}" itemLabel="label" />
				</form:select>
				Kalorijos:
				<form:input type="number" class="form-control" min="0" step="1" path="kcal"/>
				<form:button class="btn btn-primary"  type="submit">Išsaugoti</form:button>
  				<button type="button" class="btn btn-secondary" data-dismiss="modal">Atšaukti</button>
	      	</form:form>	       
	      </div>
	    </div>
	  </div>
	</div>
	<%@ include file="footer.jsp" %>
  </body>
</html>