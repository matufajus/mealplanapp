package com.melearning.mealplanapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melearning.mealplanapp.dao.RecipeRepository;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	RecipeRepository recipeRepository;

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	@Override
	public Recipe findById(int id) {
		Optional<Recipe> result = recipeRepository.findById(id);
		Recipe recipe = null;
		if (result.isPresent()) {
			recipe= result.get();
		}else {
			throw new RuntimeException("Did not find recipe id - " + id);
		}
		return recipe;
	}

	@Override
	public void save(Recipe recipe) {
		recipeRepository.save(recipe);
	}

	@Override
	public void deleteById(int id) {
		recipeRepository.deleteById(id);
	}
	
	@Override
	public Recipe findByTitle(String title) {
		return recipeRepository.findByTitle(title);
	}

	@Override
	public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products) {
		List<Recipe> recipes = recipeRepository.findAll();
		List<Recipe> availableRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : recipes) {
			if (areIngredientsInKitchen(recipe.getIngredients(), products)) {
				availableRecipes.add(recipe);
			}
		}
		return availableRecipes;
	}
	
	private boolean isIngredientInKitchen(Ingredient ingredient, List<KitchenProduct> products) {
		for(KitchenProduct product: products) {
			if (ingredient.getName().equals(product.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean areIngredientsInKitchen(List<Ingredient> ingredients, List<KitchenProduct> products) {
		for (Ingredient ingredient : ingredients) {
			if (!isIngredientInKitchen(ingredient, products)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<Recipe> getRecipesByMealTypes(List<MealType> mealTypes) {
		List<Recipe> listWithDuplicates = recipeRepository.getRecipesByMealTypesIn(mealTypes);
		List<Recipe> listWithoutDuplicates = new ArrayList<Recipe>(new HashSet<Recipe>(listWithDuplicates));
		return listWithoutDuplicates;
	}

}
