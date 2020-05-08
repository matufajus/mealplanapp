//----------------------Kitchen-------------------------

var foodTypes;
var foodProducts;
var kitchenProducts;

$(document).ready(function(){
	loadFoodTypes()
		.then(loadFoodProducts)
		.then(loadKitchenProducts)
		.then(makeFirstFoodGroupActive)
		.then(loadAvailableRecipes)
})


function loadFoodTypes(){
	return new Promise((resolve) => {
		$.get("/kitchen/getFoodTypes", function(result){
			foodTypes = result;
			let container = $("#kitchen-products .list-group");
			$.each(foodTypes, function(i, foodType){
				let background = "url('/images/foodTypes/"+foodType.value+".jpg')";
				if ($("#kitchen-products").hasClass("webp")){
					background = "url('/images/foodTypes/"+foodType.value+".webp')";
				}
				$("<a>").attr({class:"list-group-item list-group-item-action", 'data-toggle': "list",
		    		href:"#list-"+foodType.value, role:"tab", "aria-controls":foodType.label,
		    		style:"background-image:"+ background+";"})
		    		.html("<div class='blurry'></div><span>"+foodType.label+"</span>")
		    		.appendTo(container);
			})
			resolve();
		})
	})
}

function loadFoodProducts(){
	return new Promise((resolve) => {
		$.get("/recipe/getFoodProducts", function(products){
			let container = $("#kitchen-products .tab-content");
			$.each(foodTypes, function(i, foodType){
				let list = $("<div>").attr({id: "list-"+foodType.value, "class": "tab-pane fade", role:"tabpanel"}).appendTo(container);
				$.each(products, function(i, product){
					if(product.foodType.value == foodType.value){
						let item = $("<div>").attr({class:"product", style:"background-image:"+product.image, "data-id":product.id}).html(product.name).appendTo(list);
					}
				});
		    })
		    resolve();
		})
	})
}

function loadKitchenProducts(){
	return new Promise((resolve) => {
		$.get("/kitchen/getUserProducts", function(items){
			$products = $(".product");
			$.each(items, function(index, item){
				$products.each(function(i, product){
					if (item.foodProduct.name == $(product).html()){
						$(product).addClass("active");
					}
				});
			});
			resolve()
		});
	})
}

function loadAvailableRecipes(){
	return new Promise((resolve) => {
		$.get("/kitchen/getAvailableRecipes", function(recipes){
			container = $("#available-recipes");
			container.empty();
			$.each(recipes, function(i, recipe){
				container.append("<div class = 'recipe-thmbnl'>"+
						"<a class='recipe-modal-link' data-toggle='modal' href='#recipeModal' data-recipe-id='"+recipe.id+"'>"+
							"<img class='zoom' src='"+recipe.image+"' >"+	
					   		"<h2>"+recipe.title+"</h2>"+
				  		 "</a>"+
		  		 	"</div>");
				
            })
			resolve();
		});
	})
}

$("#kitchen-products").on("click", ".product", function(){
	let element = $(this);
	let id = $(element).data("id");
	let url = "";
	if ($(element).hasClass("active")){
		url = "/kitchen/removeProduct";
	}else{
		url = "/kitchen/addProduct";
	}
	$.get(url, {foodProductId:id}, function(){
		element.toggleClass("active");
		loadAvailableRecipes();
	});
})

function makeFirstFoodGroupActive(){
	$(".list-group-item").first().click();
}

$("#available-recipes, #plan-today").on("click", ".recipe-modal-link", function () {
	var recipeId = $(this).data('recipe-id');
    $.get("recipe/getRecipe",{recipeId}, function(recipe){
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
	});
})

$("#list-tab").on("click", ".list-group-item", function () {
	var elem = document.getElementById("list-of-products");
	window.scroll(0, elem.offsetTop - 56);
});
