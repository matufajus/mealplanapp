
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

//open modal to add RECIPE to meal plan
$(document).on("click", ".open-add-recipe-meal-modal",  function(){
	let recipeId = $(this).data('recipe-id');
    let mealId = $(this).data('meal-id');
	 //clear preparations, ingredients and buttons containers
    $("#recipeDishModal .modal-body #preparations").empty();
    $("#recipeDishModal .modal-body #ingredients").empty();
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
    	//set title, image, servings, description
		$("#recipeDishModal .modal-title").text(recipe.title);  
		$("#recipeDishModal .modal-img").attr("src", recipe.image);
		if (recipe.description != null)
			$("#recipeDishModal #description span").html(recipe.description);
		//enable input for servings
		$("#recipeDishModal #servings input").attr('disabled', false);
		$("#recipeDishModal #servings input").val(recipe.servings);
		$("#recipeDishModal #servings input").data('servings', recipe.servings);
		//set nutrition
		$("#recipeDishModal #kcal span").html(recipe.nutritionForDish.kcal.toFixed(2));
		$("#recipeDishModal #carbs span").html(recipe.nutritionForDish.carbs.toFixed(2));
		$("#recipeDishModal #protein span").html(recipe.nutritionForDish.protein.toFixed(2));
		$("#recipeDishModal #fat span").html(recipe.nutritionForDish.fat.toFixed(2)); 
		//add ingredients and preparations   
    	$.each(recipe.ingredients, function(i, ingredient) {
    		$("#recipeDishModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name +
    				": " + "<span class='ingredient-ammount'>"+ ingredient.ammount.toFixed(2) +"</span> "+ingredient.unitType.label+"</p>");
         });
    	$.each(recipe.preparations, function(i, preparation) {
    		$("#recipeDishModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
         }); 
    	//set buttons
		$("#recipeDishModal .modal-body #buttons .remove").addClass('d-none');
		$("#recipeDishModal .modal-body #buttons .add").removeClass('d-none');
    	$("#recipeDishModal .modal-body #buttons .add").data("recipe-id", recipe.id);
    	//show modal
    	$('#recipeDishModal').modal("show");
	})
});

function changeIngredientsAmmounts(oldServings, newServings){
	$(".ingredient-ammount").each(function(i, ingredient){
		let newAmmount = (parseFloat($(ingredient).text()) / oldServings) * newServings;
		$(ingredient).text(newAmmount.toFixed(2));
	});
}

function changeNutrition(oldServings, newServings){
	let kcal = (parseFloat($("#recipeDishModal #kcal span").text()) / oldServings) * newServings;
	let protein = (parseFloat($("#recipeDishModal #protein span").text()) / oldServings) * newServings;
	let fat = (parseFloat($("#recipeDishModal #fat span").text()) / oldServings) * newServings;
	let carbs = (parseFloat($("#recipeDishModal #carbs span").text()) / oldServings) * newServings;
	$("#recipeDishModal #kcal span").text(kcal.toFixed(2));
	$("#recipeDishModal #protein span").text(protein.toFixed(2));
	$("#recipeDishModal #fat span").text(fat.toFixed(2));
	$("#recipeDishModal #carbs span").text(carbs.toFixed(2));
}

$("#recipeDishModal").on("change", "#servingsInput", function(){
	let newServings = $(this).val();
	let oldServings= $(this).data("servings");
	$(this).data("servings", newServings);
	changeIngredientsAmmounts(oldServings, newServings);
	changeNutrition(oldServings, newServings);
})

//open modal for editing(for now just deleting) RECIPE
$("#plan").on("click", ".open-edit-recipe-modal", function(){
	let recipeId = $(this).data('dish-id');
    let mealId = $(this).data('meal-id');
    //clear preparations, ingredients
    $("#recipeDishModal .modal-body #preparations").empty();
    $("#recipeDishModal .modal-body #ingredients").empty();
    
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
    	$.get("/plan/getMealDish", {mealId, recipeId}, function(mealDish){
        	//set title, image, description
    		$("#recipeDishModal .modal-title").text(recipe.title);  
    		$("#recipeDishModal .modal-img").attr("src", recipe.image);
    		if (recipe.description != null)
    			$("#recipeDishModal #description span").html(recipe.description);
    		//show and disable input for servings
    		$("#recipeDishModal #servings input").val(mealDish.servings);  
    		$("#recipeDishModal #servings input").attr('disabled', true);
    		//set nutrition
    		$("#recipeDishModal #kcal span").html(mealDish.nutritionForMealDish.kcal.toFixed(2));
    		$("#recipeDishModal #carbs span").html(mealDish.nutritionForMealDish.carbs.toFixed(2));
    		$("#recipeDishModal #protein span").html(mealDish.nutritionForMealDish.protein.toFixed(2));
    		$("#recipeDishModal #fat span").html(mealDish.nutritionForMealDish.fat.toFixed(2));  
  	    	//add ingredients and preparations
			$.each(recipe.ingredients, function(i, ingredient) {
				let ammount = (ingredient.ammount / recipe.servings) * mealDish.servings;
				$("#recipeDishModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name +
						": "+ ammount.toFixed(2) +" "+ingredient.unitType.label+"</p>");
			 });
			$.each(recipe.preparations, function(i, preparation) {
				$("#recipeDishModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
			 });   
			//set buttons
			$("#recipeDishModal .modal-body #buttons .remove").removeClass('d-none');
			$("#recipeDishModal .modal-body #buttons .add").addClass('d-none');
			$("#recipeDishModal .modal-body #buttons .remove").attr("href", '/plan/removeRecipe?mealId='+mealId+'&recipeId='+recipe.id);
		})
		//show modal
		$('#recipeDishModal').modal("show");
    });
    	
});

