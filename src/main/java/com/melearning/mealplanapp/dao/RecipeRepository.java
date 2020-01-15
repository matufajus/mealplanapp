package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	Recipe findByTitle(String title);
	
	//SELECT * FROM recipe WHERE id IN (SELECT DISTINCT recipe_id from recipe_meal_type WHERE meal_type IN (1, 2));

	List<Recipe> getRecipesByMealTypesIn(List<MealType> mealTypes);

}
