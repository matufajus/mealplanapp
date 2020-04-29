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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.melearning.mealplanapp.dao.UserRepository;
import com.melearning.mealplanapp.dto.IngredientDTO;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	FoodProductRepository foodProductRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper mapper;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, FoodProductRepository foodProductRepository) {
		this.recipeRepository = recipeRepository;
		this.foodProductRepository = foodProductRepository;
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
			recipe = result.get();
		} else {
			logger.error("Did not find recipe id - " + id);
			throw new RuntimeException("Did not find recipe id - " + id);
		}
		return recipe;
	}

	@Override
	public void save(RecipeFormDTO recipeFormDTO) {
		Recipe recipe = convertToEntity(recipeFormDTO);
		// for new recipe set user as author and owner
		if (recipe.getId() == 0) {
			User user = userService.getCurrentUser();
			recipe.setAuthor(user.getUsername());
			recipe.setOwner(user);
			if (recipe.getImage() == null) {
				recipe.setImage("/recipeImages/default.png");
			}
			if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
				recipe.setInspected(true);
				recipe.setPublished(true);
			}
		}
		recipeRepository.save(recipe);
	}
	
	@Override
	public RecipeFormDTO getRecipeFormDTO(int id) {
		Recipe recipe = recipeRepository.findById(id).get();
		return convertToDTO(recipe);
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
		for (KitchenProduct product : products) {
			if (ingredient.getFoodProduct().equals(product.getFoodProduct())) {
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
	public Page<Recipe> getRecipesByPage(int pageId, int pageSize) {
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
	public List<Recipe> findByOwnerIdDesc(long currentUserId) {
		return recipeRepository.findByOwnerIdOrderByIdDesc(currentUserId);
	}

	@Override
	public void makeRecipePublic(int recipeId, User publisher) {
		Recipe recipe = findById(recipeId);
		recipe.setInspected(true);
		recipeRepository.save(recipe);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Recipe copyRecipe = objectMapper.readValue(objectMapper.writeValueAsString(recipe), Recipe.class);
			copyRecipe.setId(0);
			copyRecipe.setOwner(publisher);
			copyRecipe.setPublished(true);
			copyRecipe.getIngredients().forEach(i -> i.setId(0));
			copyRecipe.getPreparations().forEach(p -> p.setId(0));
			recipeRepository.save(copyRecipe);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error("Error while creating a copy of recipe: id = " + recipeId + ", error: " + e.getMessage());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Error while creating a copy of recipe: id = " + recipeId + ", error: " + e.getMessage());
		}
	}
	
	@Override
	public void makeRecipePrivate(int recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).get();
		recipe.setInspected(true);
		recipe.setPublished(false);
		recipeRepository.save(recipe);
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
	public List<Recipe> getPrivateRecipes() {
		return recipeRepository.findByShared(false);
	}

	@Override
	public List<Recipe> getRejectedRecipes() {
		return recipeRepository.findBySharedAndInspectedAndPublished(true, true, false);
	}

	@Override
	public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
			List<MealType> selectedMealtypes, List<String> products) {
		List<Recipe> filteredRecipes;
		if (selectedMealtypes != null)
			filteredRecipes = recipes.stream().filter(
					recipe -> selectedMealtypes.stream().anyMatch(mealType -> recipe.getMealTypes().contains(mealType)))
					.collect(Collectors.toList());
		else {
			filteredRecipes = recipes;
		}
		if (products != null) {
			filteredRecipes = filteredRecipes.stream()
					.filter(recipe -> products.stream()
							.allMatch(product -> recipe.getIngredients().stream()
									.map(ingredient -> ingredient.getFoodProduct().getName().toLowerCase())
									.anyMatch(ingredient -> ingredient.contains(product.toLowerCase()))))
					.collect(Collectors.toList());
		}
		return filteredRecipes;
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
	
	private RecipeFormDTO convertToDTO(Recipe recipe) {
		RecipeFormDTO recipeDTO = mapper.map(recipe, RecipeFormDTO.class);
		recipeDTO.setOwner(recipe.getOwner().getUsername());
		recipeDTO.setIngredients(convertToDTOList(recipe.getIngredients()));
		return recipeDTO;
	}

	private Recipe convertToEntity(RecipeFormDTO recipeDTO) {
		Recipe recipe = mapper.map(recipeDTO, Recipe.class);
		recipe.setOwner(userRepository.findByUsername(recipeDTO.getOwner()));		
		recipe.setIngredients(convertToEntityList(recipeDTO.getIngredients()));
		return recipe;
	}
	
	private List<IngredientDTO> convertToDTOList(List<Ingredient> ingredients) {
		List<IngredientDTO> ingredientDTOs = new ArrayList<IngredientDTO>();
		for (Ingredient ingredient : ingredients) {
			IngredientDTO ingredientDTO = mapper.map(ingredient, IngredientDTO.class);
			ingredientDTOs.add(ingredientDTO);
		}
		return ingredientDTOs;
	}
	
	private List<Ingredient> convertToEntityList(List<IngredientDTO> ingredientDTOs){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for (IngredientDTO ingredientDTO : ingredientDTOs) {
			Ingredient ingredient = new Ingredient();
			ingredient.setAmmount(ingredientDTO.getAmmount());
			FoodProduct foodProduct = foodProductRepository.findById(ingredientDTO.getFoodProductId()).get();
			ingredient.setFoodProduct(foodProduct);
			ingredients.add(ingredient);
		}
		return ingredients;
	}

}
