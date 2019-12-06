<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
  	<%@ include file="header.jsp" %>
	<body>
		<div class="container-fluid">
		<input type="button" value="Pridėti receptą"
		    onclick="window.location.href='showForm'; return false;"
		    class="btn btn-primary" />
		<br/><br/>
	   <c:forEach var="recipe" items="${recipes}">
		   <div class = container>
		   		<img alt="${recipe.title}" src="${recipe.image}">
		   </div>
		  
	        
		  </c:forEach>
		</div>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>