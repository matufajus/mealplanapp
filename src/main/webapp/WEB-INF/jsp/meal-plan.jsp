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
		<table class="table">
			<thead class="thead-light">
				<tr class="d-flex">
					<th scope="col" class="col-sm-1"></th>
					<c:forEach var="date" items="${dates}">
						<th scope="col" class="col-sm-2">
							<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="mealType" items="${mealTypes}" varStatus="status">
					 <tr class="d-flex">
					 	<th scope="row" class="col-sm-1">${mealType.label }</th>
					 	<c:forEach var="date" items="${dates}">
					 		<c:set var="hasMeal" value="false"/>
							<c:forEach var="meal" items="${meals}">	
						 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
						 			<c:set var="hasMeal" value="true"/>
						 			<td class="col-sm-2">
						 			${meal.recipe.title}
						 			<div>
						 				<img alt="${meal.recipe.title} paveikslėlis" src="${meal.recipe.image}" style="object-fit:cover; width:100px;">
						 			</div>
						 			
						 			</td>
						 		</c:if> 		
					 		</c:forEach>
					 		<c:if test="${hasMeal == false}">
					 			<td class="col-sm-2">None</td>
					 		</c:if>
						</c:forEach>
				    </tr>
				</c:forEach>
			</tbody>
		
		
		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>