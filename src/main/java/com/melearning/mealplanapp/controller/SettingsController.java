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
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String displaySettings(Model model) {
		int planDays = userService.getCurrentUser().getPlanDays();
		model.addAttribute("planDays", planDays);
		return "settings";
	}
	
	@PostMapping("/changePlanDays")
	public String changePlanDays(@RequestParam("quantity") int planDays) {
		User currentUser = userService.getCurrentUser();
		currentUser.setPlanDays(planDays);
		userService.save(currentUser);
		return "redirect:/settings/";
	}

}
