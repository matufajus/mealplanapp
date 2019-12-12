package com.melearning.mealplanapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

import com.melearning.mealplanapp.demodata.CSVReader;
import com.melearning.mealplanapp.demodata.Record;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.service.IngredientService;
import com.melearning.mealplanapp.service.RecipeService;


@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String listRecipes(Model theModel) {
		List<Recipe> recipes = recipeService.findAll();
		theModel.addAttribute("recipes", recipes);
		return "list-recipes";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/showForm")
	public String showFormForAdd(Model theModel) {
		theModel.addAttribute("recipe", new Recipe());
		return "recipe-form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/saveRecipe")
	public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "recipe-form";
		}
		else {
			recipe.getIngredients().removeIf(i -> (i.getId() == 0));
			recipeService.save(recipe);
			return "redirect:/recipe/list";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/updateForm")
	public String showFormForUpdate(@RequestParam("recipeId") int id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe-form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete")
	public String deleteTrick(@RequestParam("recipeId") int id) {
		recipeService.deleteById(id);
		return "redirect:/recipe/list";
	}
	
	
	@GetMapping("/uploadCSVToDatabase")
	public String uploadCSV() {
		CSVReader csvReader = new CSVReader("C:\\\\Users\\\\Admins\\\\Downloads\\\\lamaistas-vegetariski.csv");
		try {
			List<Record> records = csvReader.getRecords();
			Recipe recipe = new Recipe();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			List<Preparation> preparations = new ArrayList<Preparation>();
			int ingredientCounter = 0;
			for (Record record : records) {
				if (recipeService.findByTitle(record.getTitle()) != null)
					continue;
				if (!record.getTitle().equals(recipe.getTitle())) {
					if (recipe.getTitle() != null)
						recipeService.save(recipe);
					recipe = new Recipe();
					ingredients = new ArrayList<Ingredient>();
					preparations = new ArrayList<Preparation>();
					recipe.setTitle(record.getTitle());
					recipe.setImage(record.getImage());
					ingredientCounter = 0;
				}
				if (!record.getAmmountUnits().isEmpty()) {
					Ingredient ingredient = new Ingredient(); 
					ingredient.setRecipe(recipe);
					ingredient.setAmmount(record.getAmmountUnits());
					ingredients.add(ingredient);
				}else if (!record.getIngredients().isEmpty()) {
					Ingredient ingredient = ingredients.get(ingredientCounter);
					ingredient.setName(record.getIngredients());
					ingredients.set(ingredientCounter, ingredient);
					ingredientCounter++;
				}else if (!record.getPreparation().isEmpty()) {
					Preparation preparation = new Preparation();
					preparation.setRecipe(recipe);
					preparation.setDescription(record.getPreparation());
					preparations.add(preparation);
				}	
				recipe.setIngredients(ingredients);
				recipe.setPreparations(preparations);
			}
			recipeService.save(recipe);
			
		} catch (IOException e) {
			System.out.println("Couldn't read the file. Exception: " + e);
		}
		return null;
	}
	
	@GetMapping("/info")
	public String showRecipe(@RequestParam("recipeId") int id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe";
	}
	

}
