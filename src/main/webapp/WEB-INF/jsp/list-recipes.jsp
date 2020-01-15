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
				<div class="col-sm-auto px-5 bg-secondary min-vh-100">
		            <div class="py-2 sticky-top flex-grow-1">
		                <div class="nav flex-sm-column top-container pt-2">		                 
		                    <c:forEach var="selectedMealType" items="${selectedMealTypes}">
		                   	 <input type="hidden" class="selectedMealType" value="${selectedMealType}">
		                    </c:forEach>
		                    
	                    	<form action="list" method="GET">
	                    		<c:forEach var="mealType" items="${mealTypes}">
	                    			<input type="checkbox" name="type" value="${mealType}"> <label class="text-light">${mealType.label}</label> <br/>
	                    		</c:forEach>
                    			<input type="submit" class = "btn btn-primary" value="Search" />
	                    	</form>
		                </div>
		            </div>
	        	</div>
				<div class="col bg-light top-container pl-5">
					<security:authorize access="hasRole('ADMIN')">
						<a id="add-recipe-link" href="showForm"><i class="fas fa-plus-circle fa-2x"></i> Add new recipe</a>
					</security:authorize>
					<div class="row">
						<c:set var = "i" scope = "page" value = "0"/>
					    <c:forEach var="recipe" items="${recipes}">
						    <c:if test="${(i != 0) && (i % 4 == 0)}">
				  		  		</div>
				  		  		<div class="row">
							</c:if>
						   <div class = "col-auto recipe-thmbnl">
							    <security:authorize access="hasRole('ADMIN')">
									<a href="updateForm?recipeId=${recipe.id}">
								</security:authorize>
								<security:authorize access="!hasRole('ADMIN')">
									<a href="info?recipeId=${recipe.id}">
								</security:authorize>
										<img onerror="this.onerror=null;this.src='/recipeImages/default.png';" src="${recipe.image}" >	
								   		<h2>${recipe.title}</h2>
							  		 </a>
						   </div>
						   <c:set var="i" value="${i + 1}" scope="page"/>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>