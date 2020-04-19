package com.melearning.mealplanapp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;
	Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showRegistration(Model theModel) {
		theModel.addAttribute("userDTO", new UserDTO());
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
			BindingResult theBindingResult, Model theModel, HttpServletRequest request) {
		String username = userDTO.getUsername();
		logger.info("Processing registration form for: " + username);

		// form validation
		if (theBindingResult.hasErrors()) {
			return "registration-form";
		}

		// check the database if user already exists
		User existing = userService.findByUsername(username);
		if (existing != null) {
			theModel.addAttribute("userDTO", new UserDTO());
			theModel.addAttribute("registrationError", "User name already exists.");
			logger.warn("User name already exists.");
			return "registration-form";
		}
		
		// create user account
		userService.save(userDTO);
		logger.info("Successfully created user: " + username);

		// automatically login user after successful registration
		authWithHttpServletRequest(request, username, userDTO.getPassword());

		return "home";
	}

	public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
		try {
			request.login(username, password);
		} catch (ServletException e) {
			logger.warn("User with username: " + username + " couldn't be logged in. Error: " + e.getMessage());
		}
	}
}
