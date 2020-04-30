<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${pageContext.request.contextPath}/">Maisto planuotė</a>
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
      <security:authorize access="hasRole('ADMIN')">
       <li class="nav-item">
       		<a class="nav-link" href="${pageContext.request.contextPath}/foodProduct/list">Produktai</a>
       </li>
        </security:authorize>
       <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/plan/list">Planuoti</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto mr-5">
    	<security:authorize access="isAuthenticated()">
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          <security:authentication property="name"/>
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
<%-- 		          <a class="dropdown-item" href="${pageContext.request.contextPath}/settings/">Nustatymai</a> --%>
<!-- 		          <div class="dropdown-divider"></div> -->
		          <form:form class="form-inline" action="${pageContext.request.contextPath}/logout" method="POST">
			      	<input class="btn" type="submit" value="Atsijungti"/>
			  	  </form:form>
		        </div>
		      </li>
    	</security:authorize>
    	<security:authorize access="!isAuthenticated()">
    		<li class="nav-item active">
		        <a class="nav-link" href="${pageContext.request.contextPath}/login/showLogin">Prisijungti</a>
		      </li>
   		</security:authorize>
    	
    </ul>
  </div>
</nav>