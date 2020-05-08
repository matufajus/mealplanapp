
$(document).ready(function() {
	
	//enable popovers
	$('[data-toggle="popover"]').popover();  
	
	//enable tooltips
	$('[data-toggle="tooltip"]').tooltip()
	 
	// for main navigation bar
	$('li.nav-item.active').removeClass('active');
	$('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
	if (location.pathname.startsWith("/recipe/")){
		$('a[href="/recipe/list"]').closest('li').addClass('active'); 
	}
	  
	// for button group in recipes list
	if (location.pathname.startsWith("/recipe/")){
		let type = location.pathname.substring(8);
		$('.btn-group a[href="' + type + '"]').addClass('active');
	}
	
	//enable autocomplete from database table food_product
	enableFoodProductAutocomplete();
	
	//set csrf for spring security ajax request
	$(function () {
		  var token = $("meta[name='_csrf']").attr("content");
		  var header = $("meta[name='_csrf_header']").attr("content");
		  $(document).ajaxSend(function(e, xhr, options) {
		    xhr.setRequestHeader(header, token);
		  });
		});
	
	
	

});






function enableFoodProductAutocomplete(){
	$('.food-product-name').autocomplete({
		source : '/recipe/searchProducts'
	});
}



$("#tags").tagsinput({
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
											"<a class='recipe-modal-link' data-toggle='modal' href='#recipeModal' data-recipe-id='"+recipe.id+"'>"+
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


$("#scroll-down-link").click(function(){
	var elem = document.getElementById("reasonsToPlan");
	window.scroll(0, elem.offsetTop - 56);
});

$("#recipes-side-nav .mealType-checkbox").change(function(){
	$("#recipe-side-nav-form").submit();
})

$("#recipes-side-nav .food-product").click(function(){
	$(this).toggleClass("active");
	let tag = $(this).data("food");
	if ($(this).hasClass("active")){
		$('#tags').tagsinput('add', tag);
	}else{
		$('#tags').tagsinput('remove', tag);
	}
})

$('#tags').on('itemAdded itemRemoved', function(event) {
	$("#recipe-side-nav-form").submit();
})



Modernizr.on('webp', function (result) {
    if (result) {
    	$("#login-background").addClass("webp");
    	$("#landing-cover").addClass("webp");
    	$("#kitchen-products").addClass("webp");
    } else {
    	$("#login-background").addClass("no-webp");
    	$("#landing-cover").addClass("no-webp");
    	$("#kitchen-products").addClass("no-webp");
    }
});
