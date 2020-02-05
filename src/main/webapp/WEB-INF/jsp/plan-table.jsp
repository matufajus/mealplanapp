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
		<c:forEach var="date" items="${dates}" varStatus="status">
			 <tr class="d-flex">
			 	<td class="w-20 text-center"><h3 class="py-0">
			 		<fmt:parseDate  value="${date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
					<fmt:formatDate value="${parsedDate}" type="date" pattern="MM.dd E" var="stdDatum" />
					${stdDatum}
			 	</h3></td>
			 	<c:forEach var="mealType" items="${mealTypes}">
			 		<c:set var="hasMeal" value="false"/>
					<c:forEach var="meal" items="${meals}">	
				 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
				 			<c:set var="hasMeal" value="true"/>
				 			<td class="w-20 text-center" data-meal-type="${mealType}" data-date="${date}">
<%-- 				 				<a class="remove-recipe" href="plan/deleteMeal?mealId=${meal.id}" style="font-size:100%;">&#10006;</a> --%>
					 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right" data-html="true" 
					 				data-content="<div class='recipe-thmbnl'><img src='${meal.recipe.image}'></div>">
									${meal.recipe.title}
								</p>
<!-- 									 			<div> -->
<%-- 									 				<img onerror="this.onerror=null;this.src='/recipeImages/default.png';" src="${meal.recipe.image}" style="object-fit:cover; width:100px;"> --%>
<!-- 									 			</div> -->
				 			</td>
				 		</c:if> 		
			 		</c:forEach>
			 		<c:if test="${hasMeal == false}">
			 			<td class="w-20 text-center" data-meal-type="${mealType}" data-date="${date}"><a class="add-meal-button"><i class="fas fa-plus-circle fa-2x py-0"></i></a></td>
			 		</c:if>
				</c:forEach>
		    </tr>
		</c:forEach>
	</tbody>
</table>