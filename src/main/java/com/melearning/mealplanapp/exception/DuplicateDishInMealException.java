package com.melearning.mealplanapp.exception;

import com.melearning.mealplanapp.entity.Dish;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Recipe;

public class DuplicateDishInMealException extends RuntimeException{

	public DuplicateDishInMealException(Dish dish, Meal meal) {
		super("Receptas arba produktas pavadinimu "+ dish.getTitle() +" jau yra priskirtas prie: "+meal.getDate()+" " + meal.getMealType().getLabel());
	}
}
