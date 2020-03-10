$(".add-meal-button").click(showRecipesContainer);

function showRecipesContainer(){
	var date = $(this).parent().data("date");
	var mealType = $(this).parent().data("meal-type");
	$("#meal-recipes-container").data("date", date);
	$("#meal-recipes-container").data("meal-type", mealType);
	loadRecipes();
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
	
	if ((typeof page == "undefined") && (typeof page == "undefined")){
		var page = 0;
		var size = 16 
	}
	
	
	$("#chooseRecipeModal .modal-title").text(`Pasirinkti patiekalą ${month}  ${d}  dienai ${translateMealType(mealType.toLowerCase())}:`);

	var urlString = "/recipe/simple/getRecipes?pageId="+page+"&pageSize="+size;
	
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
	            html += "<div class='col-3'><a class='open-add-meal-modal' data-dismiss='modal' data-toggle='modal' href='#addMealModal' data-recipe-id="+recipe.id+" data-meal-id='0'>"+
			             		"<img class='img-thmbnl' src='"+recipe.image+"'>" +
			             		"<span>"+recipe.title+"</span>" +
		             		"</a></div>";            
	         });
	        html +="</div>";
	        
	        recipesContainer.append(html);
	        },
	    error: function(){
	    	var html = "Nepavyko įkelti receptų";
	    	recipesContainer.append(html);
	    }
	})
}

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

$(document).on("click", ".open-add-meal-modal",  function(){
	var recipeId = $(this).data('recipe-id');
    var mealId = $(this).data('meal-id');
    console.log(recipeId + " " + mealId);
    $("#addMealModal .modal-body").empty();
    var html ="";
    $.get("/recipe/simple/getRecipe",{recipeId}, function(recipe){
    	html = "<div class='row'>" +
    				"<div class='col-4'>"+
						"<img class='img-modal' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
    					"<div class='row'>Porcijos:<input type='number' id='numberOfServings' min='1' max='100' value='1'></div>"+
    					"<div class='row'><input class='mr-1 mt-2' type='checkbox' id='addIngredients' checked> Įtraukti ingredientus į pirkinių sąrašą </div>"+
    					"<div class='row mt-2' id='buttons'></div>"+
					"</div>"+
				"</div>"+
	    		"<div class='row'>" +
		    		"<div class='col' id='ingredients'>Reikės:</div>"+    
		    		"<div class='col' id='preparations'>Paruošimo būdas:</div>"+  
	    		"</div>";
        $("#addMealModal .modal-body").append(html);     
    	$("#addMealModal .modal-title").text(recipe.title);   	    	
    	$.each(recipe.ingredients, function(i, ingredient) {
    		$("#addMealModal .modal-body #ingredients").append("<p>"+ ingredient.name + ": "+ ingredient.ammount +"</p>");
         });
    	$.each(recipe.preparations, function(i, preparation) {
    		$("#addMealModal .modal-body #preparations").append("<p>" + (i+1)  +". "+ preparation.description +"</p>");
         });   	
    	$("#addMealModal .modal-body #buttons").append("<a class='add-meal btn btn-success' data-recipe-id="+recipeId+">Pridėti prie plano</a>");
    	
	})
});

$("#addMealModal").on("change", "#addIngredients", function(){
    $('#addIngredients').val($(this).is(':checked'));   
});

$('#addMealModal').on('hidden.bs.modal', function () {
	$('#chooseRecipeModal').modal("show");
});

$(document).on("click", ".add-meal", function() {
	var recipeId = $(this).data("recipe-id");
	var date = $("#meal-recipes-container").data("date");
	var mealType = $("#meal-recipes-container").data("meal-type");
	var servings = $("#numberOfServings").val();
	var addIngredients = $("#addIngredients").val();
	
	$("input[name='recipeId']").attr("value", recipeId);
	$("input[name='date']").attr("value", date);
	$("input[name='mealType']").attr("value", mealType);
	$("input[name='servings']").attr("value", servings);
	$("input[name='addIngredients']").attr("value", addIngredients);
	
	$("form[name='saveMeal']").submit();
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

$("#plan").on("click", ".open-edit-meal-modal", function(){
	var recipeId = $(this).data('recipe-id');
    var mealId = $(this).data('meal-id');
    $("#editMealModal .modal-body").empty();
    var html ="";
    $.get("/recipe/simple/getRecipe",{recipeId}, function(recipe){
    	html = "<div class='row'>" +
    				"<div class='col-4'>"+
						"<img class='img-modal' src='"+recipe.image+"'>"+
					"</div>"+
					"<div class='col-8'>"+
						"<div class='row'>Aprašymas:<p class='ml-3'>"+recipe.description+"</p></div>"+
						"<div class='row'>Porcijos: "+recipe.servings+"</div>"+
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
    	$("#editMealModal .modal-body #buttons").append("<a class='btn btn-danger' href='plan/deleteMeal?mealId="+mealId+"'>Pašalinti iš plano</a>");	
	})
});