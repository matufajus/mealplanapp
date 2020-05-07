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
				Maisto planavimo programa 	<br>
				Bandomoji versija <a href="/showLogin"><span class="badge badge-secondary"> Prisijungti</span></a>
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
			<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
			  <ol class="carousel-indicators">
			    <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
			    <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
			    <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
			  </ol>
			  <div class="carousel-inner">
			    <div class="carousel-item active">
				<picture class="d-block w-100">
					 <source type="image/webp" srcset="/images/screenshot-recipes.webp">
					 <img src="/images/screenshot-recipes.PNG" >
				</picture>
			      <div class="carousel-caption d-none d-md-block">
			        <h5>First slide label</h5>
			        <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
			      </div>
			    </div>
			    <div class="carousel-item">
			    <picture class="d-block w-100">
					 <source type="image/webp" srcset="/images/screenshot-plan.webp" >
					 <img src="/images/screenshot-plan.PNG"  >
				</picture>
			      <div class="carousel-caption d-none d-md-block">
			        <h5>Second slide label</h5>
			        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
			      </div>
			    </div>
			    <div class="carousel-item">
			    <picture class="d-block w-100">
					 <source type="image/webp" srcset="/images/screenshot-choose-meal.webp" >
					 <img src="/images/screenshot-choose-meal.PNG"  >
				</picture>
			      <div class="carousel-caption d-none d-md-block">
			        <h5>Third slide label</h5>
			        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
			      </div>
			    </div>
			  </div>
			  <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
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