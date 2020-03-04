$( "#ingredient-container" ).on("click", ".remove-ingredient", function() {
	$(this).parent().remove();
});

$( "#ingredient-container" ).on("mouseover", ".remove-ingredient", function() {
	$(this).siblings().children("input").css("border", "3px solid #ced4da");
});

$( "#ingredient-container" ).on("mouseout", ".remove-ingredient", function() {
	$(this).siblings().children("input").css("border", "1px solid #ced4da");
});

$( "#preparation-container" ).on("mouseover", ".remove-preparation", function() {
	$(this).siblings("textarea").css("border", "3px solid #ced4da");
});

$( "#preparation-container" ).on("mouseout", ".remove-preparation", function() {
	$(this).siblings("textarea").css("border", "1px solid #ced4da");
});



$( "#preparation-container" ).on("click", ".remove-preparation", function() {
	var indexes = $(this).parent().nextAll().find("p.preparation-index");
	indexes.each(function(){
		var id = $(this).text() - 1;
		$(this).text(id);
	});
	$(this).parent().remove();
});

$("#add-ingredient-button").click(function(){
	var container = document.getElementById("add-ingredient-container");
	if ($(this).parent().find(".ingredient-container").last().attr("id") != null){
		var id = parseInt($(this).parent().find(".ingredient-container").last().attr("id").substring(11))+1;
	} else {
		var id = 0;
	}
	
	var ingredient = document.createElement("div");
	ingredient.setAttribute("class", "ingredient-container form-group row");
	ingredient.setAttribute("id", "ingredient-"+id);
	
	var name = document.createElement("div");
	name.setAttribute("class", "col-6");
	var nInput = document.createElement("input");
	nInput.setAttribute("id", "ingredients"+id+".name");
	nInput.setAttribute("name", "ingredients["+id+"].name");
	nInput.setAttribute("type", "text");
	nInput.setAttribute("class", "form-control food-product-name");
	nInput.setAttribute("required", "true");
	nInput.setAttribute("maxlength", "50");
	name.appendChild(nInput);
	ingredient.appendChild(name);
	
	var ammount = document.createElement("div");
	ammount.setAttribute("class", "col-3");
	var aInput = document.createElement("input");
	aInput.setAttribute("id", "ingredients"+id+".ammount");
	aInput.setAttribute("name", "ingredients["+id+"].ammount");
	aInput.setAttribute("class", "form-control");
	aInput.setAttribute("type", "number");
	aInput.setAttribute("required", "true");
	aInput.setAttribute("value", 0.0);
	aInput.setAttribute("step", "0.1");
	aInput.setAttribute("min", "0.1");
	ammount.appendChild(aInput);
	ingredient.appendChild(ammount);
	
	var unit = document.createElement("div");
	unit.setAttribute("class", "col-2");
	var uInput = document.createElement("select");
	uInput.setAttribute("id", "ingredients"+id+".unit");
	uInput.setAttribute("name", "ingredients["+id+"].unit");
	uInput.setAttribute("class", "form-control food-product-unit");
	uInput.setAttribute("required", "true");
	loadUnitTypes(uInput);
	unit.appendChild(uInput);
	ingredient.appendChild(unit);
	
	var iInput = document.createElement("input");
	iInput.setAttribute("id", "ingredients"+id+".id");
	iInput.setAttribute("name", "ingredients["+id+"].id");
	iInput.setAttribute("type", "hidden");
	iInput.setAttribute("value", 0);
	ingredient.appendChild(iInput);
	
	var rInput = document.createElement("input");
	rInput.setAttribute("id", "ingredients"+id+".recipe");
	rInput.setAttribute("name", "ingredients["+id+"].recipe");
	rInput.setAttribute("type", "hidden");
	var recipeId = document.getElementById("id").value;
	rInput.setAttribute("value", recipeId);
	ingredient.appendChild(rInput);
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-ingredient col-1");
	button.innerHTML = "<img class='icon-m mr-2' src='/images/minus-black.svg'>";
	ingredient.appendChild(button);
	
	container.appendChild(ingredient);
	
	enableFoodProductAutocomplete();
});

function loadUnitTypes(container){
	 var html = "";
	 $.get("/recipe/getUnitTypes", function(unitTypes){	
		 $.each(unitTypes, function(i, unitType) {
	    		html += "<option value="+unitType.name+">"+unitType.label+"</option>";
	         }); 
		 $(container).append(html);
	 });
}

