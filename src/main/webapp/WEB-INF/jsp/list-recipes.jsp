<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
  	<%@ include file="header.jsp" %>
	<body>
		<div class="container-fluid">
			<div class="row">
				<c:set var = "i" scope = "page" value = "0"/>
			    <c:forEach var="recipe" items="${recipes}">
				    <c:if test="${(i != 0) && (i % 4 == 0)}">
		  		  		</div>
		  		  		<div class="row">
					</c:if>
				   <div class = "col recipe-thmbnl">
					   <a href="info?recipeId=${recipe.id}">
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