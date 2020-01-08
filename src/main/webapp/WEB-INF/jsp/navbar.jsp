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
        <a class="nav-link" href="${pageContext.request.contextPath}/home">Pagrindinis <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/recipe/list">Receptai</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/kitchen/showProducts">Mano virtuvė</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/">Planuoti</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/">Valgyti dabar</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
    	<security:authorize access="isAuthenticated()">
		    <form:form class="form-inline" action="${pageContext.request.contextPath}/logout" method="POST">
		       <input class="btn" type="submit" value="Atsijungti"/>
		    </form:form>
    </security:authorize>
    </ul>
  </div>
</nav>