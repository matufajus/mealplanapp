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
				$("<a>").attr({class:"list-group-item list-group-item-action", 'data-toggle': "list",
		    		href:"#list-"+foodType.position, role:"tab", "aria-controls":foodType.label,
		    		style:"background-image: url('/images/foodTypes/"+foodType.position+".jpg');"})
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
				let list = $("<div>").attr({id: "list-"+foodType.position, "class": "tab-pane fade", role:"tabpanel"}).appendTo(container);
				$.each(products, function(i, product){
					if(product.foodType.position == foodType.position){
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
	});
	loadAvailableRecipes();
})

function makeFirstFoodGroupActive(){
	$(".list-group-item").first().click();
}