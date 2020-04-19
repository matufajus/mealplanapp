package com.melearning.mealplanapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.enumeration.UnitType;
import com.melearning.mealplanapp.service.FileService;
import com.melearning.mealplanapp.service.RecipeService;
import com.melearning.mealplanapp.service.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	ModelMapper mapper;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String listPublishedRecipes(Model model) {
		List<Recipe> recipes = recipeService.getPublicRecipes();
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("recipes", recipes);
		return "recipes-list";
	}

	@GetMapping("/myList")
	public String listUserRecipes(Model model) {
		List<Recipe> recipes = recipeService.findByOwnerIdDesc(userService.getCurrentUserId());
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("recipes", recipes);
		return "recipes-list";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/sharedList")
	public String listSharedRecipes(Model model) {
		List<Recipe> recipes = recipeService.getRecipesWaitingForInspection();
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("recipes", recipes);
		return "recipes-list";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/privateList")
	public String listUsersPrivateRecipes(Model model) {
		List<Recipe> recipes = recipeService.getPrivateRecipes();
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("recipes", recipes);
		return "recipes-list";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/rejectedList")
	public String listRejectedRecipes(Model model) {
		List<Recipe> recipes = recipeService.getRejectedRecipes();
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("recipes", recipes);
		return "recipes-list";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/approveRecipe")
	public String makeRecipePublic(@RequestParam("recipeId") int recipeId) {
		User publisher = userService.getCurrentUser();
		recipeService.makeRecipePublic(recipeId, publisher);
		return "redirect:/recipe/sharedList";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/rejectRecipe")
	public String makeRecipePrivate(@RequestParam("recipeId") int recipeId) {
		Recipe recipe = recipeService.findById(recipeId);
		recipe.setInspected(true);
		recipe.setPublished(false);
		recipeService.save(recipe);
		return "redirect:/recipe/sharedList";
	}

	@GetMapping("/showForm")
	public String showFormForAdd(Model model) {
		RecipeFormDTO recipeDTO = new RecipeFormDTO();
		recipeDTO.setShared(true);
		if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
			recipeDTO.setInspected(true);
			recipeDTO.setPublished(true);
		}
		model.addAttribute("recipe", recipeDTO);
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		return "recipe-form";
	}

	@PostMapping("/saveRecipe")
	public @ResponseBody ResponseEntity<Object> saveRecipe(@Valid @ModelAttribute("recipe") RecipeFormDTO recipeDTO,
			BindingResult bindingResult, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bindingResult.hasErrors()) {
			map.put("errors", bindingResult.getAllErrors());
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		} else {
			if (!recipeDTO.getImageFile().isEmpty()) {
				String prefix = RandomString.make(8);
				try {
					fileService.uploadFile(recipeDTO.getImageFile(), prefix);
					recipeDTO.setImage("/recipeImages/" + prefix + recipeDTO.getImageFile().getOriginalFilename());
				} catch (Exception e) {
					recipeDTO.setImage("/recipeImages/default.png");
				}
			}
			Recipe recipe = convertToEntity(recipeDTO);
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
			recipeService.save(recipe);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
	}

	@GetMapping("/delete")
	public String deleteRecipe(@RequestParam("recipeId") int id) {
		if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
			recipeService.deleteById(id);
		} else {
			Recipe recipe = recipeService.findById(id);
			if (recipe.getOwner() == userService.getCurrentUser()) {
				recipeService.deleteById(id);
			}
		}
		return "redirect:/recipe/list";
	}

	@GetMapping("/getRecipes")
	public @ResponseBody Page<Recipe> getRecipes(@RequestParam(name = "section") String section,
			@RequestParam(name = "pageId", defaultValue = "0") int pageId,
			@RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
		if (section.equals("public")) {
			return recipeService.getPublicRecipes(pageId, pageSize);
		} else {
			return recipeService.findByOwnerId(userService.getCurrentUserId(), pageId, pageSize);
		}

	}

	private RecipeFormDTO convertToDTO(Recipe recipe) {
		RecipeFormDTO recipeDTO = mapper.map(recipe, RecipeFormDTO.class);
		recipeDTO.setOwner(recipe.getOwner().getUsername());
		return recipeDTO;
	}

	private Recipe convertToEntity(RecipeFormDTO recipeDTO) {
		Recipe recipe = mapper.map(recipeDTO, Recipe.class);
		recipe.setOwner(userService.findByUsername(recipeDTO.getOwner()));
		return recipe;
	}

	@GetMapping("/getRecipe")
	public @ResponseBody RecipeFormDTO getRecipe(@RequestParam("recipeId") int id) {
		return convertToDTO(recipeService.findById(id));
	}

	@GetMapping("/getUnitTypes")
	public @ResponseBody UnitType[] getUnitTypes() {
		return UnitType.values();
	}

	@GetMapping("/searchProducts")
	public @ResponseBody List<String> search(@RequestParam("term") String keyword) {
		return recipeService.getNamesLike(keyword);
	}

	@GetMapping("/getFoodProduct")
	public @ResponseBody FoodProduct getFoodProduct(@RequestParam("name") String name) {
		return recipeService.getFoodProduct(name);
	}

	@GetMapping("/getFilteredRecipes")
	public @ResponseBody List<Recipe> getFilteredRecipes(
			@RequestParam(name = "section", required = true) String section,
			@RequestParam(name = "type", required = false) List<MealType> selectedMealtypes,
			@RequestParam(name = "products", required = false) List<String> products) {
		List<Recipe> recipes = new ArrayList<Recipe>();
		switch (section) {
		case "list":
			recipes = recipeService.getPublicRecipes();
			break;
		case "myList":
			recipes = recipeService.findByOwnerIdDesc(userService.getCurrentUserId());
			break;
		case "sharedList":
			if (userService.hasCurrentUserRole("ROLE_ADMIN"))
				recipes = recipeService.getRecipesWaitingForInspection();
			break;
		case "privateList":
			if (userService.hasCurrentUserRole("ROLE_ADMIN"))
				recipes = recipeService.getPrivateRecipes();
			break;
		case "rejectedList":
			if (userService.hasCurrentUserRole("ROLE_ADMIN"))
				recipes = recipeService.getRejectedRecipes();
			break;
		default:
			break;
		}
		recipes = recipeService.filterRecipesByMealTypesAndSearchProducts(recipes, selectedMealtypes, products);
		return recipes;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/unknownIngredients")
	public String showUnknownIngredients(Model model) {
		model.addAttribute("ingredients", recipeService.getUnkownIngredients());
		model.addAttribute("foodProduct", new FoodProduct());
		return "unknown-ingredients";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editIngredient")
	public String editIngredient(@RequestParam(name = "id") int id, @RequestParam(name = "name") String name) {
		recipeService.updateIngredientName(id, name);
		return "redirect:/recipe/unknownIngredients";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addFoodProduct")
	public String addFoodProduct(@ModelAttribute("foodProduct") FoodProduct foodProduct) {
		recipeService.addFoodProduct(foodProduct);
		return "redirect:/recipe/unknownIngredients";
	}

	@GetMapping("/checkAuthorizationForRecipe")
	public @ResponseBody boolean checkIfuserIsAuthorizedForRecipe(@RequestParam int recipeId) {
		Recipe recipe = recipeService.findById(recipeId);
		if ((userService.getCurrentUser() == recipe.getOwner()) || (userService.hasCurrentUserRole("ROLE_ADMIN"))) {
			return true;
		}
		return false;
	}

	@GetMapping("/hasUserRole")
	public @ResponseBody boolean checkIfuserHasRole(@RequestParam String role) {
		if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	@GetMapping("/getFoodProducts")
	public @ResponseBody List<FoodProduct> getFoodProducts() {
		List<FoodProduct> products = recipeService.getFoodProducts();
		return products;
	}

	@GetMapping("/getFoodProductsByType")
	public @ResponseBody List<FoodProduct> getFoodProductsByType(@RequestParam String type) {
		FoodType foodType = FoodType.valueOf(type);
		List<FoodProduct> products = recipeService.getFoodProductsByType(foodType);
		return products;
	}

}
