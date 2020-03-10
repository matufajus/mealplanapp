$(document).ready(function() {
	loadShoppingItems();
});

function loadShoppingItems(){
	$("#shopping-items").empty();
	let url;
	if (location.pathname.startsWith("/plan/simple")){
		url = "/plan/simple/getShoppingItems";
	}else{
		url = "/plan/getShoppingItems";
	}
	 $.get(url, function(shoppingItemsDTO){
		 shoppingList = removeDuplicateShoppingItems(shoppingItemsDTO);
		 if ($('#checkKitchenProducts').is(":checked")){			 
			 checkKitchenProducts(shoppingList)
			  .then(response => {
			    appendShoppingList(shoppingList);
			  })
			  .catch(error => {
			    console.log(error)
			  })
		 } else{
			 appendShoppingList(shoppingList);
		 }
	 });
}

function appendShoppingList(shoppingList){
	 shoppingList.sort(compareFoodType);
	 let grouped = shoppingList.reduce(function (g, a) {
	        g[a.foodType.label] = g[a.foodType.label] || [];
	        g[a.foodType.label].push(a);
	        return g;
	    }, Object.create(null));
	 $.each(grouped, function(i, foodType){			 
		 let html = "<div>"+i;
		 $.each(foodType, function(i, shoppingItemDTO){
			 let shoppingItem = shoppingItemDTO.shoppingItem;
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
	if ($("#shopping-list-container #shopping-items").height() < 300){
		$("#read-more-shopping-list").hide();
	}
	showOrHideDoneItems();	 
}

function removeDuplicateShoppingItems(shoppingList){	
	$.each(shoppingList, function(i, shoppingItem){
		shoppingItem.shoppingItem.ids = [shoppingItem.shoppingItem.id];
	});
	for(i = shoppingList.length-1; i >= 0; i--) {
		if (i != 0) {
			let item1 = shoppingList[i].shoppingItem;
			let item2 = shoppingList[i-1].shoppingItem;
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

function checkKitchenProducts(shoppingList){
	return new Promise((resolve, reject) => {
		$.get("/kitchen/getUserProducts", function(products){
			$.each(products, function(i, product){
				$.each(shoppingList, function(j, item){
					//TODO: add checking if product.quantity > shoppingItem.quantity
					if (product.name == item.shoppingItem.name){
						item.shoppingItem.done = true;
					}
				});
			});
			resolve("success");
		}).fail(function(){
			reject("Couldn't get kitchen products");
		});
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
	var name = $(this).parent("div").data("name");
	if (location.pathname.startsWith("/plan/simple")){
		$.post("/plan/simple/updateShoppingItem",{ids: ids}, function(){
			updateShoppingItem(item);
		})
	}else{
		$.post("/plan/updateShoppingItem",{ids: ids}, function(){
			updateShoppingItem(item);
		})

	}
});

function updateShoppingItem(item){
	item.toggleClass("checked");
	item.toggleClass("unchecked");
	if (item.hasClass("checked")){
		item.attr('src','/images/rectangular-checked.svg');
	}else{
		item.attr('src','/images/rectangular.svg');
	}
	showOrHideDoneItems();
}

$('#checkKitchenProducts').change(function(){
	loadShoppingItems();
});

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