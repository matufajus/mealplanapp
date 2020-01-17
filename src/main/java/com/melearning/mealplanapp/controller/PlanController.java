package com.melearning.mealplanapp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.security.CustomUserDetails;
import com.melearning.mealplanapp.service.MealService;
import com.melearning.mealplanapp.service.RecipeService;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	@Autowired
	MealService mealService;
	
	@Autowired
	RecipeService recipeService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("")
	public String showPlan(Model model, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		List<Meal> meals = mealService.getAllUserMeals(currentUser.getUserId());
		Meal meal = new Meal();
		model.addAttribute("meals", meals);
		model.addAttribute("dates", mealService.getDatesForMealPlan(7));
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("newMeal", meal);
		return "meal-plan";
	}
	
	@PostMapping("/createMeal")
	public String createMeal(int recipeId, String date, String mealType, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		Recipe recipe = recipeService.findById(recipeId);
		MealType type = MealType.valueOf(mealType);
		LocalDate mealDate = LocalDate.parse(date);
		Meal meal = new Meal(0, currentUser.getUser(), recipe, type, mealDate);
		mealService.saveMeal(meal);
		
		return "redirect:/plan";
	}

}
