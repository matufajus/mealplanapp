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
				Maisto planavimo programa <a href="/showLogin"><span class="badge badge-secondary"> Prisijungti</span></a>	
			</div>			
			<div id="landing-page-login">
					
			</div>
			<a id="scroll-down-link">
				<img src="/images/arrow-circle-down-solid.svg">
			</a>
		</div>
		<div id="reasonsToPlan" class="container white-bg">	
			<h1 class="py-5">Kodėl verta planuoti iš anksto?</h1>
			<div class="row">	
				<div class="col-md">
					<h2>Sutaupyk pinigų</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
				<div class="col-md">
					<h2>Valgyk sveikai</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md">
					<h2>Nešvaistyk maisto</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
				<div class="col-md">
					<h2>Mažiau streso</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
			</div>
		</div>
		<div id="planningTools" class="container white-bg">
			<h1 class="py-5">Kaip naudotis maisto planavimo sistema?</h1>
			<div class="row">	
				<div class="col-md my-auto">
					<h2>Lorem ipsum</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
				<div class="col-md p-2">
					<img src="/images/screenshot-recipes.PNG">
				</div>
			</div>
			<div class="row">
				<div class="col-md my-auto">
					<h2>Lorem ipsum</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
				<div class="col-md p-2">
					<img src="/images/screenshot-plan.PNG">
				</div>
			</div>
			<div class="row">
				<div class="col-md my-auto">
					<h2>Lorem ipsum</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
					Nulla fringilla nisi sit amet tristique scelerisque. 
					Nunc euismod turpis elit, at auctor leo consequat consectetur. 
					Suspendisse blandit ex aliquet, pellentesque nisl a, pulvinar enim. 
					Praesent a tempor justo.</p>
				</div>
				<div class="col-md p-2">
					<img src="/images/screenshot-choose-meal.PNG">
				</div>
			</div>
		</div>
		<div id="aboutUs" class="container white-bg px-5 pb-5">
			<h1 class="py-5">Parašykite mums!</h1>
			<form action="sendMail" method="POST">
				<div class="row">
					<div class="col-md">
						<input type="text" class="form-control form-control-lg" placeholder="Jūsų vardas">
					</div>
					<div class="col-md">
						<input type="email" class="form-control form-control-lg" placeholder="El. paštas">
					</div>
				</div>	    
				<textarea class="form-control form-control-lg" placeholder="Žinutė" rows="5"></textarea>
			    <button type="submit" class="btn btn-primary btn-lg" disabled>Siųsti</button>
			</form>
			
		</div>
		
		
		<%@ include file="footer.jsp" %>
	</body>
</html>