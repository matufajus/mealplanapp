package com.melearning.mealplanapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.service.RecipeService;


@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping({"/", "/hello"})
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }
	
	@GetMapping("/list")
	public String listRecipes(Model theModel) {
		List<Recipe> recipes = recipeService.findAll();
		theModel.addAttribute("recipes", recipes);
		return "list-recipes";
	}
	
	@GetMapping("/showForm")
	public String showFormForAdd(Model theModel) {
		theModel.addAttribute("recipe", new Recipe());
		return "recipe-form";
	}
	
	@PostMapping("/saveRecipe")
	public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "recipe-form";
		}
		else {
			recipeService.save(recipe);;
			return "redirect:/recipe/list";
		}
	}
	
	@GetMapping("/updateForm")
	public String showFormForUpdate(@RequestParam("recipeId") int id, Model model) {
		model.addAttribute("trick", recipeService.findById(id));
		return "recipe-form";
	}
	
	@GetMapping("/delete")
	public String deleteTrick(@RequestParam("recipeId") int id) {
		recipeService.deleteById(id);
		return "redirect:/recipe/list";
	}
	
	
//	@GetMapping("/recipes/{recipeId}")
//	public Recipe getRecipe(@PathVariable int recipeId) {
//		
//		Recipe recipe = recipeService.findById(recipeId);
//		
//		if (recipe == null)
//			throw new RuntimeException("Recipe id not found - " + recipeId);
//		
//		return recipe;
//	}
//	
//	@PostMapping("/recipes")
//	public Recipe addRecipe(@RequestBody Recipe recipe) {
//		
//		recipe.setId(0);
//		
//		recipeService.save(recipe);
//		
//		return recipe;
//	}
//	
//	@PutMapping("/recipes")
//	public Recipe updateRecipe(@RequestBody Recipe recipe) {
//		
//		recipeService.save(recipe);
//		
//		return recipe;
//	}
//	
//	@DeleteMapping("/recipes/{recipeId}")
//	public String deleteRecipe(@PathVariable int recipeId) {
//		
//		Recipe recipe = recipeService.findById(recipeId);
//		
//		if(recipe == null) {
//			throw new RuntimeException("Recipe id not found - " + recipeId);
//		}
//		
//		recipeService.deleteById(recipeId);
//		
//		return "Deleted recipe id = " + recipeId;
//	}

}
