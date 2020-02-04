$(document).ready(function(){
  $('[data-toggle="popover"]').popover();   
});

//$(document).on('click', function (e) {
//    $('[data-toggle="popover"],[data-original-title]').each(function () {
//        //the 'is' for buttons that trigger popups
//        //the 'has' for icons within a button that triggers a popup
//        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {                
//            (($(this).popover('hide').data('bs.popover')||{}).inState||{}).click = false  // fix for BS 3.3.6
//        }
//
//    });
//});

$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

$(document).ready(function() {
  $('li.nav-item.active').removeClass('active');
  $('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
  
  if (location.pathname.startsWith("/recipe/")){
	  $('a[href="/recipe/list"]').closest('li').addClass('active'); 
  }
  
  if (location.pathname.startsWith("/recipe/list")){
	  var selectedTypes = $(".selectedMealType");
	  var checkBoxes = $("input[name='type'");
	  	
	  checkBoxes.each(function( index ) {
		  var checkBox = this;
		  selectedTypes.each(function( index ) {
			  if(checkBox.value == this.value) 
				  checkBox.setAttribute("checked", "checked");
			});
		 
		});	
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
	
	var ammount = document.createElement("div");
	ammount.setAttribute("class", "col-4");
	var aInput = document.createElement("input");
	aInput.setAttribute("id", "ingredients"+id+".ammount");
	aInput.setAttribute("name", "ingredients["+id+"].ammount");
	aInput.setAttribute("type", "text");
	aInput.setAttribute("class", "form-control");
	aInput.setAttribute("required", "true");
	ammount.appendChild(aInput);
	ingredient.appendChild(ammount);
	
	var name = document.createElement("div");
	name.setAttribute("class", "col-4");
	var nInput = document.createElement("input");
	nInput.setAttribute("id", "ingredients"+id+".name");
	nInput.setAttribute("name", "ingredients["+id+"].name");
	nInput.setAttribute("type", "text");
	nInput.setAttribute("class", "form-control");
	nInput.setAttribute("required", "true");
	name.appendChild(nInput);
	ingredient.appendChild(name);
	
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
	console.log(recipeId);
	rInput.setAttribute("value", recipeId);
	ingredient.appendChild(rInput);
	
	var button = document.createElement("a");
	button.setAttribute("class", "remove-ingredient");
	button.setAttribute("type", "button");
	button.innerHTML = "<i class='fas fa-minus-circle fa-2x'>";
	ingredient.appendChild(button);
	
	container.appendChild(ingredient);
});

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
	button.setAttribute("type", "button");
	button.innerHTML = "<i class='fas fa-minus-circle fa-2x'>";
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


$('select').selectpicker();

$(".add-meal-button").click(function(){
	$("td.table-success").removeClass("table-success");
	$(this).parent().addClass("table-success");
	var date = $(this).parent().data("date");
	var mealType = $(this).parent().data("meal-type");
	$("#pagination-nav").removeClass("d-none");
	$("#meal-recipes-container").data("date", date);
	$("#meal-recipes-container").data("meal-type", mealType);
	$("#shopping-list-container").addClass("d-none");
	loadRecipes();
});

function loadRecipes(page, size){
	var container = $("#meal-recipes-container");
	var date = container.data("date");
	var mealType = container.data("meal-type");
	console.log(date);
	console.log(mealType);
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
			showPageNumbers(data.totalPages, page);
			container.empty();
			container.parent().attr("class", "col-6");
			var html = "<h2>Pasirinkti patiekalą " +month + " " + d +" dienai (" +translateMealType(mealType.toLowerCase())+"):</h2>" +
					"<div class='row'>";
	        $.each(data.content, function(i, recipe) {
	        	if ((i != 0) && (i % 4 == 0)){
	        		html += "</div><div class='row'>";
	        	}
	            if (recipe.image == null)
	            	recipe.image = "/recipeImages/default.png";
	            html += "<div class='col-3 choose-meal' data-recipe-id="+recipe.id+">"+
			             		"<img class='img-thumbnail' src='"+recipe.image+"'>" +
			             		"<span>"+recipe.title+"</span>" +
		             		"</div>";
	             
	         });
	        html +="</div>";
	        container.append(html);
	        },
	    erroe: function(){
	    	console.log("Nepavyko įkelti receptų");
	    }
	})
}

$( "#meal-recipes-container" ).on("click", ".choose-meal", function() {
	var recipeId = $(this).data("recipe-id");
	var date = $("#meal-recipes-container").data("date");
	var mealType = $("#meal-recipes-container").data("meal-type");
	
	$("input[name='recipeId']").attr("value", recipeId);
	$("input[name='date']").attr("value", date);
	$("input[name='mealType']").attr("value", mealType);
	
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

$("#shopping-not-done").on("click", ".check-item", function(){
	var item = $(this).parent("div");
	var name = item.data("name");
	$.post("plan/updateShoppingItem",{name: name}, function(){
		item.children("i").removeClass("fa-square check-item").addClass("fa-check-square uncheck-item");
		item.remove();
		$("#shopping-done").append(item);
	})
})

$("#shopping-done").on("click", ".uncheck-item", function(){
	var item = $(this).parent("div");
	var name = item.data("name");
	$.post("plan/updateShoppingItem",{name: name}, function(){
		item.children("i").removeClass("fa-check-square uncheck-item").addClass("fa-square check-item");
		item.remove();
		$("#shopping-not-done").append(item);
	})
})

function printPlan()
{       
        //hide unnecessary stuff
        $("#plan-side-container").hide();
        $(".remove-recipe").hide();
        $(".link-to-settings").hide();
        $(".btn").hide();
        //Print Page
        window.print();
        //Restore orignal HTML
        location.reload();
}

function printShoppingList()
{       
        //hide unnecessary stuff
        $("#plan").hide();
        $(".remove-recipe").hide();
        $(".link-to-settings").hide();
        $(".btn").hide();
        
        $("#plan-side-container").removeClass("col-2");
        $("#plan-side-container").css("position", "absolute");
        
        //Print Page
        window.print();
        //Restore orignal HTML
        location.reload();
}