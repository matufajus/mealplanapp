//----------------------Kitchen-------------------------

var foodTypes;
var foodProducts;
var kitchenProducts;

$(document).ready(function(){
	loadFoodTypes();
	loadFoodProducts();
	loadKitchenProducts();
})

function loadFoodTypes(){
	$.get("/kitchen/getFoodTypes", function(result){
		foodTypes = result;
		let container = $("#kitchen-products .list-group");
		$.each(foodTypes, function(i, foodType){
			$("<a>").attr({class:"list-group-item list-group-item-action", 'data-toggle': "list",
	    		href:"#list-"+foodType.position, role:"tab", "aria-controls":foodType.label}).html(foodType.label).appendTo(container);
		})
	})
}

function loadFoodProducts(){
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
	})
}

function loadKitchenProducts(){
	$.get("/kitchen/getUserProducts", function(items){
		$products = $(".product");
		$.each(items, function(index, item){
			$products.each(function(i, product){
				if (item.name == $(product).html()){
					$(product).addClass("active");
				}
			});
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
})