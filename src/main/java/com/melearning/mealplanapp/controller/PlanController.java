package com.melearning.mealplanapp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.validation.Valid;

import org.hibernate.hql.internal.ast.tree.MapKeyEntityFromElement;
import org.modelmapper.spi.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.Dish;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealDish;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.SingleDishProduct;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.enumeration.UnitType;
import com.melearning.mealplanapp.exception.DuplicateDishInMealException;
import com.melearning.mealplanapp.exception.OverlappingPlanDatesExceptions;
import com.melearning.mealplanapp.service.FoodProductService;
import com.melearning.mealplanapp.service.KitchenService;
import com.melearning.mealplanapp.service.PlanService;
import com.melearning.mealplanapp.service.RecipeService;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/plan")
public class PlanController {

	@Autowired
	RecipeService recipeService;

	@Autowired
	UserService userService;

	@Autowired
	KitchenService kitchenService;

	@Autowired
	PlanService planService;

	@Autowired
	FoodProductService foodProductService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String getUserPlans(@ModelAttribute String errorMessage, Model model) {
		User user = userService.getCurrentUser();
		List<Plan> previousPlans = planService.getAllUserPlansUntil(user.getId(), LocalDate.now());
		List<Plan> upcomingPlans = planService.getAllUserPlansFrom(user.getId(), LocalDate.now());
		logger.debug("Number of user plans found: old - " + previousPlans.size() + ", new - " + upcomingPlans.size());
		model.addAttribute("previousPlans", previousPlans);
		model.addAttribute("upcomingPlans", upcomingPlans);
		if (!errorMessage.isEmpty()) {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "plans";
	}

	@GetMapping("/meals")
	public String showPlan(@ModelAttribute String errorMessage, @RequestParam int id, Model model) {
		User user = userService.getCurrentUser();
		Plan plan = planService.getPlanById(id);
		for (Meal meal : plan.getMeals()) {
			Collections.sort(meal.getMealDishes());
		}
		model.addAttribute("planStyle", user.getPlanStyle());
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("unitTypes", UnitType.values());
		model.addAttribute("plan", plan);
		return "plan-meals";
	}

	@PostMapping("/createPlan")
	public String saveNewPlan(String title, String dates, RedirectAttributes redirectAttrs) {
		try {
			Plan plan = new Plan();
			plan.setTitle(title);
			String[] datesArray = dates.split(" - ");
			plan.setStartDate(LocalDate.parse(datesArray[0]));
			plan.setEndDate(LocalDate.parse(datesArray[1]));
			plan.setUser(userService.getCurrentUser());
			logger.info("Creating plan name: " + plan.getTitle() + ", from: " + plan.getStartDate() + ", to: "
					+ plan.getEndDate() + ", for user with id: ", plan.getUser().getId());
			planService.save(plan);
			logger.info("Plan created with id: " + plan.getId());
			return "redirect:/plan/meals?id=" + plan.getId();
		} catch (OverlappingPlanDatesExceptions e) {
			logger.warn("User already has a plan for specified dates");
			redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/plan/list";
		}
	}

	@PostMapping("/addDish")
	public String addDishToMeal(Integer recipeId, Integer foodProductId, Float ammount, String unitType, String date,
			String mealType, int servings, int planId, RedirectAttributes redirectAttrs) {
		MealType mealTypeVal = MealType.valueOf(mealType);
		LocalDate mealDate = LocalDate.parse(date);
		// get meal if exists by plan id, date and mealType
		Meal meal = planService.getMeal(planId, mealDate, mealTypeVal);
		// if meal doesn't exist create new
		if (meal == null) {
			Plan plan = planService.getPlanById(planId);
			meal = new Meal(0, new ArrayList<MealDish>(), mealTypeVal, mealDate, plan);
		}
		try {
			if (recipeId != null) {
				Dish dish = recipeService.findById(recipeId);
				planService.addDishToMeal(meal, dish, servings);
			} else if (foodProductId != null) {
				UnitType ingrUnitType = UnitType.valueOf(unitType);
				FoodProduct foodProduct = foodProductService.getFoodProduct(foodProductId);
				Ingredient ingredient = new Ingredient(0, ammount, ingrUnitType, foodProduct);
				SingleDishProduct dish = new SingleDishProduct();
				dish.setIngredient(ingredient);
				planService.saveSingleDish(dish);
				planService.addDishToMeal(meal, dish, servings);
			}
		} catch (DuplicateDishInMealException e) {
			logger.warn("User already has this dish in meal the same date and same type");
			redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/plan/meals?id=" + planId;
		}
		logger.info("New dish added to meal(id: " + meal.getId() + ") in plan(id: " + planId + ")");
		return "redirect:/plan/meals?id=" + planId;
	}

	@GetMapping("/removeRecipe")
	public String removeRecipeFromMeal(@RequestParam("mealId") int mealId, @RequestParam("recipeId") int recipeId) {
		Meal meal = planService.getMeal(mealId);
		Recipe recipe = recipeService.findById(recipeId);
		planService.removeDishFromMeal(meal, recipe);
		Plan plan = planService.getPlanByMealId(mealId);
		return "redirect:/plan/meals?id=" + plan.getId();
	}

	@GetMapping("/removeSingleDish")
	public String removeSingleDish(@RequestParam("mealId") int mealId, @RequestParam("singleDishId") int singleDishId) {
		Meal meal = planService.getMeal(mealId);
		SingleDishProduct singleDishProduct = planService.getSingleDishProduct(singleDishId);
		planService.removeDishFromMeal(meal, singleDishProduct);
		planService.deleteSingleDish(singleDishProduct);
		Plan plan = planService.getPlanByMealId(mealId);
		return "redirect:/plan/meals?id=" + plan.getId();
	}

	@GetMapping("/getMeal")
	public @ResponseBody Meal getMeal(@RequestParam("mealId") int id) {
		return planService.getMeal(id);
	}

	@GetMapping("/getSingleDish")
	public @ResponseBody SingleDishProduct getSingleDish(@RequestParam("productId") int id) {
		SingleDishProduct singleDish = planService.getSingleDishProduct(id);
		singleDish.getIngredients().size();
		return singleDish;
	}
	
	@GetMapping("/getMealDish")
	public @ResponseBody MealDish getMealDish(@RequestParam("mealId") int mealId,
			@RequestParam("recipeId") Integer recipeId) {
		Meal meal = planService.getMeal(mealId);
		Recipe recipe = recipeService.findById(recipeId);
		return meal.findMealDish(recipe);
	}

	@GetMapping("/getMealsForToday")
	public @ResponseBody List<Meal> getMealsForToday() {
		User user = userService.getCurrentUser();
		Plan plan = planService.getCurrentPlan(user);
		return plan.getMealsForToday();
	}

	@GetMapping("/getShoppingItems")
	public @ResponseBody Map<String, List<ShoppingItemDTO>> getShoppingItems(@RequestParam int planId) {
		User user = userService.getCurrentUser();
		Plan plan = planService.getPlanById(planId);
		Map<String, List<ShoppingItemDTO>> shoppingList = planService.getPreparedShoppingList(planId);
		return shoppingList;
	}

	@PostMapping("/updateShoppingItem")
	public @ResponseBody String updateShoppingItem(@RequestParam int planId, @RequestParam String ingredientName,
			@RequestParam boolean isDone, @RequestParam String units) {
		planService.updateShoppingItems(planId, ingredientName, isDone, UnitType.valueOf(units));
		return "updated";
	}

}
