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
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-ingredient col-1");
	button.innerHTML = "<img class='icon-m mr-2' src='/images/minus-black.svg'>";
	ingredient.appendChild(button);
	
	container.appendChild(ingredient);
	
	enableFoodProductAutocomplete();
	$('.food-product-name').autocomplete("option", "appendTo", "#recipeFormModal");
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

function expandTextAreas(){
	$('textarea').each(function () {
		  this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
		}).on('input', function () {
		  this.style.height = 'auto';
		  this.style.height = (this.scrollHeight) + 'px';
		});
}



$('.selectpicker').selectpicker();

$("#recipes-list-container").on("click", ".recipe-modal-link", function () {
		var recipeId = $(this).data('recipe-id');
	    $.get("getRecipe",{recipeId}, function(recipe){
	    	$("#recipeModal .modal-title").text(recipe.title);
	    	$("#recipeModal .modal-img").attr("src", recipe.image);
	    	if (recipe.description != null){
	    		$("#recipeModal .modal-description").html(recipe.description);	
	    	}else{
	    		$("#recipeModal .modal-description").addClass("d-none");	
	    	}
	    	$("#recipeModal .modal-mealTypes").empty();
	    	$.each(recipe.mealTypes, function(i, mealType){
				$("#recipeModal .modal-mealTypes").append("<p>"+mealType.label+"</p>");
			})
	    	$("#recipeModal .modal-servings").html(recipe.servings);	
	    	$("#recipeModal .modal-body .modal-ingredients").empty();
	    	$.each(recipe.ingredients, function(i, ingredient) {
	    		$("#recipeModal .modal-body .modal-ingredients").append("<p>"+ ingredient.name + ": "+ ingredient.ammount +" "+ ingredient.unit.label +"</p>");
	         });
	    	$("#recipeModal .modal-body .modal-preparations").empty();
	    	$.each(recipe.preparations, function(i, preparation) {
	    		$("#recipeModal .modal-preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
	         });   	
	    	$("#recipeModal .modal-body .modal-buttons").empty();
	    	$.get("checkAuthorizationForRecipe",{recipeId}, function(data){
	    		if (data == true){
	    			$("#recipeModal .modal-buttons").append("<a class='btn btn-primary mx-1 edit-recipe-btn' data-recipe-id="+recipeId+">Redaguoti</a>");
	    		}
	    	$.get("hasUserRole", {role: "ROLE_ADMIN"}, function(data){
	    		if ((data == true) && (recipe.shared && !recipe.inspected)){
	    			$("#recipeModal .modal-buttons").append("<a class='btn btn-primary mx-1' href='approveRecipe?recipeId="+recipeId+"'>Patvirtinti</a>"+
																	"<a class='btn btn-primary mx-1' href='rejectRecipe?recipeId="+recipeId+"'>Atmesti</a>");
	    		}
	    	})
	    	});
		})
})

$("#recipeFormModal").on("shown.bs.modal", function(){
	enableFoodProductAutocomplete();
	$('.food-product-name').autocomplete("option", "appendTo", "#recipeFormModal");
})



$("#recipeFormModal form").submit(function(event){
	event.preventDefault();
	var form = $(this);
    var url = form.attr('action');
	$.ajax({
		url: form.attr("action"),
		type: form.attr("method"),
		data: new FormData(this),
		processData: false,
		contentType: false,
		success: function(result){
			$("#recipeFormModal").modal('toggle');
			window.location.replace("/recipe/myList");
        },
		error: function(result){
			console.log(result.responseJSON);
			$.each(result.responseJSON.errors, function(i, error){	
					$("#"+error.field+"-error").html(error.defaultMessage);
			});
	           
	    }
    });
});

$("#recipeModal").on("click", ".edit-recipe-btn", function(){
	let recipeId = $(this).data("recipe-id");	
	$.get("getRecipe",{recipeId}, function(recipe){
		$("#recipeFormModal .modal-title").html("Redaguoti receptą");
		$("#recipeFormModal input[name=id]").val(recipe.id);
		$("#recipeFormModal input[name=title]").val(recipe.title);
		if (recipe.description == null){
			recipe.description = "";
		}
		$("#recipeFormModal input[name=description]").val(recipe.description);
		$.each(recipe.mealTypes, function(i, mealType){
			$("#recipeFormModal input[name=mealTypes][value="+mealType.name+"]").prop("checked", true);
		})
		$("#recipeFormModal input[name=servings][value="+recipe.servings+"]").prop("checked", true);
		$("#recipeFormModal input[name=shared]").prop("checked", recipe.shared);
		$("#recipeFormModal input[name=author]").val(recipe.author);
		$("#recipeFormModal input[name=owner]").val(recipe.owner);
		$("#recipeFormModal input[name=inspected]").val(recipe.inspected);
		$("#recipeFormModal input[name=published]").val(recipe.published);
		$("#recipeFormModal #recipe-form-image").attr("src", recipe.image);
		$("#recipeFormModal input[name=image]").val(recipe.image);
		
	
		
		$.each(recipe.ingredients, function(i, item){
			let ingredient = $("#recipeFormModal .ingredient-container").last().clone();
			ingredient.attr("id", "ingredient-"+i);
			ingredient.find(".food-product-name").attr("name", "ingredients["+i+"].name");
			ingredient.find(".food-product-name").val(item.name);
			ingredient.find(".ammount").attr("name", "ingredients["+i+"].ammount");
			ingredient.find(".ammount").val(item.ammount);
			ingredient.find(".food-product-unit").attr("name", "ingredients["+i+"].unit");
			ingredient.find(".food-product-unit").append(loadUnitTypes());
			ingredient.find(".food-product-unit").val(item.unit.name);
			ingredient.find(".ingredient-id").attr("name", "ingredients["+i+"].id");
			ingredient.find(".ingredient-id").val(item.id);
			if (i > 0){
				ingredient.find(".remove-ingredient").removeClass("d-none");
				ingredient.insertAfter($("#recipeFormModal .ingredient-container").last());
			}else{
				$("#recipeFormModal .ingredient-container").replaceWith(ingredient);
			}
		});
		
		$.each(recipe.preparations, function(i, prep){
			let preparation = $("#recipeFormModal .preparation-container").last().clone();
			preparation.attr("id", "preparation-"+i);
			preparation.find(".preparation-index").html(i+1);
			preparation.find(".preparation-area").attr("name", "preparations["+i+"].description");
			preparation.find(".preparation-area").val(prep.description);
			preparation.find(".preparation-id").attr("name", "preparations["+i+"].id");
			preparation.find(".preparation-id").val(prep.id);
			if (i > 0){
				preparation.find(".remove-preparation").removeClass("d-none");
				preparation.insertAfter($("#recipeFormModal .preparation-container").last());
			}else{
				$("#recipeFormModal .preparation-container").replaceWith(preparation);
			}
		});
//		$("#recipeModal").modal('toggle');
		$("#recipeFormModal").modal('show');	
	});
});


$("#recipeFormModal").on('shown.bs.modal', function () {
	expandTextAreas();
});

$(document).on('hidden.bs.modal', '.modal', function () {
    $('.modal:visible').length && $(document.body).addClass('modal-open');
});

$("#add-recipe-link").click(function (){
	//reset form because it's using the same form for creating and editing
	//if editing was done before there would be some info left and we need
	//a fresh new form for adding new recipe
	$("#recipeFormModal modal-title").html("Sukurti receptą");
	$("#recipe-form-image").attr("src", "/recipeImages/default.png")
	document.getElementById("recipe-form").reset();
});

