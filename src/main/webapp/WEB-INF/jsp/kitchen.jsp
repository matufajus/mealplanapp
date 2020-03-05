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
	<div class="container top-container blue-bg">
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
			<form:input path="name" placeholder="Pavadinimas" required="true"/>
			<form:input path="quantity" placeholder="Kiekis"/>
			<form:input type="date" path="expirationDate" placeholder="Galiojimo data"/>
			<form:select path="foodType">
				<form:options items="${foodTypes}" itemLabel="label"/>
			</form:select>
			<input type="submit" value="Pridėti"/>
		</form:form>
		<br>
		<hr>
		<br>
		<h3>What can I make from the products that I have?</h3>
		<div class="row">
			<c:if test="${recipes.size() eq 0 }">
				<p>Unfortunately, there are no suitable recipes for your products..</p>
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