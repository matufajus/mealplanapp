<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!doctype html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <body>
	<div class="container">
		<h1>Sukurti receptą</h1>
	 	<form:form action="saveRecipe" cssClass="form-horizontal"
      method="post" modelAttribute="recipe">
      	<form:hidden path="id" />
		  <div class="form-group">
		    <label for="title">Pavadinimas</label>
	    	<form:input type="text" class="form-control" path="title" required="true"/>
		  	<form:errors path="title" class="alert-danger"/>
		  </div>
		  <div class="form-group">
    	  	<label for="description">Aprašymas</label>
            <form:textarea class="form-control" path="description" rows="3" required="true"></form:textarea>
 		 </div>
		  <form:button type="submit" class="btn btn-primary">Išsaugoti</form:button>
		</form:form>
	</div>
	<%@ include file="footer.jsp" %>
  </body>
</html>