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
		                
		                	<security:authorize access="hasRole('ADMIN')">
								<a id="add-recipe-link" href="showForm"><img class="icon-m mr-2" src="/images/plus-sign.svg"> Pridėti naują receptą</a>
							</security:authorize>
		                	                 
		                    <c:forEach var="selectedMealType" items="${selectedMealTypes}">
		                   	 <input type="hidden" class="selectedMealType" value="${selectedMealType}">
		                    </c:forEach>
		                    
	                    	<form id="recipe-side-nav-form" action="getFilteredRecipes" method="GET">
	                    		<c:forEach var="mealType" items="${mealTypes}">
	                    			<input type="checkbox" name="type" value="${mealType}"> <label>${mealType.label}</label> <br/>
	                    		</c:forEach>
	                    		
	               				Produktai:
	                    		<input type="text" value="" data-role="tagsinput" id="tags" name="products" class="form-control">
	                    		
                    			<input type="submit" class = "btn btn-primary" value="Ieškoti" />
	                    	</form>
	                    	
	                    	
	                    	
		                </div>
		            </div>
	        	</div>
				<div id="recipes-list-container" class="col bg-light top-container">
					<c:if test="${recipes.size() == 0}">
						<h2 class="m-3">Deja, nepavyko rasti receptų.</h2>
					</c:if>	
				    <c:forEach var="recipe" items="${recipes}">
					   
					   <div class = "recipe-thmbnl">
								<a href="info?recipeId=${recipe.id}">
									<img class="zoom" src="${recipe.image}" >	
							   		<h2>${recipe.title}</h2>
						  		 </a>
					   </div>
					</c:forEach>
				
				</div>
			</div>
		</div>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>