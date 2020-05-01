
//--------------------------LIST OF PLANS-----------------------------------

var nowDate = new Date();
var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), 0, 0, 0, 0);

$(".clickable-row").click(function() {
    window.location = $(this).data("href");
});

$('input[name="dates"]').daterangepicker({
	 locale: {
	        "format": "YYYY-MM-DD",
	        "separator": " - ",
	        "applyLabel": "Pasirinkti",
	        "cancelLabel": "Atšaukti",
	        "fromLabel": "nuo",
	        "toLabel": "Iki",
	        "customRangeLabel": "Custom",
	        "daysOfWeek": [
	            "Sekm",
	            "Pirm",
	            "Antr",
	            "Treč",
	            "Ketv",
	            "Pekt",
	            "Šešt"
	        ],
	        "monthNames": [
	            "Sausis",
	            "Vasaris",
	            "Kovas",
	            "Balandis",
	            "Gegužė",
	            "Birželis",
	            "Liepa",
	            "Rugpjūtis",
	            "Rugsėjis",
	            "Spalis",
	            "Lapkritis",
	            "Gruodis"
	        ],
	        "firstDay": 1
	    },
	minDate: today,
	opens: 'left'
	}, function(start, end, label) {
		console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
});

// --------------------------PLAN MEALS-----------------------------------

$(document).ready(function() {
	if (location.pathname.startsWith("/plan/meals")){
		loadShoppingItems();
	}
});

$(".add-dish-button").click(showDishesContainer);

function showDishesContainer(){
	var date = $(this).parent().data("date");
	var mealType = $(this).parent().data("meal-type");
	$("#dishes-container").data("date", date);
	$("#dishes-container").data("meal-type", mealType);
	$(".recipes-section a.active").removeClass("active");
	$(".recipes-section a[data-section=public]").addClass("active");
	setChooseDishModalTitle();
	loadRecipes();
}

function setChooseDishModalTitle(){
	var date = $("#dishes-container").data("date");
	var mealType = $("#dishes-container").data("meal-type");
	var formattedDate = new Date(date);
	var d = formattedDate.getDate();
	var m =  formattedDate.getMonth();
	var month = getNameOfTheMonth(m);
	m += 1; 
	$("#chooseDishModal .modal-title").text(`Pasirinkti patiekalą ${month}  ${d}  dienai ${translateMealType(mealType.toLowerCase())}:`);
}

function loadRecipes(page, size){
	var recipesContainer = $("#dishes-list-container");
	if ((typeof page == "undefined") && (typeof page == "undefined")){
		var page = 0;
		var size = 16 
	}
	var section = $("#dishes-container .recipes-section .active").data("section");
	var urlString = "/recipe/getRecipes?section="+section+"&pageId="+page+"&pageSize="+size;	
	$.ajax({
		type: "GET",
		url: urlString,
		success: function (data) {
			recipesContainer.empty();
			showPageNumbers(data.totalPages, page);
			var html = "<div class='row'>";
	        $.each(data.content, function(i, recipe) {
	        	if ((i != 0) && (i % 4 == 0)){
	        		html += "</div><div class='row'>";
	        	}
	            if (recipe.image == null)
	            	recipe.image = "/recipeImages/default.png";
	            html += "<div class='col-3'><a class='open-add-recipe-meal-modal' data-dismiss='modal' data-recipe-id="+recipe.id+">"+
			             		"<img class='img-thmbnl' src='"+recipe.image+"'>" +
			             		"<span>"+recipe.title+"</span>" +
		             		"</a></div>";            
	         });
	        html +="</div>";
	        recipesContainer.append(html);
	        },
	    error: function(){
	    	console.log("Nepavyko įkelti receptų");
	    }
	})
}

function loadProducts(){
	var productsContainer = $("#dishes-list-container");
	var urlString = "/foodProduct/getAll";	
	$.ajax({
		type: "GET",
		url: urlString,
		success: function (data) {
			productsContainer.empty();
			var html = "";
	        $.each(data, function(i, product) {
	            html += "<div class='col-3 product'><a class='open-add-product-meal-modal' href='javascript:void(0);' data-dismiss='modal'" +
	            		" data-product-id="+product.id+">"+
			             		"<span>"+product.name+"</span>" +
		             		"</a></div>";            
	         });
	        productsContainer.append(html);
	        },
	    error: function(){
	    	console.log("Nepavyko įkelti receptų");
	    }
	})
}



