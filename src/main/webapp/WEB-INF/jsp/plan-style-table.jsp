<table id="plan" class="table table-bordered table-striped">
	<thead class="thead-light">
		<tr class="d-flex">
			<th scope="col" class="w-20"></th>
			<c:forEach var="mealType" items="${mealTypes}">
				<th scope="col" class="w-20 text-center">
					${mealType.label}
				</th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="date" items="${plan.dates}" varStatus="status">
			 <tr class="d-flex">
			 	<td class="w-20 text-center"><h3 class="py-0">
			 		<fmt:parseDate  value="${date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
					<fmt:formatDate value="${parsedDate}" type="date" pattern="MM.dd E" var="stdDatum" />
					${stdDatum}
			 	</h3></td>
			 	<c:forEach var="mealType" items="${mealTypes}">
			 		<td class="w-20 text-center" data-test="${meal }" data-meal-type="${mealType}" data-date="${date}">
						<c:forEach var="meal" items="${plan.meals}">	
					 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
					 				<c:forEach var="recipe" items="${meal.recipes}">
							 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right" data-html="true" 
							 				data-content="<div><img class='img-thmbnl' src='${recipe.image}'></div>">
											<a class="open-edit-meal-modal" data-toggle="modal" href="#editMealModal" data-recipe-id="${recipe.id }" data-meal-id="${meal.id }">${recipe.title}</a>
										</p>
									</c:forEach>					 			
					 			
					 		</c:if> 		
				 		</c:forEach>
			 			<a class="add-meal-button" data-toggle="modal" href="#chooseRecipeModal">
			 				<img class="icon-m" src="/images/plus-sign.svg">
			 			</a>
			 		</td>
			 		
				</c:forEach>
		    </tr>
		</c:forEach>
	</tbody>
</table>