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
						   		<img alt="${recipe.title} paveikslėlis" src="${recipe.image}">				   		
						   		<h2>${recipe.title}</h2>
					  		 </a>
				   </div>
				   <c:set var="i" value="${i + 1}" scope="page"/>
				</c:forEach>
			</div>
		</div>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>