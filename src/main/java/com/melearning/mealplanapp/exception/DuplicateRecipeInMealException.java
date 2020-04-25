package com.melearning.mealplanapp.exception;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Recipe;

public class DuplicateRecipeInMealException extends RuntimeException{

	public DuplicateRecipeInMealException(Recipe recipe, Meal meal) {
		super("Receptas "+ recipe.getTitle() +" jau yra priskirtas prie: "+meal.getDate()+" " + meal.getMealType().getLabel());
	}
}
