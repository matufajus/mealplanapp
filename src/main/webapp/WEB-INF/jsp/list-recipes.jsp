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
				<div class="col-sm-2 px-1 bg-secondary min-vh-100">
		            <div class="py-2 sticky-top flex-grow-1">
		                <div class="nav flex-sm-column top-container">
		                    <a href="" class="nav-link text-light d-none d-sm-inline">Sidebar</a>
		                    <a href="" class="nav-link text-light">Link</a>
		                    <a href="" class="nav-link text-light">Link</a>
		                    <a href="" class="nav-link text-light">Link</a>
		                    <a href="" class="nav-link text-light">Link</a>
		                    <a href="" class="nav-link text-light">Link</a>
		                </div>
		            </div>
	        	</div>
				<div class="col bg-light top-container">
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
						   <div class = "col recipe-thmbnl">
							    <security:authorize access="hasRole('ADMIN')">
									<a href="updateForm?recipeId=${recipe.id}">
								</security:authorize>
								<security:authorize access="!hasRole('ADMIN')">
									<a href="info?recipeId=${recipe.id}">
								</security:authorize>
										<c:if test="${recipe.image eq null}">
											<img alt="${recipe.title} image" src="/recipeImages/default.png" >
										</c:if>
										<c:if test="${recipe.image ne null}">
											<img alt="${recipe.title} image" src="${recipe.image}" >
										</c:if>				   		
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