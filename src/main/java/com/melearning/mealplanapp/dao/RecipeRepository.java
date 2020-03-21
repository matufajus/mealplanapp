package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	Recipe findByTitle(String title);
	
	//SELECT * FROM recipe WHERE id IN (SELECT DISTINCT recipe_id from recipe_meal_type WHERE meal_type IN (1, 2));

	List<Recipe> getRecipesByMealTypesIn(List<MealType> mealTypes);
	
	List<Recipe> findByOwnerIdOrderByIdDesc(long id);
	
	List<Recipe> findBySharedAndInspected(boolean b, boolean c);
	
	List<Recipe> findByInspected(boolean b);
	
	List<Recipe> findByShared(boolean b);
	
	List<Recipe> findByPublished(boolean b);

	List<Recipe> findBySharedAndInspectedAndPublished(boolean b, boolean c, boolean d);

	Page<Recipe> findByPublished(boolean b, Pageable pageable);

	Page<Recipe> findByOwnerId(long currentUserId, Pageable pageable);
}
