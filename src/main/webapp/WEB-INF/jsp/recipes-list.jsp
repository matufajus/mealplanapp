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
				<div id="recipes-side-nav" class="col-sm-2 px-1 min-vh-100">
		            <div class="py-2 sticky-top flex-grow-1">
		                <div class="nav flex-sm-column top-container pt-2">	         
                   
	                    	<form id="recipe-side-nav-form" action="getFilteredRecipes" method="GET">
	                    		<h3>Tipas</h3>
	                    		<c:forEach var="mealType" items="${mealTypes}">
	                    			<div class="mealType-container">	                    					
		                    			<input id="${mealType}-checkbox" class="mealType-checkbox d-none" type="checkbox" name="type" value="${mealType}">
		                    			<label class="mealType-image" for="${mealType}-checkbox" >
			                    			<span>${mealType.label}</span>
			                    			<picture>
												 <source type="image/webp" srcset="/images/${mealType}.webp" >
												 <source type="image/jpeg" srcset="/images/${mealType}.jpg" >
												 <img src="/images/${mealType}.jpg"  >
											</picture>
		                    			</label> 
	                    			</div>
	                    		</c:forEach>
	                    		<h3>Produktai</h3>
	                    		<div class="food-product" data-food="Avokad">
	                    			 <picture>
										 <source type="image/webp" srcset="/images/foodProducts/avocado.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/avocado.jpg" >
										 <img src="/images/foodProducts/avocado.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Brokol">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/broccoli.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/broccoli.jpg" >
										 <img src="/images/foodProducts/broccoli.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Mork">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/carrot.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/carrot.jpg" >
										 <img src="/images/foodProducts/carrot.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Kalafijor">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/cauliflower.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/cauliflower.jpg" >
										 <img src="/images/foodProducts/cauliflower.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Baklažan">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/eggplant.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/eggplant.jpg" >
										 <img src="/images/foodProducts/eggplant.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Pomidor">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/tomato.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/tomato.jpg" >
										 <img src="/images/foodProducts/tomato.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Moliūg">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/pumpkin.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/pumpkin.jpg" >
										 <img src="/images/foodProducts/pumpkin.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Gryb">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/mushroom.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/mushroom.jpg" >
										 <img src="/images/foodProducts/mushroom.jpg"  >
									</picture>
	                    		</div>
	                    		<div class="food-product" data-food="Svogūn">
	                    			<picture>
										 <source type="image/webp" srcset="/images/foodProducts/onion.webp" >
										 <source type="image/jpeg" srcset="/images/foodProducts/onion.jpg" >
										 <img src="/images/foodProducts/onion.jpg"  >
									</picture>
	                    		</div>
								
	               				<span>Daugiau:</span>
	                    		<input type="text" value="" id="tags" name="products" class="form-control">
	                    		
	                    		<input type="hidden" name="section">
	                    		
<!--                     			<input type="submit" class = "btn btn-primary" value="Ieškoti" /> -->
	                    	</form>
	                    	
	                    	
	                    	
		                </div>
		            </div>
	        	</div>
				<div class="col bg-white top-container">
					<div class="row">
						
						<security:authorize access="!hasRole('ADMIN')">
								<div class="btn-group my-4 mx-5" role="group" aria-label="Basic example">
									<a href="myList" class="btn btn-light"> Mano receptai</a>
									<a href="list" class="btn btn-light">Visų receptai</a>									
								</div>
							</security:authorize>
							<br>
						
						<security:authorize access="hasRole('ADMIN')">
							<div class="btn-group my-4 mx-5" role="group" aria-label="Basic example">
								<a href="list" class="btn btn-light">Patvirtinti receptai</a>
								<a href="sharedList" class="btn btn-light"> Laukiantys patvirtinimo</a>
							</div>
						</security:authorize>
<!-- 						<a id="add-recipe-link" class="btn btn-light my-4 mx-5" href="showForm"> -->
						<a id="add-recipe-link" class="btn btn-light my-4 mx-5" data-toggle="modal" href="#recipeFormModal">

							<img class="icon-m mr-2" src="/images/plus-sign.svg"> Pridėti receptą
						</a>
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
		<%@ include file="recipe-modal.jsp" %>
		<%@ include file="recipe-form-modal.jsp" %>
 		
		<%@ include file="footer.jsp" %>
		<script src="/js/recipe.js"></script>
	</body>
</html>