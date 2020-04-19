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
	<div class="container top-container">
		<div class="row p-4">
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPlanModal">
				<img class="icon-sm"> Naujas planas
			</button>
		</div>
		<c:if test="${errorMessage ne null}">
			<div class="alert alert-danger" role="alert">
				${errorMessage}
			</div>
		</c:if>
		<h2>Ateinantys planai:</h2>
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Pavadinimas</th>
		      <th scope="col">Nuo</th>
		      <th scope="col">Iki</th>
		    </tr>
		  </thead>
		  <tbody>
	 		<c:forEach var="plan" items="${upcomingPlans}" varStatus="status">
				<tr class="clickable-row" data-href="meals?id=${plan.id}">
					<th>${status.index+1 }</th>
					<td>
						${plan.title} 
					</td>
					<td>${plan.startDate}</td>
					<td>${plan.endDate}</td> 
				</tr>
			</c:forEach>
		  </tbody>
		</table>
		<h2>Praėję planai:</h2>
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Pavadinimas</th>
		      <th scope="col">Nuo</th>
		      <th scope="col">Iki</th>
		    </tr>
		  </thead>
		  <tbody>
	 		<c:forEach var="plan" items="${previousPlans}" varStatus="status">
				<tr>
					<th>${status.index+1 }</th>
					<td>
						${plan.title} 
					</td>
					<td>${plan.startDate}</td>
					<td>${plan.endDate}</td> 
				</tr>
			</c:forEach>
		  </tbody>
		</table>
	</div>
	
	<div class="modal" id="newPlanModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Naujas planas</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	 			<form action="createPlan" method="POST">
					<div class="form-group">
						<label for="title">Pavadinimas</label>
						<input type="text" class="form-control" name="title"/>
					</div>
					<div class="form-group">
						<label for="">Laikotarpis</label>
						<input type="text" class="form-control" name="dates"/>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input class="btn btn-primary"  type="submit" value="Sukurti"/>
				</form>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<%@ include file="footer.jsp"%>
	<script src="/js/plan.js"></script>
</body>
</html>