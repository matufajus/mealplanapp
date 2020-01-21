package com.melearning.mealplanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.security.CustomUserDetails;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String displaySettings(Model model, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		int planDays = currentUser.getUser().getPlanDays();
		model.addAttribute("planDays", planDays);
		return "settings";
	}
	
	@PostMapping("/changePlanDays")
	public void changePlanDays(@RequestParam("planDays") int planDays, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		currentUser.getUser().setPlanDays(planDays);
		userService.save(currentUser.getUser());
	}

}
