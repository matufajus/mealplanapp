
//------------------------------------RECIPE MODALS-------------------------------------

$( "#ingredient-container" ).on("click", ".remove-ingredient", function() {
	$(this).parent().remove();
});

$( "#ingredient-container" ).on("mouseover", ".remove-ingredient", function() {
	$(this).parent().siblings().children().css("border", "3px solid #ced4da");
});

$( "#ingredient-container" ).on("mouseout", ".remove-ingredient", function() {
	$(this).parent().siblings().children().css("border", "1px solid #ced4da");
});

$( "#preparation-container" ).on("mouseover", ".remove-preparation", function() {
	$(this).parent().siblings("textarea").css("border", "3px solid #ced4da");
});

$( "#preparation-container" ).on("mouseout", ".remove-preparation", function() {
	$(this).parent().siblings("textarea").css("border", "1px solid #ced4da");
});

$( "#preparation-container" ).on("click", ".remove-preparation", function() {
	var indexes = $(this).parent().parent().nextAll().find("p.preparation-index");
	indexes.each(function(){
		var id = $(this).text() - 1;
		$(this).text(id);
	});
	$(this).parent().parent().remove();
});

$("#add-ingredient-button").click(function(){
	let i = parseInt($(this).parent().find(".ingredient-container").last().attr("id").substring(11))+1;
	let ingredient = $("#recipeFormModal .ingredient-container").last().clone();
	ingredient.attr("id", "ingredient-"+i);
	ingredient.find(".ingredient-name").attr("name", "ingredients["+i+"].name");
	ingredient.find(".ingredient-name").val('');
	ingredient.find(".ammount").attr("name", "ingredients["+i+"].ammount");
	ingredient.find(".ammount").val(0);
	ingredient.find(".ingredient-unit").attr("name", "ingredients["+i+"].unitType");
	ingredient.find(".ingredient-unit").append(loadUnitTypes());
	ingredient.find(".ingredient-id").attr("name", "ingredients["+i+"].id");
	ingredient.find(".ingredient-id").val(0);
	ingredient.find(".food-product-id").attr("name", "ingredients["+i+"].foodProductId");
	ingredient.find(".food-product-id").val('');
	ingredient.find(".remove-ingredient").removeClass("d-none");
	ingredient.insertAfter($("#recipeFormModal .ingredient-container").last());
	enableIngredientNameAutocomplete();
	$('.ingredient-name').autocomplete("option", "appendTo", "#recipeFormModal");
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
	let i = parseInt($(this).parent().find(".preparation-container").last().attr("id").substring(12))+1;	
	let preparation = $("#recipeFormModal .preparation-container").last().clone();
	preparation.attr("id", "preparation-"+i);
	preparation.find(".preparation-index").html(i+1);
	preparation.find(".preparation-area").attr("name", "preparations["+i+"].description");
	preparation.find(".preparation-area").val('');
	preparation.find(".preparation-id").attr("name", "preparations["+i+"].id");
	preparation.find(".preparation-id").val(0);
	preparation.find(".remove-preparation").removeClass("d-none");
	preparation.insertAfter($("#recipeFormModal .preparation-container").last());
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
	    		$("#recipeModal .modal-body .modal-ingredients").append("<p>"+ ingredient.foodProduct.name + ": "+ ingredient.ammount +" "+ ingredient.unitType.label +"</p>");
	         });
	    	$("#recipeModal .modal-body .modal-preparations").empty();
	    	$.each(recipe.preparations, function(i, preparation) {
	    		$("#recipeModal .modal-preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
	         });   	
	    	$("#recipeModal .modal-body .modal-buttons").empty();
	    	$.get("checkAuthorizationForRecipe",{recipeId}, function(data){
	    		if (data == true){
	    			$("#recipeModal .modal-buttons").append("<a class='btn btn-primary mx-1 edit-recipe-btn' data-recipe-id="+recipeId+">Redaguoti</a>" +
	    					"<a class='btn btn-danger mx-1' href='/recipe/delete?recipeId="+recipeId+"'>Ištrinti</a>");
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
	document.getElementById("recipe-form").reset();
	$("#recipeFormModal .ingredient-container.remove-after").remove();
	$("#recipeFormModal .preparation-container.remove-after").remove();
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
		
	
		$.each(recipe.ingredients, function(i, ingr){
			let ingredient = $("#recipeFormModal .ingredient-container").first().clone();
			ingredient.attr("id", "ingredient-"+i);
			ingredient.find(".ingredient-name").attr("name", "ingredients["+i+"].name");
			ingredient.find(".ingredient-name").val(ingr.foodProduct.name);
			ingredient.find(".ammount").attr("name", "ingredients["+i+"].ammount");
			ingredient.find(".ammount").val(ingr.ammount);
			ingredient.find(".ingredient-unit").attr("name", "ingredients["+i+"].unitType");
			ingredient.find(".ingredient-unit").append(loadUnitTypes());
			ingredient.find(".ingredient-unit").val(ingr.unitType.name);
			ingredient.find(".ingredient-id").attr("name", "ingredients["+i+"].id");
			ingredient.find(".ingredient-id").val(ingr.id);
			ingredient.find(".food-product-id").attr("name", "ingredients["+i+"].foodProductId");
			ingredient.find(".food-product-id").val(ingr.foodProductId);
			if (i > 0){
				ingredient.find(".remove-ingredient").removeClass("d-none");
				ingredient.addClass("remove-after");
				ingredient.insertAfter($("#recipeFormModal .ingredient-container").last());
			}else{
				$("#recipeFormModal .ingredient-container").replaceWith(ingredient);
			}
		});
		
		$.each(recipe.preparations, function(i, prep){
			let preparation = $("#recipeFormModal .preparation-container").first().clone();
			preparation.attr("id", "preparation-"+i);
			preparation.find(".preparation-index").html(i+1);
			preparation.find(".preparation-area").attr("name", "preparations["+i+"].description");
			preparation.find(".preparation-area").val(prep.description);
			preparation.find(".preparation-id").attr("name", "preparations["+i+"].id");
			preparation.find(".preparation-id").val(prep.id);
			if (i > 0){
				preparation.find(".remove-preparation").removeClass("d-none");
				preparation.addClass("remove-after");
				preparation.insertAfter($("#recipeFormModal .preparation-container").last());
			}else{
				$("#recipeFormModal .preparation-container").replaceWith(preparation);
			}
		});
// $("#recipeModal").modal('toggle');
		$("#recipeFormModal").modal('show');	
	});
});


