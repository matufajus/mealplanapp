package com.melearning.mealplanapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.security.CustomUserDetails;
import com.melearning.mealplanapp.service.MealService;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	@Autowired
	MealService mealService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("")
	public String showPlan(Model model, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		List<Meal> meals = mealService.getAllUserMeals(currentUser.getUserId());
		model.addAttribute("meals", meals);
		model.addAttribute("dates", mealService.extractDatesFromMeals(meals));
		model.addAttribute("mealTypes", MealType.values());
		return "meal-plan";
	}

}
