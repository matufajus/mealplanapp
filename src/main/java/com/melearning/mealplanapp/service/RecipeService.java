package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;

public interface RecipeService {
	
	public List<Recipe> findAll();
	
	public Recipe findById(int id);
	
	public void save(Recipe recipe);
	
	public void deleteById(int id);
	
	public Recipe findByTitle(String title);
	
	public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products);
	
	public Page<Recipe> getRecipesByPage(int pageId, int pageSize);
	
	public List<String> getNamesLike(String keyword);
	
	public FoodProduct getFoodProduct(String name);

	public List<Recipe> findByOwnerId(long currentUserId);

	public List<Recipe> getRecipesWaitingForInspection();

	public List<Recipe> getPublicRecipes();

	public List<Recipe> getPrivateRecipes();

	public void makeRecipePublic(int recipeId, User publisher);

	public List<Recipe> getRejectedRecipes();

	public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
			List<MealType> selectedMealtypes, List<String> products);

	public List<Ingredient> getUnkownIngredients();

	public void updateIngredientName(int id, String name);

	public void addFoodProduct(FoodProduct foodProduct);


}
