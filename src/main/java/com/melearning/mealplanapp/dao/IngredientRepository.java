package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.melearning.mealplanapp.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
	
	@Query("SELECT i FROM Ingredient i LEFT JOIN FoodProduct fp ON i.name = fp.name WHERE fp.name IS NULL")
	List<Ingredient> findUnknownIngredients();

}
