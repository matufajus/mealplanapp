
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

//--------------------------PLAN MEALS-----------------------------------

$(document).ready(function() {
	if (location.pathname.startsWith("/plan/meals")){
		loadShoppingItems();
	}
});

$(".add-meal-button").click(showRecipesContainer);

function showRecipesContainer(){
	var date = $(this).parent().data("date");
	var mealType = $(this).parent().data("meal-type");
	$("#meal-components-container").data("date", date);
	$("#meal-components-container").data("meal-type", mealType);
	$(".recipes-section a.active").removeClass("active");
	$(".recipes-section a[data-section=public]").addClass("active");
	setChooseMealComponentModalTitle();
	loadRecipes();
}

function setChooseMealComponentModalTitle(){
	var date = $("#meal-components-container").data("date");
	var mealType = $("#meal-components-container").data("meal-type");
	var formattedDate = new Date(date);
	var d = formattedDate.getDate();
	var m =  formattedDate.getMonth();
	var month = getNameOfTheMonth(m);
	m += 1; 
	$("#chooseMealComponentModal .modal-title").text(`Pasirinkti patiekalą ${month}  ${d}  dienai ${translateMealType(mealType.toLowerCase())}:`);
}

function loadRecipes(page, size){
	var recipesContainer = $("#meal-components-list-container");
	if ((typeof page == "undefined") && (typeof page == "undefined")){
		var page = 0;
		var size = 16 
	}
	var section = $("#meal-components-container .recipes-section .active").data("section");
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
	var productsContainer = $("#meal-components-list-container");
	var urlString = "/foodProduct/getAll";	
	$.ajax({
		type: "GET",
		url: urlString,
		success: function (data) {
			productsContainer.empty();
			var html = "";
	        $.each(data, function(i, product) {
	            html += "<div class='col-3 product'><a class='open-add-product-meal-modal' data-dismiss='modal'" +
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



//switching section of recipes when choosing a recipe for a meal
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


$("#plan").on("click", ".open-edit-meal-modal", function(){
	var recipeId = $(this).data('recipe-id');
    var mealId = $(this).data('meal-id');
    $("#mealComponentModal .modal-body").empty();
    var html ="";
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
    	$.get("/plan/getMeal", {mealId}, function(meal){
    		html = "<div class='row'>" +
					"<div class='col-4'>"+
						"<img class='modal-img' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
						"<div class='row'>Kalorijos:<p class='ml-3'>"+meal.calories+" kcal</p></div>"+
						"<div class='row'>Porcijos: "+meal.servings+"</div>"+
						"<div class='row mt-2' id='buttons'></div>"+
					"</div>"+
				"</div>"+
				"<div class='row'>" +
		    		"<div class='col' id='ingredients'>Reikės:</div>"+    
		    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
				"</div>";
		$("#mealComponentModal .modal-body").append(html);       
		$("#mealComponentModal .modal-title").text(recipe.title);    	    	
		$.each(recipe.ingredients, function(i, ingredient) {
			let ammount = (ingredient.ammount / recipe.servings) * meal.servings;
			$("#mealComponentModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name + ": "+ ammount +"</p>");
		 });
		$.each(recipe.preparations, function(i, preparation) {
			$("#mealComponentModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
		 });   	 	
		$("#mealComponentModal .modal-body #buttons").append("<a class='btn btn-danger' href='/plan/removeRecipe?mealId="+mealId+"$recipeId="+recipe.id+"'>Pašalinti iš plano</a>");	

    	})
    		})
});

$(document).on("click", ".open-add-recipe-meal-modal",  function(){
	var recipeId = $(this).data('recipe-id');
    var mealId = $(this).data('meal-id');
    $("#mealComponentModal .modal-body").empty();
    var html ="";
    $.get("/recipe/getRecipe",{recipeId}, function(recipe){
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
        $("#mealComponentModal .modal-body").append(html);     
    	$("#mealComponentModal .modal-title").text(recipe.title);
    	$.each(recipe.ingredients, function(i, ingredient) {
    		$("#mealComponentModal .modal-body #ingredients").append("<p>"+ ingredient.foodProduct.name + ": " +
    				"<span class='ingredient-ammount'>"+ ingredient.ammount +"</span></p>");
         });
    	$.each(recipe.preparations, function(i, preparation) {
    		$("#mealComponentModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
         });   	
    	$("#mealComponentModal .modal-body #buttons").append("<a class='add-meal btn btn-success' data-recipe-id="+recipeId+">Pridėti prie plano</a>");
    	$('#mealComponentModal').modal("show");
	})
});

$(document).on("click", ".open-add-product-meal-modal",  function(){
	var productId = $(this).data('product-id');
    var mealId = $(this).data('meal-id');
    $("#mealComponentModal .modal-body").empty();
    var html ="";
    $.get("/foodProduct/get",{productId}, function(product){
    	console.log(product);
    	html = "<div class='col'>" +
//					"<div class='row'>Kalorijos:<p class='ml-3'><span id='calories'>"+product.nutrition.calories+"</span> kcal</p></div>"+
					"<div class='row'><span class='mx-2'>Kiekis: </span><input type='number' id='food-product-ammount' step='0.25' value='1'> "+product.unitType.label+"</div>"+
					"<div class='row mt-2' id='buttons'></div>"+
				"</div>";
        $("#mealComponentModal .modal-body").append(html);     
    	$("#mealComponentModal .modal-title").text(product.name);
    	$("#mealComponentModal .modal-body #buttons").append("<a class='add-meal btn btn-success' data-product-id="+productId+">Pridėti prie plano</a>");
    	$('#mealComponentModal').modal("show");
	})
});


$(document).on("click", ".add-meal", function() {
	var recipeId = $(this).data("recipe-id");
	var date = $("#meal-components-container").data("date");
	var mealType = $("#meal-components-container").data("meal-type");
	var servings = $("#numberOfServings").val();
	
	$("input[name='recipeId']").attr("value", recipeId);
	$("input[name='date']").attr("value", date);
	$("input[name='mealType']").attr("value", mealType);
	$("input[name='servings']").attr("value", servings);
	
	$("form[name='saveMeal']").submit();
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

$("#mealComponentModal").on("change", "#numberOfServings", function(){
	let newServings = $(this).val();
	let oldServings= $(this).data("servings");
	$(this).data("servings", newServings);
	changeIngredientsAmmounts(oldServings, newServings);
	changeCalories(oldServings, newServings);
})

$('#mealComponentModal').on('hidden.bs.modal', function () {
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

//-----------------------Shopping List--------------------------------------------------

function loadShoppingItems(){
	 $("#shopping-items").empty();
	 let planId = $("input[name=planId]").val()
	 $.get("/plan/getShoppingItems", {planId: planId}, function(shoppingList){
		 $.each(shoppingList, function(i, foodType){	
			 if (foodType.length > 0){
				 let html = "<div>"+i;
				 $.each(foodType, function(i, shoppingItem){
					 html = html + "<div class='shopping-item' data-plan='"+planId+"' data-name='"+shoppingItem.name+"'" +
					 		" data-done='"+shoppingItem.done+"'>";
					 if(!shoppingItem.done)
						html = html + "<img class='icon-sm check-item mr-1 unchecked' src='/images/rectangular.svg'>";
					 if(shoppingItem.done)
							html = html + "<img class='icon-sm check-item mr-1 checked' src='/images/rectangular-checked.svg'>";
					 html = html + shoppingItem.name + ": " + shoppingItem.ammount + " " + shoppingItem.units+"</div>";
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
	$.post("/plan/updateShoppingItem",{planId: planId, ingredientName: name, isDone: done}, function(){
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
				//TODO: add checking if product.ammount > shoppingItem.ammount
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