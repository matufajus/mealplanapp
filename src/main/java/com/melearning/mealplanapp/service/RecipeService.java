package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;

public interface RecipeService {
	
	public List<Recipe> findAll();
	
	public Recipe findById(int id);
	
	public void save(Recipe recipe);
	
	public void deleteById(int id);
	
	public Recipe findByTitle(String title);
	
	public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products);
	
	public List<Recipe> getRecipesByMealTypes(List<MealType> mealTypes);
	
	public Page<Recipe> getRecipesByPage(int pageId, int pageSize);

}