$("#recipeFormModal").on('shown.bs.modal', function () {
	expandTextAreas();
	enableIngredientNameAutocomplete();
	$('.ingredient-name').autocomplete("option", "appendTo", "#recipeFormModal");
});

$(document).on('hidden.bs.modal', '.modal', function () {
    $('.modal:visible').length && $(document.body).addClass('modal-open');
});

$("#add-recipe-link").click(function (){
	// reset form because it's using the same form for creating and editing
	// if editing was done before there would be some info left and we need
	// a fresh new form for adding new recipe
	$("#recipeFormModal modal-title").html("Sukurti receptą");
	$("#recipe-form-image").attr("src", "/recipeImages/default.png")
	document.getElementById("recipe-form").reset();
	$("#recipeFormModal .ingredient-container.remove-after").remove();
	$("#recipeFormModal .preparation-container.remove-after").remove();
});

function enableIngredientNameAutocomplete(){
	$('.ingredient-name').autocomplete({
		source : '/recipe/searchProducts',
		select: function( event, ui ) {
	    	$.get("/recipe/getFoodProduct", {name: ui.item['value']}, function(foodProduct){
	    		var foodProductId = $(event.target).parents().siblings().children(".food-product-id");
	    		foodProductId.val(foodProduct.id);
	    	});
	    },
	    change: function(event, ui) {
	        if (ui.item == null) {
	          event.currentTarget.value = ''; 
	          event.currentTarget.focus();
	        }
	    }
	});
}
