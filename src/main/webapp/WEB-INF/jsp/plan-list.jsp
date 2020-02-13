<div id="plan" class="m-auto list" style="text-align:center;">
	<c:forEach var="date" items="${dates}" varStatus="status">
		<br>
		<fmt:parseDate  value="${date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
		<fmt:formatDate value="${parsedDate}" type="date" pattern="EEEE (MM.dd)" var="stdDatum" />
		<h2>${stdDatum}</h2>
		<c:forEach var="mealType" items="${mealTypes}">
			<h4>${mealType.label}</h4>

	 		<c:set var="hasMeal" value="false"/>
			<c:forEach var="meal" items="${meals}">	
		 		<c:if test="${(meal.date == date) && (meal.mealType == mealType)}">
		 			<c:set var="hasMeal" value="true"/>
		 			<div data-meal-type="${mealType}" data-date="${date}">
<%-- 		 				<a class="remove-recipe" href="plan/deleteMeal?mealId=${meal.id}">&#10006;</a> --%>
			 			<p data-container="body" data-toggle="popover" data-trigger="hover" data-placement="top" data-html="true" 
			 				data-content="<div><img class='img-thmbnl' src='${meal.recipe.image}'></div>">
								<a class="open-edit-meal-modal" data-toggle="modal" href="#editMealModal" data-recipe-id="${meal.recipe.id }" data-meal-id="${meal.id }">${meal.recipe.title}</a>
						</p>
	<!-- 									 			<div> -->
	<%-- 									 				<img onerror="this.onerror=null;this.src='/recipeImages/default.png';" src="${meal.recipe.image}" style="object-fit:cover; width:100px;"> --%>
	<!-- 									 			</div> -->
		 			</div>
		 		</c:if> 		
	 		</c:forEach>
	 		<c:if test="${hasMeal == false}">
	 			<div data-meal-type="${mealType}" data-date="${date}"><a class="add-meal-button"><i class="fas fa-plus-circle fa-2x py-0"></i></a></div>
	 		</c:if>

		</c:forEach>
		<hr/>
	</c:forEach>
</div>