$(document).ready(function() {
	loadShoppingItems();
});

$(".add-meal-button").click(showRecipesContainer);

$( "#plan-side-container" ).on("click", "#hide-meal-recipes-container-icon", hideRecipesContainer);

function showRecipesContainer(){
	window.scrollTo(0, 0);
	$("td.table-success").removeClass("table-success");
	$(this).parent("td").addClass("table-success");
	var date = $(this).parent().data("date");
	var mealType = $(this).parent().data("meal-type");
	$("#pagination-nav").removeClass("d-none");
	$("#meal-recipes-container" ).removeClass("d-none");
	$("#meal-recipes-container").data("date", date);
	$("#meal-recipes-container").data("meal-type", mealType);
	$("#shopping-list-container").addClass("d-none");
	$("#plan-side-container").attr("class", "col-6");
	$("#hide-meal-recipes-container-icon").removeClass("d-none");
	loadRecipes();
}

function hideRecipesContainer() {
	$("td.table-success").removeClass("table-success");
	$("#plan-side-container").attr("class", "col-3");
	$("#meal-recipes-container" ).addClass("d-none");
	$("#pagination-nav").addClass("d-none");
	$("#shopping-list-container").removeClass("d-none");
	$("#hide-meal-recipes-container-icon").addClass("d-none");
}

