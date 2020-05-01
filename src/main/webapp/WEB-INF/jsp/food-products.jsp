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
  <%@ include file="navbar.jsp"%>
	<div class="container top-container">
		<a class="btn btn-primary m-4" id="addFoodProductLink" data-toggle="modal" href="#addFoodProductModal">Pridėti</a>
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Pavadinimas</th>
		       <th scope="col">Kategorija</th>
		      <th scope="col">Tankis (g/ml)</th>
		      <th scope="col">Kalorijos</th>
		      <th scope="col">Baltymai</th>
		      <th scope="col">Riebalai</th>
		      <th scope="col">Angliavandeniai</th>
		      <th></th>
		    </tr>
		  </thead>
		  <tbody>
	 		<c:forEach var="product" items="${foodProducts}" varStatus="status">
				<tr>
					<th>${status.index+1 }</th>
					<td>${product.name} </td>
					<td>${product.foodType.label}</td>
					<td>${product.density}</td>
					<td>${product.nutrition.kcal}</td>
					<td>${product.nutrition.protein}</td>
					<td>${product.nutrition.fat}</td>
					<td>${product.nutrition.carbs}</td>
<%-- 					<td><a href="deleteFoodProduct?id=${product.id}">Pašalinti</a></td> --%>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
	</div>
	
	<div class="modal fade" id="addFoodProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pridėti produktą į sistemą:</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">	
	      	<form:form action="addFoodProduct" cssClass="form-horizontal"
      method="post" modelAttribute="foodProduct" enctype="multipart/form-data">
      			Pavadinimas
      			<form:input type="text" class="form-control" path="name" required="true"/>
      			Kategorija:
      			<form:select class="form-control" path="foodType" required="true">
				    <form:options items="${foodTypes}" itemLabel="label" />
				</form:select>
				Tankis (g/ml):
				<form:input type="number" class="form-control" min="0" step="0.01" path="density" required="true"/>
				Kalorijos:
				<form:input type="number" class="form-control" min="0" step="0.1" path="nutrition.kcal" required="true"/>
				Baltymai:
				<form:input type="number" class="form-control" min="0" step="0.1" path="nutrition.protein"/>
				Riebalai:
				<form:input type="number" class="form-control" min="0" step="0.1" path="nutrition.fat"/>
				Angliavandeniai:
				<form:input type="number" class="form-control" min="0" step="0.1" path="nutrition.carbs"/>
				<form:button class="btn btn-primary"  type="submit">Išsaugoti</form:button>
  				<button type="button" class="btn btn-secondary" data-dismiss="modal">Atšaukti</button>
	      	</form:form>	       
	      </div>
	    </div>
	  </div>
	</div>
	
<!-- 	<div class="deleteFoodProductModal" tabindex="-1" role="dialog"> -->
<!-- 	  <div class="modal-dialog" role="document"> -->
<!-- 	    <div class="modal-content"> -->
<!-- 	      <div class="modal-header"> -->
<!-- 	        <h5 class="modal-title">Naikinti maisto produktą</h5> -->
<!-- 	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"> -->
<!-- 	          <span aria-hidden="true">&times;</span> -->
<!-- 	        </button> -->
<!-- 	      </div> -->
<!-- 	      <div class="modal-body"> -->
<!-- 	        <p>Prieš naikindami įsitikinkite ar produktas nėra naudojamas.</p> -->
<!-- 	      </div> -->
<!-- 	      <div class="modal-footer"> -->
<!-- 	        <button type="button" class="btn btn-primary">Naikinti</button> -->
<!-- 	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
<!-- 	      </div> -->
<!-- 	    </div> -->
<!-- 	  </div> -->
<!-- 	</div> -->
	<%@ include file="footer.jsp" %>
  </body>
</html>