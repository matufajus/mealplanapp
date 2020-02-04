package com.melearning.mealplanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
	
	@GetMapping("showLogin")
	public String showLogin() {
		return "login";
	}
	
	@GetMapping("home")
	public String showHome() {
		return "home";
	}
	
	@GetMapping("/")
	public String showLandingPage(Authentication authentication) {
		if (authentication != null) 
			if (authentication.isAuthenticated())
				return "home";
	
		return "index";
	}


}