// switching section of recipes when choosing a recipe for a meal
$(".recipes-section a").click(function(){
	$(".recipes-section a.active").removeClass("active");
	$(this).addClass("active");
	if ($(this).data("section") == ("products")){
		loadProducts();
	}else{
		loadRecipes();
	}
});

function getNameOfTheMonth(d){
	var month = new Array();
	month[0] = "Sausio";
	month[1] = "Vasario";
	month[2] = "Kovo";
	month[3] = "Balandžio";
	month[4] = "Gegužės";
	month[5] = "Birželio";
	month[6] = "Liepos";
	month[7] = "Rugpjūčio";
	month[8] = "Rugsėjo";
	month[9] = "Spalio";
	month[10] = "Lapkričio";
	month[11] = "Gruodžio";
	return month[d];
}

function translateMealType(eng){
	switch(eng) {
	  case "breakfast":
	    return "pusryčiams";;
	  case "lunch":
	    return "pietums";;
	  case "dinner":
		return "vakarienei";;
	  case "snacks":
		return "užkandžiams";
	  default:
	    return "";
	}
}

function showPageNumbers(n, current){
	var nav = $("#pagination-nav").children("ul");
	nav.empty();
	var i;
	var html = "";
	for(i=0;i<n;i++){
		if (i==current){
			html += "<li class='active page-item'><a class='page-link'>"+(parseInt(i)+1)+"</a></li>";
		}else{
			html += "<li class='page-item'><a class='page-link'>"+(parseInt(i)+1)+"</a></li>"
		}
		
	}
	nav.append(html);
}

$("#pagination-nav").on("click", ".page-link", function() {
	var page = $(this).text()-1;
	loadRecipes(page, 16);
})


$("#plan").on("click", ".open-edit-recipe-modal", function(){
	var recipeId = $(this).data('dish-id');
    var mealId = $(this).data('meal-id');
    $("#editDishModal .modal-body").empty();
    var html ="";
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
    	$.get("/plan/getMealDishServings", {mealId, recipeId}, function(servings){
    		if (recipe.description == null)
    			recipe.description="-";
    		let calories = recipe.calories / recipe.servings * servings;
			html = "<div class='row'>" +
					"<div class='col-4'>"+
						"<img class='modal-img' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
						"<div class='row'>Kalorijos:<p class='ml-3'>"+calories+" kcal</p></div>"+
						"<div class='row'>Porcijos: "+servings+"</div>"+
						"<div class='row mt-2' id='buttons'></div>"+
					"</div>"+
				"</div>"+
				"<div class='row'>" +
		    		"<div class='col' id='ingredients'>Reikės:</div>"+    
		    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
				"</div>";
		$("#editDishModal .modal-body").append(html);       
		$("#editDishModal .modal-title").text(recipe.title);    	    	
		$.each(recipe.ingredients, function(i, ingredient) {
			let ammount = (ingredient.ammount / recipe.servings) * servings;
			$("#editDishModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name + ": "+ ammount +"</p>");
		 });
		$.each(recipe.preparations, function(i, preparation) {
			$("#editDishModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
		 });   	 	
		$("#editDishModal .modal-body #buttons").append("<a class='btn btn-danger' href='/plan/removeRecipe?mealId="+mealId+"&recipeId="+recipe.id+"'>Pašalinti iš plano</a>");	
		})
		$('#editDishModal').modal("show");
    });
    	
});

$(document).on("click", ".open-edit-single-dish-modal",  function(){
	var productId = $(this).data('dish-id');
    var mealId = $(this).data('meal-id');
    $("#editDishModal .modal-body").empty();
    var html ="";
    $.get("/plan/getSingleDish",{productId}, function(dish){
    	html = "<div class='col'>" +
					"<div class='row'><span class='mx-2'>Kiekis: </span>"+ dish.ammount +" "+dish.foodProduct.unitType.label+"</div>"+
					"<div class='row mt-2' id='buttons'></div>"+
				"</div>";
        $("#editDishModal .modal-body").append(html);     
    	$("#editDishModal .modal-title").text(dish.foodProduct.name);
		$("#editDishModal .modal-body #buttons").append("<a class='btn btn-danger' href='/plan/removeSingleDish?mealId="+mealId+"&singleDishId="+dish.id+"'>Pašalinti iš plano</a>");	
    	$('#editDishModal').modal("show");
	})
});

