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
		<%@ include file="navbar.jsp" %>
		<div class="container-fluid">
			<div class="row">
				<div id="recipes-side-nav" class="col-sm-2 px-5 min-vh-100">
		            <div class="py-2 sticky-top flex-grow-1">
		                <div class="nav flex-sm-column top-container pt-2">	         
	
		                    
	                    	<form id="recipe-side-nav-form" action="getFilteredRecipes" method="GET">
	                    		<c:forEach var="mealType" items="${mealTypes}">
	                    			<input type="checkbox" name="type" value="${mealType}"> <label>${mealType.label}</label> <br/>
	                    		</c:forEach>
	                    		
	               				Produktai:
	                    		<input type="text" value="" id="tags" name="products" class="form-control">
	                    		
	                    		<input type="hidden" name="section">
	                    		
                    			<input type="submit" class = "btn btn-primary" value="Ieškoti" />
	                    	</form>
	                    	
	                    	
	                    	
		                </div>
		            </div>
	        	</div>
				<div class="col bg-white top-container">
					<div class="row">
						
						<security:authorize access="!hasRole('ADMIN')">
								<div class="btn-group my-4 mx-5" role="group" aria-label="Basic example">
									<a href="list" class="btn btn-light">Visi receptai</a>									
									<a href="myList" class="btn btn-light"> Mano receptai</a>
								</div>
							</security:authorize>
							<br>
						
						<security:authorize access="hasRole('ADMIN')">
							<div class="btn-group my-4 mx-5" role="group" aria-label="Basic example">
								<a href="list" class="btn btn-light">Patvirtinti receptai</a>
								<a href="privateList" class="btn btn-light">Privatūs receptai</a>
								<a href="sharedList" class="btn btn-light"> Laukiantys patvirtinimo</a>
								<a href="rejectedList" class="btn btn-light">Atmesti receptai</a>
							</div>
							
							<a class="btn btn-light my-4 mx-2" href="unknownIngredients">Neatpažinti ingredientai</a>
						</security:authorize>
						<a id="add-recipe-link" class="btn btn-light my-4 mx-5" href="showForm"><img class="icon-m mr-2" src="/images/plus-sign.svg"> Pridėti receptą</a>
					</div>
					<div id="recipes-list-container" class="row">	
						<c:if test="${recipes.size() == 0}">
						<h2 class="m-3">Deja, nepavyko rasti receptų.</h2>
					</c:if>	
				    <c:forEach var="recipe" items="${recipes}">		   
					   <div class = "recipe-thmbnl">
							<a class="recipe-modal-link" data-toggle="modal" href="#recipeModal" data-recipe-id="${recipe.id}">
								<img class="zoom" src="${recipe.image}" >	
						   		<h2>${recipe.title}</h2>
					  		 </a>
					   </div>
					</c:forEach>			
					</div>
				</div>
			</div>
		</div>
		
			<!-- Modal -->
		<div class="modal fade" id="recipeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
 		
		<%@ include file="footer.jsp" %>
		<script src="/js/recipe.js"></script>
	</body>
</html>