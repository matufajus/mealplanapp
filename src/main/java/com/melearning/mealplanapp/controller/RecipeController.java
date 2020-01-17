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
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.service.FileService;
import com.melearning.mealplanapp.service.IngredientService;
import com.melearning.mealplanapp.service.RecipeService;


@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	ModelMapper mapper;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String listRecipes(@RequestParam(name = "type", required = false) List<MealType> selectedMealtypes, Model model) {
		List<Recipe> recipes = recipeService.findAll();
		if (selectedMealtypes != null) {
			model.addAttribute("selectedMealTypes", selectedMealtypes);
			recipes = recipeService.getRecipesByMealTypes(selectedMealtypes);
		}
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("recipes", recipes);
		return "list-recipes";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/showForm")
	public String showFormForAdd(Model model) {
		model.addAttribute("recipe", new RecipeFormDTO());
		model.addAttribute("mealTypes", MealType.values());
		return "recipe-form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/saveRecipe")
	public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeFormDTO recipeDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			System.out.println(recipeDTO.getPreparations());
			System.out.println(recipeDTO.getMealTypes());
			model.addAttribute("mealTypes", MealType.values());
			return "recipe-form";
		}
		else {
			if (!recipeDTO.getImageFile().isEmpty()) {
				fileService.uploadFile(recipeDTO.getImageFile());
				recipeDTO.setImage("/recipeImages/" + recipeDTO.getImageFile().getOriginalFilename());
			}
			Recipe recipe = convertToEntity(recipeDTO);
			recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
			recipe.getPreparations().forEach(p -> p.setRecipe(recipe));
//			recipeDTO.getIngredients().removeIf(i -> (i.getRecipe() == null));
//			recipeDTO.getPreparations().removeIf(p -> (p.getRecipe() == null));
			recipeService.save(recipe);
			return "redirect:/recipe/list";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/updateForm")
	public String showFormForUpdate(@RequestParam("recipeId") int id, Model model) {
		Recipe recipe = recipeService.findById(id);
		model.addAttribute("recipe", convertToDTO(recipe));
		model.addAttribute("mealTypes", MealType.values());
		return "recipe-form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete")
	public String deleteRecipe(@RequestParam("recipeId") int id) {
		recipeService.deleteById(id);
		return "redirect:/recipe/list";
	}
	
	@GetMapping("/info")
	public String showRecipe(@RequestParam("recipeId") int id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
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

}