$(document).on("click", ".open-add-recipe-meal-modal",  function(){
	var recipeId = $(this).data('recipe-id');
    var mealId = $(this).data('meal-id');
    $("#editDishModal .modal-body").empty();
    var html ="";
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
    	if (recipe.description == null)
			recipe.description="-";
    	html = "<div class='row'>" +
    				"<div class='col-4'>"+
						"<img class='modal-img' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
						"<div class='row'>Kalorijos:<p class='ml-3'><span id='calories'>"+recipe.calories+"</span> kcal</p></div>"+
    					"<div class='row'>Porcijos:<input type='number' id='numberOfServings' min='1' max='100' value='"+recipe.servings+"' " +
    							"data-servings='"+recipe.servings+"'></div>"+
    					"<div class='row mt-2' id='buttons'></div>"+
					"</div>"+
				"</div>"+
	    		"<div class='row'>" +
		    		"<div class='col' id='ingredients'>Reikės:</div>"+    
		    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
	    		"</div>";
        $("#editDishModal .modal-body").append(html);     
    	$("#editDishModal .modal-title").text(recipe.title);
    	$.each(recipe.ingredients, function(i, ingredient) {
    		$("#editDishModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name + ": " +
    				"<span class='ingredient-ammount'>"+ ingredient.ammount +"</span></p>");
         });
    	$.each(recipe.preparations, function(i, preparation) {
    		$("#editDishModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
         });   	
    	$("#editDishModal .modal-body #buttons").append("<a class='add-recipe-to-meal btn btn-success' data-recipe-id="+recipeId+">Pridėti prie plano</a>");
    	$('#editDishModal').modal("show");
	})
});

$(document).on("click", ".open-add-product-meal-modal",  function(){
	var productId = $(this).data('product-id');
    var mealId = $(this).data('meal-id');
    $("#editDishModal .modal-body").empty();
    let html ="";
    $.get("/foodProduct/get",{productId}, function(product){
    	console.log(product);
    	html = "<div class='col'>" +
// "<div class='row'>Kalorijos:<p class='ml-3'><span
// id='calories'>"+product.nutrition.calories+"</span> kcal</p></div>"+
					"<div class='row'><span class='mx-2'>Kiekis: </span>" +
						"<input type='number' id='ingredient-ammount' step='0.25' value='1'>" +
						"<select id='ingredient-unit'>"+
						"</select>"+
						"</div>"+
					"<div class='row mt-2' id='buttons'></div>"+
				"</div>";
        $("#editDishModal .modal-body").append(html);     
    	$("#editDishModal .modal-title").text(product.name);
    	$("#editDishModal .modal-body #buttons").append("<a class='add-product-to-meal btn btn-success' data-product-id="+productId+">Pridėti prie plano</a>");
    	 html = "";
    	 $.get("/recipe/getUnitTypes", function(unitTypes){	
    		 $.each(unitTypes, function(i, unitType) {
    	    		html += "<option value="+unitType.name+">"+unitType.label+"</option>";
    	         }); 
    		 $("#ingredient-unit").append(html);
    	 });
    	$('#editDishModal').modal("show");
	})
});
	


$(document).on("click", ".add-recipe-to-meal", function() {
	let recipeId = $(this).data("recipe-id");
	let date = $("#dishes-container").data("date");
	let mealType = $("#dishes-container").data("meal-type");
	let servings = $("#numberOfServings").val();
	let planId = $("#plan-id").val()
	
	$.post("/plan/addDish", {recipeId, date, mealType, servings, planId}, function(){
		location.reload();
	});
});

$(document).on("click", ".add-product-to-meal", function() {
	let foodProductId = $(this).data("product-id");
	let ammount = $("#ingredient-ammount").val();
	let unitType = $("#ingredient-unit").val();
	let date = $("#dishes-container").data("date");
	let mealType = $("#dishes-container").data("meal-type");
	let servings = 1;
	let planId = $("#plan-id").val();
	
	$.post("/plan/addDish", {foodProductId, ammount, unitType, date, mealType, servings, planId}, function(){
		location.reload();
	});
});

function changeIngredientsAmmounts(oldServings, newServings){
	$(".ingredient-ammount").each(function(i, ingredient){
		let newAmmount = (parseFloat($(ingredient).text()) / oldServings) * newServings;
		$(ingredient).text(newAmmount);
	});
}

function changeCalories(oldServings, newServings){
	let newCalories = (parseFloat($("#calories").text()) / oldServings) * newServings;
	$("#calories").text(newCalories);
}

