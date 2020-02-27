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
import org.springframework.web.bind.annotation.ResponseBody;

import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.service.MealService;
import com.melearning.mealplanapp.service.RecipeService;
import com.melearning.mealplanapp.service.ShoppingService;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	@Autowired
	MealService mealService;
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	ShoppingService shoppingService;
	
	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("")
	public String showPlan(Model model) {
		User user = userService.getCurrentUser();
		List<Meal> meals = mealService.getUserMealsFromTodayUntil(user.getId(), user.getPlanDays());
		Meal meal = new Meal();
		int planDays = user.getPlanDays();
		String planStyle = user.getPlanStyle();
		model.addAttribute("meals", meals);
		model.addAttribute("dates", mealService.getDatesForMealPlan(planDays));
		model.addAttribute("planStyle", user.getPlanStyle());
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("newMeal", meal);
		model.addAttribute("planDays", planDays);
		model.addAttribute("planStyle", planStyle);
		return "meal-plan";
	}
	
	@PostMapping("/createMeal")
	public String createMeal(int recipeId, String date, String mealType, int servings) {
		Recipe recipe = recipeService.findById(recipeId);
		MealType type = MealType.valueOf(mealType);
		LocalDate mealDate = LocalDate.parse(date);
		Meal meal = new Meal(0, userService.getCurrentUser(), recipe, type, mealDate, servings);
		mealService.saveMeal(meal);
		shoppingService.addMealIngredientsToShoppingList(meal);
		return "redirect:/plan";
	}
	
	@PostMapping("/updateShoppingItem")
	public @ResponseBody String updateShoppingItem(@RequestParam(name = "ids") List<Integer> ids){
		shoppingService.updateShoppingItems(ids);
		return "updated";
	}
	
	@GetMapping("/deleteMeal")
	public String deleteMeal(@RequestParam("mealId") int mealId) {
		mealService.deleteMeal(mealId);
		shoppingService.removeMealIngredientsFromShoppingList(mealId);
		return "redirect:/plan";
	}
	
	@PostMapping("/changePlanSettings")
	public String changePlanSettings(@RequestParam("quantity") int planDays, @RequestParam("planStyle") String planStyle) {
		User currentUser = userService.getCurrentUser();
		currentUser.setPlanDays(planDays);
		currentUser.setPlanStyle(planStyle);
		userService.save(currentUser);
		return "redirect:/plan";
	}
	
	@GetMapping("/getShoppingItems")
	public @ResponseBody List<ShoppingItemDTO> getShoppingItems() {
		User user = userService.getCurrentUser();
		List<Meal> meals = mealService.getUserMealsFromTodayUntil(user.getId(), user.getPlanDays());
		List<ShoppingItemDTO> shoppingList = shoppingService.getShoppingListForMeals(meals);
		return shoppingList;
	}
}
