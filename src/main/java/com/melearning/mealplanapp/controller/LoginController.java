package com.melearning.mealplanapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.service.MealService;
import com.melearning.mealplanapp.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	MealService mealService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("showLogin")
	public String showLogin() {
		return "login";
	}
	
	@GetMapping("home")
	public String showHome(Model model) {
		User user = userService.getCurrentUser();
		List<Meal> meals = mealService.getUserMealsFromTodayUntil(user.getId(), 1);
		model.addAttribute("mealTypes", MealType.values());
		model.addAttribute("meals", meals);
		return "home";
	}
	
	@GetMapping("/")
	public String showLandingPage(Authentication authentication) {
//		//uncomment if logged user shouldn't see index(landing) page
//		if (authentication != null) 
//			if (authentication.isAuthenticated())
//				return "home";
//	
		return "index";
	}


}
