

$(document).ready(function() {
  $('li.active').removeClass('active');
  $('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
  
  if (location.pathname.startsWith("/recipe/list")){
	  var selectedTypes = $(".selectedMealType");
	  var checkBoxes = $("input[name='type'");
	  	
	  checkBoxes.each(function( index ) {
		  var checkBox = this;
		  selectedTypes.each(function( index ) {
			  if(checkBox.value == this.value) 
				  checkBox.setAttribute("checked", "checked");
			});
		 
		});	
  }
		  

});



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
	
	var ammount = document.createElement("div");
	ammount.setAttribute("class", "col-4");
	var aInput = document.createElement("input");
	aInput.setAttribute("id", "ingredients"+id+".ammount");
	aInput.setAttribute("name", "ingredients["+id+"].ammount");
	aInput.setAttribute("type", "text");
	aInput.setAttribute("class", "form-control");
	aInput.setAttribute("required", "true");
	ammount.appendChild(aInput);
	ingredient.appendChild(ammount);
	
	var name = document.createElement("div");
	name.setAttribute("class", "col-4");
	var nInput = document.createElement("input");
	nInput.setAttribute("id", "ingredients"+id+".name");
	nInput.setAttribute("name", "ingredients["+id+"].name");
	nInput.setAttribute("type", "text");
	nInput.setAttribute("class", "form-control");
	nInput.setAttribute("required", "true");
	name.appendChild(nInput);
	ingredient.appendChild(name);
	
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
	console.log(recipeId);
	rInput.setAttribute("value", recipeId);
	ingredient.appendChild(rInput);
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-ingredient");
	button.setAttribute("type", "button");
	button.innerHTML = "<i class='fas fa-minus-circle fa-2x'>";
	ingredient.appendChild(button);
	
	container.appendChild(ingredient);
});

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
	button.setAttribute("type", "button");
	button.innerHTML = "<i class='fas fa-minus-circle fa-2x'>";
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


$('select').selectpicker();

$(".add-meal-button").click(function(){
	$.ajax({
		type: "GET",
		url: "recipe/getRecipes",
		success: function (data) {
			var container = $("#meal-recipes-container");
			container.attr("class", "col-6");
			var html = "<div class='row'>";
	        $.each(data, function(i, recipe) {
	        	if ((i != 0) && (i % 4 == 0)){
	        		console.log(i);
	        		html += "</div><div class='row'>";
	        	}
	            if (recipe.image == null)
	            	recipe.image = "/recipeImages/default.png";
	            html += "<div class='col-3'><a href='${pageContext.request.contextPath}/recipe/info?recipeId="+recipe.id+"'>" +
	             		"<img class='img-thumbnail' src='"+recipe.image+"'>" +
	             		"<span>"+recipe.title+"</span>" +
	             		" </a></div>";
	             
	         });
	        html +="</div>";
	        container.append(html);
	        },
	    erroe: function(){
	    	console.log("Failed to retrieve recipes");
	    }
	})
});