$("#add-preparation-button").click(function(){
	var container = document.getElementById("add-preparation-container");
	
	if ($(this).parent().find(".preparation-container").last().attr("id") != null){
		var id = parseInt($(this).parent().find(".preparation-container").last().attr("id").substring(12))+1;
	} else {
		var id = 0;
	}
		
	var preparation = document.createElement("div");
	preparation.setAttribute("class", "row form-group preparation-container");
	preparation.setAttribute("id", "preparation-"+id);
	
	var p = document.createElement("p");
	var prevNumber = parseInt($(this).parent().find(".preparation-container").last().children("p").text());
	if (isNaN(prevNumber)){
		prevNumber = 0;
	}
	p.textContent=prevNumber+1;
	p.setAttribute("class","preparation-index col-1");
	preparation.appendChild(p);
	
	var description = document.createElement("textarea");
	description.setAttribute("class", "preparation-area form-control col");
	description.setAttribute("name", "preparations["+id+"].description");
	description.setAttribute("id", "preparations"+id+".description");
	description.setAttribute("required", "true");
	description.setAttribute("maxlength", "1000");
	preparation.appendChild(description);
	
	var iInput = document.createElement("input");
	iInput.setAttribute("id", "preparations"+id+".id");
	iInput.setAttribute("name", "preparations["+id+"].id");
	iInput.setAttribute("type", "hidden");
	iInput.setAttribute("value", 0);
	preparation.appendChild(iInput);
	
	var rInput = document.createElement("input");
	rInput.setAttribute("id", "preparations"+id+".recipe");
	rInput.setAttribute("name", "preparations["+id+"].recipe");
	rInput.setAttribute("type", "hidden");
	var recipeId = document.getElementById("id").value;
	console.log(recipeId);
	rInput.setAttribute("value", recipeId);
	preparation.appendChild(rInput);
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-preparation");
	button.innerHTML = "<img class='icon-m mr-2' src='/images/minus-black.svg'>";
	preparation.appendChild(button);
	
	container.appendChild(preparation);
});


function readURL(input) {
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    
	    reader.onload = function(e) {
	      $('#recipe-form-image').attr('src', e.target.result);
	    }
	    
	    reader.readAsDataURL(input.files[0]);
	  }
	}

$("#recipe-image-input").change(function() {
	  readURL(this);
});


$('textarea').each(function () {
	  this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
	}).on('input', function () {
	  this.style.height = 'auto';
	  this.style.height = (this.scrollHeight) + 'px';
	});


$('.selectpicker').selectpicker();

$("#recipes-list-container").on("click", ".recipe-modal-link", function () {
		var recipeId = $(this).data('recipe-id');
	    $("#recipeModal .modal-body").empty();
	    var html ="";
	    $.get("getRecipe",{recipeId}, function(recipe){
	    	html = "<div class='row'>" +
	    				"<div class='col-4'>"+
							"<img class='img-modal' src='"+recipe.image+"'>"+
						"</div>"+
						"<div class='col-8'>"+
							"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
							"<div class='row mt-2' id='buttons'></div>"+
						"</div>"+
					"</div>"+
		    		"<div class='row'>" +
			    		"<div class='col' id='ingredients'>Reikės:</div>"+    
			    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
		    		"</div>";
	        $("#recipeModal .modal-body").append(html);
	        
	    	$("#recipeModal .modal-title").text(recipe.title);
	    	    	
	    	$.each(recipe.ingredients, function(i, ingredient) {
	    		$("#recipeModal .modal-body #ingredients").append("<p>"+ ingredient.name + ": "+ ingredient.ammount +" "+ ingredient.unit.label +"</p>");
	         });
	    	$.each(recipe.preparations, function(i, preparation) {
	    		$("#recipeModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
	         });   	
	    	$.get("checkAuthorizationForRecipe",{recipeId}, function(data){
	    		if (data == true){
	    			$("#recipeModal .modal-body #buttons").append("<a class='btn btn-primary mx-1' href='updateForm?recipeId="+recipeId+"'>Redaguoti</a>");
	    		}
	    	$.get("hasUserRole", {role: "ROLE_ADMIN"}, function(data){
	    		if ((data == true) && (recipe.shared && !recipe.inspected)){
	    			$("#recipeModal .modal-body #buttons").append("<a class='btn btn-primary mx-1' href='approveRecipe?recipeId="+recipeId+"'>Patvirtinti</a>"+
																	"<a class='btn btn-primary mx-1' href='rejectRecipe?recipeId="+recipeId+"'>Atmesti</a>");
	    		}
	    	})
	    	});
	    	
		})
		
		
})