$("#addDishModal").on("change", "#numberOfServings", function(){
	let newServings = $(this).val();
	let oldServings= $(this).data("servings");
	$(this).data("servings", newServings);
	changeIngredientsAmmounts(oldServings, newServings);
	changeCalories(oldServings, newServings);
})

$('#addDishModal').on('hidden.bs.modal', function () {
	$('#chooseMealComponentModal').modal("show");
});

function printPlan()
{       
        // hide unnecessary stuff
        $("#plan-side-container").hide();
        $(".remove-recipe").hide();
        $(".link-to-settings").hide();
        $(".btn").hide();
        $(".open-edit-meal-modal").css("color","black");
        $(".open-edit-meal-modal").css("text-decoration","none");
        $(".add-meal-button img").hide();
        $(".add-meal-button").append("<br>")
        // $("body").addClass("page-landscape");
        // Print Page
        window.print();
        // Restore orignal HTML
        location.reload();
}

// -----------------------Shopping List-----------------------------

function loadShoppingItems(){
	 $("#shopping-items").empty();
	 let planId = $("#plan-id").val();
	 $.get("/plan/getShoppingItems", {planId: planId}, function(shoppingList){
		 $.each(shoppingList, function(i, foodType){	
			 if (foodType.length > 0){
				 let html = "<div>"+i;
				 $.each(foodType, function(i, shoppingItem){
					 html = html + "<div class='shopping-item' data-plan='"+planId+"' data-name='"+shoppingItem.name+"'" +
					 		"data-units='"+shoppingItem.unitType.name+"'  data-done='"+shoppingItem.done+"'>";
					 if(!shoppingItem.done)
						html = html + "<img class='icon-sm check-item mr-1 unchecked' src='/images/rectangular.svg'>";
					 if(shoppingItem.done)
							html = html + "<img class='icon-sm check-item mr-1 checked' src='/images/rectangular-checked.svg'>";
					 html = html + shoppingItem.name + ": " + shoppingItem.ammount + " " + shoppingItem.unitType.label+"</div>";
				 })
				 html = html + "<hr/></div>";
				 $("#shopping-items").append(html);
			 }
		 });
		 if ($("#shopping-list-container #shopping-items").height() < 300){
				$("#read-more-shopping-list").hide();
		 }
	 });
}

function printShoppingList()
{       
        // hide unnecessary stuff
        $("#plan").hide();
        $(".remove-recipe").hide();
        $(".link-to-settings").hide();
        $(".btn").hide();
        $("#shopping-items").addClass("show");
        $("#shopping-list-settings").hide();
        $("#shopping-list-container a").hide();
        $("#plan-side-container").removeClass("col-2");
        $("#plan-side-container").css("position", "absolute");
        
        // Print Page
        window.print();
        // Restore orignal HTML
        location.reload();
}

$("#shopping-list-container").on("click", ".check-item", function(){
	let item = $(this);
	let planId = $(this).parent("div").data("plan");
	let name = $(this).parent("div").data("name");
	let done = $(this).parent("div").data("done");
	let units = $(this).parent("div").data("units");
	$.post("/plan/updateShoppingItem",{planId: planId, ingredientName: name, isDone: done, units: units}, function(){
		item.toggleClass("checked");
		item.toggleClass("unchecked");
		if (item.hasClass("checked")){
			item.attr('src','/images/rectangular-checked.svg');
		}else{
			item.attr('src','/images/rectangular.svg');
		}
	})
});

$('#checkKitchenProducts').change(function(){
	checkKitchenProducts();
});

function checkKitchenProducts(){	
	let shoppingList = $(".shopping-item");
	$.get("/kitchen/getUserProducts", function(products){
		$.each(products, function(i, product){
			$.each(shoppingList, function(j, item){
				// TODO: add checking if product.ammount > shoppingItem.ammount
				if (product.foodProduct.name == $(item).data("name")){
					$(item).children("img").toggleClass("checked");
					$(item).children("img").toggleClass("unchecked");
					if ($('#checkKitchenProducts').is(":checked")){
						$(item).children("img").attr('src','/images/rectangular-checked.svg');
					 }else{
						 $(item).children("img").attr('src','/images/rectangular.svg');
					 };
				}
			});
		});
	});
}

$('#hideDoneItems').change(function(){
	showOrHideDoneItems();
});


function showOrHideDoneItems(){
	if ($('#hideDoneItems').is(":checked")){
		 $(".checked").parent().hide();
	 }else{
		$(".checked").parent().show();
	 };
}