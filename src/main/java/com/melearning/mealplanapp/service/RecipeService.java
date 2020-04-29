package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;

public interface RecipeService {
	
	public List<Recipe> findAll();
	
	public Recipe findById(int id);
	
	public void save(RecipeFormDTO recipeFormDTO);
	
	public RecipeFormDTO getRecipeFormDTO(int id);
	
	public void deleteById(int id);
	
	public Recipe findByTitle(String title);
	
	public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products);
	
	public Page<Recipe> getRecipesByPage(int pageId, int pageSize);
	
	public List<String> getNamesLike(String keyword);

	public List<Recipe> findByOwnerIdDesc(long currentUserId);

	public List<Recipe> getRecipesWaitingForInspection();

	public List<Recipe> getPublicRecipes();

	public List<Recipe> getPrivateRecipes();

	public void makeRecipePublic(int recipeId, User publisher);
	
	public void makeRecipePrivate(int recipeId);

	public List<Recipe> getRejectedRecipes();

	public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
			List<MealType> selectedMealtypes, List<String> products);

	public Page<Recipe> getPublicRecipes(int pageId, int pageSize);

	public Page<Recipe> findByOwnerId(long currentUserId, int pageId, int pageSize);

}