//open modal to add SINGLE dish to meal plan
$(document).on("click", ".open-add-product-meal-modal",  function(){
	var productId = $(this).data('product-id');
    var mealId = $(this).data('meal-id');
    $.get("/foodProduct/getWithNutrition",{productId}, function(product){
    	console.log(product);
    	$("#singleDishModal .modal-title").text(product.name);
    	//enable input for amount and unit type of the ingriedient/food product
    	$("#singleDishModal #amount input").attr('disabled', false);
    	$("#singleDishModal #amount select").attr('disabled', false);
    	//set nutrition for 100g
    	$("#singleDishModal #nutrition #label").html("(100g)");
		$("#singleDishModal #kcal span").html(product.nutrition.kcal.toFixed(2));
		$("#singleDishModal #carbs span").html(product.nutrition.carbs.toFixed(2));
		$("#singleDishModal #protein span").html(product.nutrition.protein.toFixed(2));
		$("#singleDishModal #fat span").html(product.nutrition.fat.toFixed(2));   
    	//set buttons
    	$("#singleDishModal .modal-body #buttons .remove").addClass('d-none');
		$("#singleDishModal .modal-body #buttons .add").removeClass('d-none');
    	$("#singleDishModal .modal-body #buttons .add").data("product-id", product.id);
    	//show modal
    	$('#singleDishModal').modal("show");
	})
});
	

//open modal for editing(for now just deleting) SINGLE dish
$(document).on("click", ".open-edit-single-dish-modal",  function(){
	var productId = $(this).data('dish-id');
    var mealId = $(this).data('meal-id');
    //get single dish info
    $.get("/plan/getSingleDish",{productId}, function(dish){
    	//set title, amount
    	$("#singleDishModal .modal-title").text(dish.ingredients[0].foodProduct.name);
		$("#singleDishModal #amount input").val(dish.ingredients[0].ammount);
		$("#singleDishModal #amount select").val(dish.ingredients[0].unitType.name);   
		$("#singleDishModal #amount input").attr('disabled', true);
		$("#singleDishModal #amount select").attr('disabled', true);
		//set nutrition for meal dish
		$("#singleDishModal #nutrition #label").html("(pasirinkto kiekio)");
		$("#singleDishModal #kcal span").html(dish.nutritionForDish.kcal.toFixed(2));
		$("#singleDishModal #carbs span").html(dish.nutritionForDish.carbs.toFixed(2));
		$("#singleDishModal #protein span").html(dish.nutritionForDish.protein.toFixed(2));
		$("#singleDishModal #fat span").html(dish.nutritionForDish.fat.toFixed(2));   
		//set buttons
		$("#singleDishModal .modal-body #buttons .remove").removeClass('d-none');
		$("#singleDishModal .modal-body #buttons .add").addClass('d-none');
		$("#singleDishModal .modal-body #buttons .remove").attr("href", '/plan/removeSingleDish?mealId='+mealId+'&singleDishId='+dish.id);
		//show modal
		$('#singleDishModal').modal("show");
	})
});


//send RECIPE dish data to be added to the plan
$(document).on("click", "#recipeDishModal .add", function() {
	let date = $("#dishes-container").data("date");
	let mealType = $("#dishes-container").data("meal-type");
	let planId = $("#plan-id").val();
	let recipeId = $(this).data("recipe-id");	
	let servings = $("#servingsInput").val();
	$.post("/plan/addDish", {recipeId, date, mealType, servings, planId}, function(){
		location.reload();
	});
});

//send SINGLE dish data to be added to the plan
$(document).on("click", "#singleDishModal .add", function() {
	let date = $("#dishes-container").data("date");
	let mealType = $("#dishes-container").data("meal-type");
	let planId = $("#plan-id").val();
	let foodProductId = $(this).data("product-id");
	let ammount = $("#amountInput").val();
	let unitType = $("#unitInput").val();
	let servings = 1;
	$.post("/plan/addDish", {foodProductId, ammount, unitType, date, mealType, servings, planId}, function(){
		location.reload();
	});
});

$('.dish-modal').on('hidden.bs.modal', function () {
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