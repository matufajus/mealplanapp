package com.melearning.mealplanapp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.validation.Valid;

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
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.exception.DuplicateRecipeInMealException;
import com.melearning.mealplanapp.exception.OverlappingPlanDatesExceptions;
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
		model.addAttribute("planStyle", user.getPlanStyle());
		model.addAttribute("mealTypes", MealType.values());
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

	@PostMapping("/addRecipe")
	public String addRecipeToMeal(int recipeId, String date, String mealType, int servings, int planId, RedirectAttributes redirectAttrs) {
		MealType type = MealType.valueOf(mealType);
		LocalDate mealDate = LocalDate.parse(date);
		// get meal if exists by plan id, date and mealType
		Meal meal = planService.getMeal(planId, mealDate, type);
		// if meal doesn't exist create new
		if (meal == null) {
			Plan plan = planService.getPlanById(planId);
			meal = new Meal(0, new ArrayList<Recipe>(), type, mealDate, servings, plan);
		}
		// add recipe to recipes list in a meal
		try {
			Recipe recipe = recipeService.findById(recipeId);
			planService.addRecipeToMeal(meal, recipe);
		} catch (DuplicateRecipeInMealException e) {
			logger.warn("User already has this recipe in meal the same date and same type");
			redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/plan/meals?id=" + planId;
		}
		logger.info("New meal(id: " + meal.getId() + ") added to plan(id: " + planId + ")");
		return "redirect:/plan/meals?id=" + planId;
	}

	@GetMapping("/removeRecipe")
	public String removeRecipeFromMeal(@RequestParam("mealId") int mealId, @RequestParam("recipeId") int recipeId) {
		Meal meal = planService.getMeal(mealId);
		Recipe recipe = recipeService.findById(recipeId);
		planService.removeRecipeFromMeal(meal, recipe);
		Plan plan = planService.getPlanByMealId(mealId);
		return "redirect:/plan/meals?id=" + plan.getId();
	}

	@GetMapping("/getMeal")
	public @ResponseBody Meal getMeal(@RequestParam("mealId") int id) {
		return planService.getMeal(id);
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
			@RequestParam boolean isDone) {
		planService.updateShoppingItems(planId, ingredientName, isDone);
		return "updated";
	}

}
