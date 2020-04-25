<div id="plan" class="m-auto list" style="text-align:center;">
	<c:forEach var="date" items="${plan.dates}" varStatus="status">
		<br>
		<fmt:parseDate  value="${date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
		<fmt:formatDate value="${parsedDate}" type="date" pattern="EEEE (MM.dd)" var="stdDatum" />
		<h2>${stdDatum}</h2>
		<c:forEach var="mealType" items="${mealTypes}">
			<h4>${mealType.label}</h4>
			<div data-meal-type="${mealType}" data-date="${date}">
				<c:forEach var="meal" items="${plan.meals}">	
			 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">	
		 				<c:forEach var="recipe" items="${meal.recipes}">
				 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" 
				 				data-content="<div><img class='img-thmbnl' src='${recipe.image}'></div>">
									<a class="open-edit-meal-modal" data-toggle="modal" href="#editMealModal" data-recipe-id="${recipe.id }" data-meal-id="${meal.id }">${recipe.title}</a>
							</p>
			 			</c:forEach>
			 		</c:if> 		
		 		</c:forEach>
	 			<a class="add-meal-button" data-toggle="modal" href="#chooseRecipeModal">
	 				<img class="icon-m" src="/images/plus-sign.svg">
 				</a>
	 		</div>
		</c:forEach>
		<hr/>
	</c:forEach>
</div>