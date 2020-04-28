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
		 				<c:forEach var="mealDish" items="${meal.mealDishes}">
			 				<c:if test="${mealDish.dish['class'].simpleName == 'Recipe'}" >
					 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" 
					 				data-content="<div><img class='img-thmbnl' src='${mealDish.dish.image}'></div>">
					 		</c:if>
					 		<c:if test="${mealDish.dish['class'].simpleName == 'SingleDishProduct'}">
								<p>
							</c:if>
									<a class="open-edit-meal-modal" data-toggle="modal" href="#mealComponentModal" data-recipe-id="${mealDish.dish.id }" data-meal-id="${meal.id }">${mealDish.dish.title}</a>
								</p>
			 			</c:forEach>
			 		</c:if> 		
		 		</c:forEach>
	 			<a class="add-dish-button" data-toggle="modal" href="#chooseDishModal">
	 				<img class="icon-m" src="/images/plus-sign.svg">
 				</a>
	 		</div>
		</c:forEach>
		<hr/>
	</c:forEach>
</div>