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
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container-fluid top-container">
		<h2>Nustatymai:</h2>
		<div class="row p-5">
			<form:form action="changePlanDays" method="POST">
				<label>Pasirinkite kelių dienų maisto planą norite sudaryti:</label>
				<input type="number" name="quantity" min="1" max="31" value="${planDays}">
  				<button class="btn btn-primary"  type="submit">Išsaugoti</button>
			</form:form>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>