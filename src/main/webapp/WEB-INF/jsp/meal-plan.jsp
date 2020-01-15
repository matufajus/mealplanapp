<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			<div class="col">
				<table class="table table-bordered">
					<thead class="thead-light">
						<tr class="d-flex">
							<th scope="col" class="col-sm-2"></th>
							<c:forEach var="mealType" items="${mealTypes}">
								<th scope="col" class="col-sm-2 text-center">
									${mealType.label}
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="date" items="${dates}" varStatus="status">
							 <tr class="d-flex">
							 	<td class="col-sm-2 text-center"><h3><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></h3></td>
							 	<c:forEach var="mealType" items="${mealTypes}">
							 		<c:set var="hasMeal" value="false"/>
									<c:forEach var="meal" items="${meals}">	
								 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
								 			<c:set var="hasMeal" value="true"/>
								 			<td class="col-sm-2 text-center">
								 			${meal.recipe.title}
								 			<div>
								 				<img alt="${meal.recipe.title} paveikslėlis" src="${meal.recipe.image}" style="object-fit:cover; width:100px;">
								 			</div>
								 			
								 			</td>
								 		</c:if> 		
							 		</c:forEach>
							 		<c:if test="${hasMeal == false}">
							 			<td class="col-sm-2 align-middle text-center"><a class="add-meal-button"><i class="fas fa-plus-circle fa-3x py-4"></i></a></td>
							 		</c:if>
								</c:forEach>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
				<div id="meal-recipes-container">
				
				</div>
			
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>