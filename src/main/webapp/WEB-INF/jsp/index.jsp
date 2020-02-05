<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>    
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="header.jsp" %>
		<title></title>
	</head>
	<body>
		<div id="landing-cover" class="container-fluid p-0">
			<div id="landing-page-title">
				<h1>Atsidarai šaldytuvą ir... </h1>
				<h2>Nežinai ką valgyti? <br/> Neturi reikiamų produktų? <br/> Bijai, kad vėl teks valgyti kažką nesveiko? </h2>
			</div>			
			<security:authorize access="isAnonymous()">
				<div id="landing-page-login">	
				<div>
			    	<form:form action="${pageContext.request.contextPath}/authenticateUser"
					method="POST">
					
						<c:if test="${param.error != null}">
							<h5 class="card-title">Neteisingas vartotojo vardas arba slaptažodis</h5>
						</c:if>
						<c:if test="${param.logout != null}">
							<h5 class="card-title">Atsijungėte.</h5>
						</c:if>
						<div class="form-group">
							<label for="usernameInput">Vartotojo vardas</label>
							<input type="text" name ="username" class="form-control" 
							id="usernameInput" placeholder="Įveskite vartotojo vardą"
							required>
						</div>
						<div class="form-group">
							<label for="passwordInput">Slaptažodis</label>
							<input type="password" name="password" class="form-control" 
							id="passwordInput" placeholder="Įveskite slaptažodį"
							required>
						</div>
						<input class="btn btn-primary"  type="submit" value="Prisijungti"/>
					</form:form>
				  </div>
				  	<span>Naujas vartotojas?</span>			
					<a href="${pageContext.request.contextPath}/showRegistrationForm"> <span>Registruotis</span></a>
					<br>
				</div>
			</security:authorize>
			<security:authorize access="!isAnonymous()">
					<%@ include file="navbar.jsp"%>
			</security:authorize>

			<a id="scroll-down-link" href="#reasonsToPlan">
				<img src="/images/circle-down-white.svg">
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
				<h3>Tau padės: </h3><a href="#planningTools"><h2>maistoplanuote.lt</h2></a>
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