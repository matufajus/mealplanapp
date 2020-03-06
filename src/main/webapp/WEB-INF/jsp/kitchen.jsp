<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="header.jsp"%>
<title></title>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container-fluid top-container blue-bg px-5">
		<br>
		<h2>Mano virtuvėje esantys maisto produktai:</h2>
		<div class="row">
			<c:forEach var="type" items="${foodTypes}">
				<div class="col food-type-products">
					<h3	>${type.label} </h3>
					<c:forEach var="product" items="${products}">
						<c:if test="${product.foodType eq type }">
							<div class="row">
								<div class="col">
									<a href="removeProduct?productId=${product.id}">x </a>
									${product.name}
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<br>
		<c:if test="${errorMessage != null}">
			<p style="color:red">${errorMessage}</p>
		</c:if>
		<form:form method="POST" action="addProduct" modelAttribute="newProduct">
			<div class="form-row">
				<div class="form-group col-2">
					<label for="product-name">Pavadinimas</label>
					<form:input class="form-control" id="product-name" path="name" placeholder="Avokadas" required="true"/>
				</div>
<!-- 				 <div class="form-group col"> -->
<!-- 					<label for="product-quantity">Kiekis</label> -->
<%-- 					<form:input class="form-control" id="product-quantity" placeholder="1 vnt" path="quantity"/> --%>
<!-- 				</div> -->
<!-- 				 <div class="form-group col"> -->
<!-- 				 	<label for="product-expiration-date">Galiojimo data</label> -->
<%-- 					<form:input class="form-control" id="product-expiration-date" type="date" path="expirationDate"/> --%>
<!-- 				</div> -->
				 <div class="form-group col-2">
				 	<label for="product-food-type">Kategorija</label>
					<form:select  class="form-control" path="foodType" id="product-food-type">
						<form:options items="${foodTypes}" itemLabel="label"/>
					</form:select>
				</div>
				<div class="form-group col">		
					<br>
					<input class="btn btn-primary mt-2" type="submit" value="Pridėti"/>	
				</div>
			</div>
		</form:form>
		<br>
		<hr>
		<br>
		<h3>Iš savo turimų produktų galite pasigaminti:</h3>
		<div class="row">
			<c:if test="${recipes.size() eq 0 }">
				<p>Deja, tokių receptų nėra..</p>
			</c:if>
			<c:set var = "i" scope = "page" value = "0"/>
		    <c:forEach var="recipe" items="${recipes}">
			    <c:if test="${(i != 0) && (i % 4 == 0)}">
	  		  		</div>
	  		  		<div class="row">
				</c:if>
			   <div class = "col recipe-thmbnl">
				    <security:authorize access="hasRole('ADMIN')">
						<a href="${pageContext.request.contextPath}/recipe/updateForm?recipeId=${recipe.id}">
					</security:authorize>
					<security:authorize access="!hasRole('ADMIN')">
						<a href="${pageContext.request.contextPath}/recipe/info?recipeId=${recipe.id}">
					</security:authorize>
					   		<img onerror="this.onerror=null;this.src='/recipeImages/default.png';" src="${recipe.image}">				   		
					   		<h2>${recipe.title}</h2>
				  		 </a>
			   </div>
			   <c:set var="i" value="${i + 1}" scope="page"/>
			</c:forEach>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>