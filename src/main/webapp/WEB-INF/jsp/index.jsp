<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>    
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="header.jsp" %>
		<%@ include file="navbar.jsp" %>
		<title></title>
	</head>
	<body>
		<div id="landing-cover" class="container-fluid p-0">
			<div id="landing-page-title">
			</div>			
			<div id="landing-page-login">		
			</div>

			<a id="scroll-down-link" href="#reasonsToPlan">
				<img src="/images/arrow-circle-down-solid.svg">
			</a>
		</div>
		<div id="reasonsToPlan" class="container-fluid">
			<div>		
				<h2>Susiplanuok maistą iš anksto!</h2>
				<br>
				<h3>- Sutaupysi laiko eidamas į parduotuvę vieną kartą ir iškart žinodamas, ką pirksi.</h3>
				<br>
				<h3>- Išleisi mažiau pinigų, pirkdamas tik tuos produktus, kurių tikrai reikės.</h3>
				<br>
				<h3>- Lengviau kontroliuosi savo mitybos įpročius.</h3>
				<br>
				<br>
				<a href="#planningTools"><h2>maistoplanuote.lt</h2></a>
			</div>
		</div>
		<div id="planningTools">
			<h1>Maisto planavimo sistema</h1>
			<div style="height:50vh;"></div>
			<hr/>
			<h1>Apie mus</h1>
			<div style="height:50vh;"></div>
		</div>
		
		
		<%@ include file="footer.jsp" %>
	</body>
</html>