<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="kitchen" class="container top-container px-5">
	<br>
	<h2>Mano virtuvėje esantys maisto produktai:</h2>
	<div id="kitchen-products" class="row">
	  <div class="col-4">
	    <div class="list-group" id="list-tab" role="tablist">
	   	</div>
	  </div>
	  <div class="col-8">
	    <div class="tab-content" id="nav-tabContent">
	      
	    </div>
	  </div>
	</div>
	<br>
	<br>
	<hr>
	<br>
	<h3>Galiu pasigaminti:</h3>
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