package com.melearning.mealplanapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
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

import com.melearning.mealplanapp.demodata.CSVReader;
import com.melearning.mealplanapp.demodata.Record;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.UnitType;
import com.melearning.mealplanapp.service.FileService;
import com.melearning.mealplanapp.service.RecipeService;
import com.melearning.mealplanapp.service.UserService;


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
	public String listRecipes(Model model) {
		List<Recipe> recipes= recipeService.findAll();
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("recipes", recipes);
		return "list-recipes";
	}
	
	@GetMapping("/myList")
	public String listUserRecipes(Model model) {
		List<Recipe> recipes= recipeService.findByAuthorId(userService.getCurrentUserId());
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("recipes", recipes);
		return "list-recipes";
	}
	
	@GetMapping("/showForm")
	public String showFormForAdd(Model model) {
		RecipeFormDTO recipeDTO = new RecipeFormDTO();		
		if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
			recipeDTO.setShared(true);
		}
		model.addAttribute("recipe", recipeDTO);
		model.addAttribute("mealTypes", MealType.values());
		return "recipe-form";
	}
	
	@PostMapping("/saveRecipe")
	public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeFormDTO recipeDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("mealTypes", MealType.values());
			return "recipe-form";
		}
		else {
			if (!recipeDTO.getImageFile().isEmpty()) {
				fileService.uploadFile(recipeDTO.getImageFile());
				recipeDTO.setImage("/recipeImages/" + recipeDTO.getImageFile().getOriginalFilename());
			} else {
				recipeDTO.setImage("/recipeImages/default.png");
			}
			Recipe recipe = convertToEntity(recipeDTO);
			recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
			recipe.getPreparations().forEach(p -> p.setRecipe(recipe));
			if (recipe.getId() == 0) {
				recipe.setAuthor(userService.getCurrentUser());
			}			
			recipeService.save(recipe);
			return "redirect:/recipe/list";
		}
	}
	
	@GetMapping("/updateForm")
	public String showFormForUpdate(@RequestParam("recipeId") int id, Model model) {
		Recipe recipe = recipeService.findById(id);
		if (recipe.getAuthor() == userService.getCurrentUser() || (userService.hasCurrentUserRole("ROLE_ADMIN"))) {
			model.addAttribute("recipe", convertToDTO(recipe));
			model.addAttribute("mealTypes", MealType.values());
			model.addAttribute("unitTypes", UnitType.values());
			return "recipe-form";
		}else {
			return "error";
		}
		
	}
	
	@GetMapping("/delete")
	public String deleteRecipe(@RequestParam("recipeId") int id) {
		if (userService.hasCurrentUserRole("ROLE_ADMIN")) {
			recipeService.deleteById(id);
		} else {
			Recipe recipe = recipeService.findById(id);
			if (recipe.getAuthor() == userService.getCurrentUser()){
				recipeService.deleteUsersRecipe(recipe);
			}
		}	
		return "redirect:/recipe/list";
	}
	
	@GetMapping("/info")
	public String showRecipe(@RequestParam("recipeId") int id, Model model) {
		Recipe recipe = recipeService.findById(id);
		boolean isAuthor = false;
		if (userService.getCurrentUser() == recipe.getAuthor()) {
			isAuthor = true;
		}
		model.addAttribute("recipe", recipe);
		model.addAttribute("isAuthor", isAuthor);
		
		return "recipe";
	}
	
	@GetMapping("/getRecipes")
	public @ResponseBody Page<Recipe> getRecipes(@RequestParam(name = "pageId", defaultValue = "0") int pageId, @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
		return recipeService.getRecipesByPage(pageId, pageSize);
	}
	
	private RecipeFormDTO convertToDTO(Recipe recipe) {
		return mapper.map(recipe, RecipeFormDTO.class);
	}
	
	private Recipe convertToEntity(RecipeFormDTO recipeDTO) {
		return mapper.map(recipeDTO, Recipe.class);
	}
	
	@GetMapping("/getRecipe")
	public @ResponseBody Recipe getRecipe(@RequestParam("recipeId") int id) {
		return recipeService.findById(id);
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
	public @ResponseBody List<Recipe> getFilteredRecipes(@RequestParam(name = "type", required = false) List<MealType> selectedMealtypes, 
			@RequestParam(name = "products", required = false) List<String> products){
		List<Recipe> recipes = new ArrayList<Recipe>();
		if (selectedMealtypes != null) {		
			if(products != null) {
				recipes = recipeService.getRecipesByMealTypesAndSearchProducts(selectedMealtypes, products);
			}else {
				recipes = recipeService.getRecipesByMealTypes(selectedMealtypes);
			}
		} else if(products != null) {
			recipes = recipeService.getRecipesForSearchProducts(products); 
		} else {
			recipes = recipeService.findAll();
		}
		return recipes;
	}

}
