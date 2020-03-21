package com.melearning.mealplanapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melearning.mealplanapp.dao.FoodProductRepository;
import com.melearning.mealplanapp.dao.IngredientRepository;
import com.melearning.mealplanapp.dao.RecipeRepository;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.FoodType;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	RecipeRepository recipeRepository;
	
	FoodProductRepository foodProductRepository;
	
	IngredientRepository ingredientRepository;

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, FoodProductRepository foodProductRepository,
			IngredientRepository ingredientRepository) {
		this.recipeRepository = recipeRepository;
		this.foodProductRepository = foodProductRepository;
		this.ingredientRepository = ingredientRepository;
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
	public Page<Recipe> getRecipesByPage(int pageId, int pageSize){
		Pageable pageable = PageRequest.of(pageId, pageSize);
		return recipeRepository.findAll(pageable);
	}

//	@Override
//	public List<String> getNamesLike(String keyword) {
//		List<FoodProduct> products = foodProductRepository.findByNameContaining(keyword);
//		List<String> productNames = new ArrayList<String>();
//		if (!products.isEmpty())
//			for (FoodProduct foodProduct : products) {
//				productNames.add(foodProduct.getName());
//			}
//		return productNames;
//	}
	
	@Override
	public List<String> getNamesLike(String keyword) {
		return foodProductRepository.findByNameContaining(keyword);
	}
	
	@Override
	public FoodProduct getFoodProduct(String name) {
		return foodProductRepository.findByName(name);
	}

	@Override
	public List<Recipe> findByOwnerIdDesc(long currentUserId) {
		return recipeRepository.findByOwnerIdOrderByIdDesc(currentUserId);
	}

	@Override
	public void makeRecipePublic(int recipeId, User publisher) {
		Recipe recipe = findById(recipeId);
		recipe.setInspected(true);
		save(recipe);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Recipe copyRecipe = objectMapper.readValue(objectMapper.writeValueAsString(recipe), Recipe.class);
			copyRecipe.setId(0);
			copyRecipe.setOwner(publisher);
			copyRecipe.setPublished(true);
			copyRecipe.getIngredients().forEach(i -> i.setId(0));
			copyRecipe.getPreparations().forEach(p -> p.setId(0));
			copyRecipe.getIngredients().forEach(i -> i.setRecipe(copyRecipe));
			copyRecipe.getPreparations().forEach(p -> p.setRecipe(copyRecipe));
			save(copyRecipe);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public List<Recipe> getRecipesWaitingForInspection() {
		return recipeRepository.findBySharedAndInspected(true, false);
	}

	@Override
	public List<Recipe> getPublicRecipes() {
		return recipeRepository.findByPublished(true);
	}
	
	@Override
	public List<Recipe> getPrivateRecipes(){
		return recipeRepository.findByShared(false);
	}
	
	@Override
	public List<Recipe> getRejectedRecipes(){
		return recipeRepository.findBySharedAndInspectedAndPublished(true, true, false);
	}

	@Override
	public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
			List<MealType> selectedMealtypes, List<String> products) {
		List<Recipe> filteredRecipes;
		if (selectedMealtypes != null)
			filteredRecipes = recipes.stream().filter(recipe -> selectedMealtypes.stream()
					.anyMatch(mealType -> recipe.getMealTypes().contains(mealType))).collect(Collectors.toList());
		else {
			filteredRecipes = recipes;
		}
		if (products != null) {
			filteredRecipes = filteredRecipes.stream().filter(recipe ->
				products.stream().allMatch(product ->
				recipe.getIngredients().stream().map(ingredient ->
				ingredient.getName().toLowerCase()).anyMatch(ingredient ->
				ingredient.contains(product.toLowerCase())))).collect(Collectors.toList());
		}
		return filteredRecipes;
	}

	@Override
	public List<Ingredient> getUnkownIngredients() {
		return ingredientRepository.findUnknownIngredients();
	}

	@Override
	public void updateIngredientName(int id, String name) {
		Optional<Ingredient> result = ingredientRepository.findById(id);
		if (result.isPresent()) {
			Ingredient ingredient = result.get();
			ingredient.setName(name);
			ingredientRepository.save(ingredient);
		}
		
	}

	@Override
	public void addFoodProduct(FoodProduct foodProduct) {
		foodProductRepository.save(foodProduct);
	}

	@Override
	public Page<Recipe> getPublicRecipes(int pageId, int pageSize) {
		Pageable pageable = PageRequest.of(pageId, pageSize);
		return recipeRepository.findByPublished(true, pageable);
	}

	@Override
	public Page<Recipe> findByOwnerId(long currentUserId, int pageId, int pageSize) {
		Pageable pageable = PageRequest.of(pageId, pageSize);
		return recipeRepository.findByOwnerId(currentUserId, pageable);
	}

	@Override
	public List<FoodProduct> getFoodProductsByType(FoodType foodType) {
		return foodProductRepository.findByFoodType(foodType);
	}
	
	@Override
	public List<FoodProduct> getFoodProducts() {
		return foodProductRepository.findAll();
	}

	@Override
	public FoodProduct getFoodProduct(int foodProductId) {	
		Optional<FoodProduct> result = foodProductRepository.findById(foodProductId);
		if (result.isPresent()) {
			return result.get();
		}
		return null;
		
	}

}
