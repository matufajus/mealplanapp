$(document).ready(function(){
  $('[data-toggle="popover"]').popover();   
});

// $(document).on('click', function (e) {
// $('[data-toggle="popover"],[data-original-title]').each(function () {
// //the 'is' for buttons that trigger popups
// //the 'has' for icons within a button that triggers a popup
// if (!$(this).is(e.target) && $(this).has(e.target).length === 0 &&
// $('.popover').has(e.target).length === 0) {
// (($(this).popover('hide').data('bs.popover')||{}).inState||{}).click = false
// // fix for BS 3.3.6
// }
//
// });
// });

$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

$(document).ready(function() {
	// for main navigation bar
  $('li.nav-item.active').removeClass('active');
  $('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
  if (location.pathname.startsWith("/recipe/")){
	  $('a[href="/recipe/list"]').closest('li').addClass('active'); 
  }
  
  // for button group in recipes list
  if (location.pathname.startsWith("/recipe/")){
	  var type = location.pathname.substring(8);
	  $('.btn-group a[href="' + type + '"]').addClass('active');
  }

});



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
	name.appendChild(nInput);
	ingredient.appendChild(name);
	
	var ammount = document.createElement("div");
	ammount.setAttribute("class", "col-2");
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
	unit.setAttribute("class", "col-3");
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
	
	var rInput = document.createElement("input");
	rInput.setAttribute("id", "ingredients"+id+".recipe");
	rInput.setAttribute("name", "ingredients["+id+"].recipe");
	rInput.setAttribute("type", "hidden");
	var recipeId = document.getElementById("id").value;
	rInput.setAttribute("value", recipeId);
	ingredient.appendChild(rInput);
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-ingredient col-1");
	button.innerHTML = "<img class='icon-m mr-2' src='/images/minus-black.svg'>";
	ingredient.appendChild(button);
	
	container.appendChild(ingredient);
	
	enableFoodProductAutocomplete();
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
	preparation.appendChild(description);
	
	var iInput = document.createElement("input");
	iInput.setAttribute("id", "preparations"+id+".id");
	iInput.setAttribute("name", "preparations["+id+"].id");
	iInput.setAttribute("type", "hidden");
	iInput.setAttribute("value", 0);
	preparation.appendChild(iInput);
	
	var rInput = document.createElement("input");
	rInput.setAttribute("id", "preparations"+id+".recipe");
	rInput.setAttribute("name", "preparations["+id+"].recipe");
	rInput.setAttribute("type", "hidden");
	var recipeId = document.getElementById("id").value;
	console.log(recipeId);
	rInput.setAttribute("value", recipeId);
	preparation.appendChild(rInput);
	
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


$('textarea').each(function () {
	  this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
	}).on('input', function () {
	  this.style.height = 'auto';
	  this.style.height = (this.scrollHeight) + 'px';
	});


$('.selectpicker').selectpicker();

$(".add-meal-button").click(function(){
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
});

$( "#plan-side-container" ).on("click", "#hide-meal-recipes-container-icon", function() {
	$("td.table-success").removeClass("table-success");
	$("#plan-side-container").attr("class", "col-3");
	$("#meal-recipes-container" ).addClass("d-none");
	$("#pagination-nav").addClass("d-none");
	$("#shopping-list-container").removeClass("d-none");
	$("#hide-meal-recipes-container-icon").addClass("d-none");
});

function loadRecipes(page, size){
	var container = $("#meal-recipes-container");
	var date = container.data("date");
	var mealType = container.data("meal-type");
	var formattedDate = new Date(date);
	var d = formattedDate.getDate();
	var m =  formattedDate.getMonth();
	var month = getNameOfTheMonth(m);
	m += 1; 
	var y = formattedDate.getFullYear();
	if ((typeof page == "undefined") && (typeof page == "undefined")){
		var page = 0;
		var size = 16 
	}
	var urlString = "recipe/getRecipes?pageId="+page+"&pageSize="+size;
	
	$.ajax({
		type: "GET",
		url: urlString,
		success: function (data) {
			container.empty();
			showPageNumbers(data.totalPages, page);
			var html = "<h2>Pasirinkti patiekalą " +month + " " + d +" dienai (" +translateMealType(mealType.toLowerCase())+"):</h2>" +
					"<div class='row'>";
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
	        container.append(html);
	        },
	    erroe: function(){
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
	var size = 16;
	loadRecipes(page, size);
})

$("#shopping-list-container").on("click", ".check-item", function(){
	var item = $(this).parent("div");
	var ids = item.data("ids");
	$.post("plan/updateShoppingItem",{ids: ids}, function(){
// item.children("i").toggleClass("fa-square fa-check-square");
// item.remove();
// if (item.children("i").hasClass("fa-square")){
// $("#shopping-not-done").append(item);
// }else{
// $("#shopping-done").append(item);
// }
		loadShoppingItems();
	})
})

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

$(document).ready(function() {
	  if (location.pathname.startsWith("/plan")){
		   loadShoppingItems();
	  }
	  
	  enableFoodProductAutocomplete();

});

function loadShoppingItems(){
	$("#shopping-items").empty();
	 $.get("plan/getShoppingItems", function(shoppingItemsDTO){
		 console.log(shoppingItemsDTO);
//		 var shoppingList = shoppingItemsDTO.map(a => a.shoppingItem);
		 shoppingList = removeDuplicateShoppingItems(shoppingItemsDTO);
		 console.log(shoppingList);
		 var grouped = shoppingList.reduce(function (g, a) {
		        g[a.foodType.label] = g[a.foodType.label] || [];
		        g[a.foodType.label].push(a);
		        return g;
		    }, Object.create(null));


		 console.log(grouped);
		 $.each(grouped, function(i, foodType){
			 
			 var html = "<div>"+i;
			 $.each(foodType, function(i, shoppingItemDTO){
				 var shoppingItem = shoppingItemDTO.shoppingItem;
				 html = html + "<div data-id="+shoppingItem.id+" data-ids="+shoppingItem.ids+">";
				 if(!shoppingItem.done)
					html = html + "<img class='icon-sm check-item mr-1' src='/images/rectangular.svg'>";
				 if(shoppingItem.done)
						html = html + "<img class='icon-sm check-item mr-1' src='/images/rectangular-checked.svg'>";
				 
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



function enableFoodProductAutocomplete(){
	$('.food-product-name').autocomplete({
		source : '/recipe/searchProducts',
		select: function( event, ui ) {
	    	$.get("/recipe/getFoodProduct", {name: ui.item['value']}, function(foodProduct){
	    		var unit = $(event.target).parents().siblings().children(".food-product-unit");
	    		unit.val(foodProduct.unitType.name);
	    	});
	    	
	    }
	});
}



$("#tags").tagsinput({
	
// typeaheadjs: {
// name: "product",
// source: '/recipe/searchProducts'
// }
	 typeahead: {
		 source: function(query) {
		      return $.get('/recipe/searchProducts?term='+query);
		    },
		afterSelect: function() {
		    this.$element[0].value = '';
		  }
		  },
	  freeInput: true
	  
	    
});

$("#recipe-side-nav-form").submit(function(event){
	
	event.preventDefault(); // avoid to execute the actual submit of the form.
	
	var tagsInput = $("#recipe-side-nav-form input[name='products']");
	if (tagsInput.val() == "")
		$("#recipe-side-nav-form input[name='products']").attr("disabled", true);
	
	var form = $(this);
    var url = form.attr('action');
    
    var section = location.pathname.substring(8);
    
    $("input[name='section']").attr("value", section);

    $.ajax({
           type: "GET",
           url: url,
           data: form.serialize(), // serializes the form's elements.
           success: function(recipes)
           {
        	   var container = $("#recipes-list-container");
        	   container.empty();
        	   if (recipes.length == 0){
        		   container.append("<h2 class='m-3'>Deja, nepavyko rasti receptų.</h2>");
        	   }else{
        		   $.each(recipes, function(i, recipe){
        			   container.append("<div class = 'recipe-thmbnl'>"+
											"<a href='info?recipeId="+recipe.id+"'>"+
												"<img class='zoom' src='"+recipe.image+"' >"+	
										   		"<h2>"+recipe.title+"</h2>"+
									  		 "</a>"+
							  		 	"</div>");
                   })
        	   }
               
           }
         });
    $("#recipe-side-nav-form input[name='products']").attr("disabled", false);
	
});

$("#editIngredientLink").click(function(){
	var id = $(this).data("id");
	var name = $(this).data("name");
	$("form[action='editIngredient'] input[name='id']").val(id);
	$("form[action='editIngredient'] input[name='name']").val(name);
});

$("#addFoodProductLink").click(function(){
	var name = $(this).data("name");
	$("form[action='addFoodProduct'] input[name='name']").val(name);
});



