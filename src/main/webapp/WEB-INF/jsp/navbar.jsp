<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="${pageContext.request.contextPath}/">MealPlanApp</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/home">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/recipe/list">Recipes</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/kitchen/showProducts">My kitchen</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/plan">Plan my meals</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
    	<security:authorize access="isAuthenticated()">
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Account
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/settings/">Settings</a>
		          <div class="dropdown-divider"></div>
		          <form:form class="form-inline" action="${pageContext.request.contextPath}/logout" method="POST">
			      	<input class="btn" type="submit" value="Log out"/>
			  	  </form:form>
		        </div>
		      </li>
		    
    </security:authorize>
    </ul>
  </div>
</nav>