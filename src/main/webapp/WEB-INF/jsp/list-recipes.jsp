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
   		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Pavadinimas</th>
		      <th scope="col">Aprašymas</th>
		      <th scope="col"></th>
		    </tr>
		  </thead>
		  <tbody>
		   <c:forEach var="tempRecipe" items="${recipes}">
		   
		   <c:url var="updateLink" value="/recipe/updateForm">
	        <c:param name="recipeId" value="${tempRecipe.id}" />
	       </c:url>
	
	       <c:url var="deleteLink" value="/recipe/delete">
	        <c:param name="recipeId" value="${tempRecipe.id}" />
	       </c:url>
	       
	       
		    <tr>
		      <td><c:out value="${tempRecipe.id}"/></td>
		      <td><c:out value="${tempRecipe.title}"/></td>
		      <td><c:out value="${tempRecipe.description}"/></td>
		      <td>
	         <!-- display the update link  -->
	         	<a href="${updateLink}">Atnaujinti</a>
	         |	 <a href="${deleteLink}"
	         onclick="if (!(confirm('Are you sure you want to delete this recipe?'))) return false">Ištrinti</a>
	        </td>
	        
		    </tr>
		  </c:forEach>
		  </tbody>
		</table>
		</div>
 		
		<%@ include file="footer.jsp" %>
	</body>
</html>