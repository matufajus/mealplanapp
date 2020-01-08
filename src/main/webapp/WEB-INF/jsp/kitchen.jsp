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
	<div class="container-fluid top-container">
		<div class="row">
			<c:forEach var="type" items="${foodTypes}">
				<div class="col">
					<h2>${type.label} </h2>
					<c:forEach var="product" items="${products}">
						<c:if test="${product.foodType eq type }">
							<div class="row">
								<div class="col">
									${product.name}
								</div>
								<div class="col">
									${product.quantity}
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		<br>
		<form:form method="POST" action="addProduct" modelAttribute="newProduct">
			<form:input path="name" placeholder="Produktas"/>
			<form:input path="quantity" placeholder="Kiekis"/>
			<form:input type="date" path="expirationDate" placeholder="Galiojimo data"/>
			<form:select path="foodType">
				<form:options items="${foodTypes}" itemLabel="label"/>
			</form:select>
			<input type="submit" value="Pridėti"/>
		</form:form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>