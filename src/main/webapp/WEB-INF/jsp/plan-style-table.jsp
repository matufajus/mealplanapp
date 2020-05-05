<table id="plan" class="table table-bordered table-striped">
	<thead class="thead-light">
		<tr class="d-flex">
			<th scope="col" class="w-20"></th>
			<c:forEach var="mealType" items="${mealTypes}">
				<th scope="col" class="w-20 text-center">${mealType.label}</th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="date" items="${plan.dates}" varStatus="status">
			<tr class="d-flex">
				<td class="w-20 text-center"><h3 class="py-0">
						<fmt:parseDate value="${date}" type="date" pattern="yyyy-MM-dd"
							var="parsedDate" />
						<fmt:formatDate value="${parsedDate}" type="date"
							pattern="MM.dd E" var="stdDatum" />
						${stdDatum}
						<div id="nutritionInfo">
							Kalorijos:<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${dailyNutrition.get(date).getKcal()}" />
							Baltymai:<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${dailyNutrition.get(date).getProtein()}" />
							Riebalai:<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${dailyNutrition.get(date).getFat()}" />
							Angliavandeniai:<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${dailyNutrition.get(date).getCarbs()}" />
						</div>

					</h3></td>
				<c:forEach var="mealType" items="${mealTypes}">
					<td class="w-20 text-center" data-test="${meal }"
						data-meal-type="${mealType}" data-date="${date}"><c:forEach
							var="meal" items="${plan.meals}">
							<c:if
								test="${(meal.date == date) && (meal.mealType == mealType)}">
								<c:forEach var="mealDish" items="${meal.mealDishes}">
									<c:if test="${mealDish.dish['class'].simpleName == 'Recipe'}">
										<p data-container="body" data-toggle="popover"
											data-trigger="hover" data-placement="right" data-html="true"
											data-content="<div><img class='img-thmbnl' src='${mealDish.dish.image}'></div>">
											<a class="open-edit-recipe-modal" href="javascript:void(0);"
												data-toggle="modal" data-dish-id="${mealDish.dish.id }"
												data-meal-id="${meal.id }">${mealDish.dish.title}</a>
										</p>
									</c:if>
									<c:if
										test="${mealDish.dish['class'].simpleName == 'SingleDishProduct'}">
										<p>
											<a class="open-edit-single-dish-modal"
												href="javascript:void(0);" data-toggle="modal"
												data-dish-id="${mealDish.dish.id }"
												data-meal-id="${meal.id }">${mealDish.dish.title}</a>
										</p>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach> <a class="add-dish-button" data-toggle="modal"
						href="#chooseDishModal"> <img class="icon-m"
							src="/images/plus-sign.svg">
					</a></td>

				</c:forEach>
			</tr>
		</c:forEach>
	</tbody>
</table>