function loadRecipes(page, size){
	var recipesContainer = $("#recipes-list-container");
	var date = $("#meal-recipes-container").data("date");
	var mealType = $("#meal-recipes-container").data("meal-type");
	var formattedDate = new Date(date);
	var d = formattedDate.getDate();
	var m =  formattedDate.getMonth();
	var month = getNameOfTheMonth(m);
	m += 1; 
	var section = $("#meal-recipes-container .recipes-section .active").data("section");
	
	if ((typeof page == "undefined") && (typeof page == "undefined")){
		var page = 0;
		var size = 16 
	}
	
	
	$("#meal-recipes-container h2").text(`Pasirinkti patiekalą ${month}  ${d}  dienai ${translateMealType(mealType.toLowerCase())}:`);

	var urlString = "recipe/getRecipes?section="+section+"&pageId="+page+"&pageSize="+size;
	
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
	            html += "<div class='col-3'><a class='open-edit-meal-modal' data-toggle='modal' href='#editMealModal' data-recipe-id="+recipe.id+" data-meal-id='0'>"+
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

$( "#editMealModal" ).on("click", ".add-meal", function() {
	var recipeId = $(this).data("recipe-id");
	var date = $("#meal-recipes-container").data("date");
	var mealType = $("#meal-recipes-container").data("meal-type");
	var servings = $("#numberOfServings").val();
	
	$("input[name='recipeId']").attr("value", recipeId);
	$("input[name='date']").attr("value", date);
	$("input[name='mealType']").attr("value", mealType);
	$("input[name='servings']").attr("value", servings);
	
	$("form[name='saveMeal']").submit();
});

//meal plan chosen public or private section 
$(".recipes-section a").click(function(){
	$(".recipes-section a.active").removeClass("active");
	$(this).addClass("active");
	let page = $("#pagination-nav .page-link .active").text();
	loadRecipes(page, 16);
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


$(document).on("click", ".open-edit-meal-modal", function () {
    var recipeId = $(this).data('recipe-id');
    var mealId =$(this).data('meal-id');
    $("#editMealModal .modal-body").empty();
    var html ="";
    $.get("recipe/getRecipe",{recipeId}, function(recipe){
    	html = "<div class='row'>" +
    				"<div class='col-4'>"+
						"<img class='img-modal' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
						"<div class='row'>Porcijos:<input type='number' id='numberOfServings' min='1' max='100' value='1'></div>"+
						"<div class='row mt-2' id='buttons'></div>"+
					"</div>"+
				"</div>"+
	    		"<div class='row'>" +
		    		"<div class='col' id='ingredients'>Reikės:</div>"+    
		    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
	    		"</div>";
        $("#editMealModal .modal-body").append(html);
        
    	$("#editMealModal .modal-title").text(recipe.title);
    	    	
    	$.each(recipe.ingredients, function(i, ingredient) {
    		$("#editMealModal .modal-body #ingredients").append("<p>"+ ingredient.name + ": "+ ingredient.ammount +"</p>");
         });
    	$.each(recipe.preparations, function(i, preparation) {
    		$("#editMealModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
         });   	
    	
    	if (mealId != 0) {
    		// var servings = $("#numberOfServings").val();
        	$("#editMealModal .modal-body #buttons").append("<a class='btn btn-danger' href='plan/deleteMeal?mealId="+mealId+"'>Pašalinti iš plano</a>");
        	// $("#editMealModal .modal-body #buttons").append("<a class='btn
			// btn-success'
			// href='plan/updateMeal?mealId="+mealId+"&servings="+servings+">Išsaugoti</a>");
        	$("#numberOfServings").prop( "disabled", true );
    	} else {
        	$("#editMealModal .modal-body #buttons").append("<a class='add-meal btn btn-success' data-recipe-id="+recipeId+">Pridėti prie plano</a>");
    	}
    	
	})
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
	 $.get("plan/getShoppingItems", function(shoppingItemsDTO){
		 shoppingList = removeDuplicateShoppingItems(shoppingItemsDTO);
		 shoppingList.sort(compareFoodType);
		 var grouped = shoppingList.reduce(function (g, a) {
		        g[a.foodType.label] = g[a.foodType.label] || [];
		        g[a.foodType.label].push(a);
		        return g;
		    }, Object.create(null));
		 
		 $.each(grouped, function(i, foodType){
			 
			 var html = "<div>"+i;
			 $.each(foodType, function(i, shoppingItemDTO){
				 var shoppingItem = shoppingItemDTO.shoppingItem;
				 html = html + "<div data-id="+shoppingItem.id+" data-ids="+shoppingItem.ids+">";
				 if(!shoppingItem.done)
					html = html + "<img class='icon-sm check-item mr-1 unchecked' src='/images/rectangular.svg'>";
				 if(shoppingItem.done)
						html = html + "<img class='icon-sm check-item mr-1 checked' src='/images/rectangular-checked.svg'>";
				 
				 html = html + shoppingItem.name + ": " + shoppingItem.ammount + " " + shoppingItem.unit.label+"</div>";
			 })
			

			 html = html + "<hr/></div>";
			 $("#shopping-items").append(html);
		 });
	 });
}
function removeDuplicateShoppingItems(shoppingList){
	
	$.each(shoppingList, function(i, shoppingItem){
		shoppingItem.shoppingItem.ids = [shoppingItem.shoppingItem.id];
	});
	for(i = shoppingList.length-1; i >= 0; i--) {
		if (i != 0) {
			var item1 = shoppingList[i].shoppingItem;
			var item2 = shoppingList[i-1].shoppingItem;
			 if (item1.name == item2.name) {
				if ((item1.unit.name == item2.unit.name) && (item1.done == item2.done)) {
					item2.ammount = parseFloat(item1.ammount) + parseFloat(item2.ammount);
					item2.ammount = Math.round( item2.ammount * 10) / 10;
					$.each(item1.ids, function(i, id){
						item2.ids.push(id);
					});
					shoppingList.splice(i, 1);					
				}
			}	
		}
	}
	return shoppingList;
}
function printShoppingList()
{       
        // hide unnecessary stuff
        $("#plan").hide();
        $(".remove-recipe").hide();
        $(".link-to-settings").hide();
        $(".btn").hide();
        
        $("#plan-side-container").removeClass("col-2");
        $("#plan-side-container").css("position", "absolute");
        
        // Print Page
        window.print();
        // Restore orignal HTML
        location.reload();
}

function compareFoodType( a, b ) {
	  if ( a.foodType.position < b.foodType.position ){
	    return -1;
	  }
	  if ( a.foodType.position > b.foodType.position ){
	    return 1;
	  }
	  return 0;
}


$("#shopping-list-container").on("click", ".check-item", function(){
	var item = $(this);
	var ids = $(this).parent("div").data("ids");
	$.post("plan/updateShoppingItem",{ids: ids}, function(){
		item.toggleClass("checked");
		item.toggleClass("unchecked");
		if (item.hasClass("checked")){
			item.attr('src','/images/rectangular-checked.svg');
		}else{
			item.attr('src','/images/rectangular.svg');
		}
		console.log(item);
	})
})