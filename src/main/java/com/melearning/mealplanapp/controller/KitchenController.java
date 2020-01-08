package com.melearning.mealplanapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.FoodType;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.security.CustomUserDetails;
import com.melearning.mealplanapp.service.KitchenService;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {

	@Autowired
	KitchenService kitchenService;

	@Autowired
	UserService userService;

	@GetMapping("/showProducts")
	public String showKitchen(Model model, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		model.addAttribute("products", kitchenService.getAllProductsForUser(currentUser.getUserId()));
		model.addAttribute("foodTypes", FoodType.values());
		model.addAttribute("newProduct", new KitchenProduct());
		return "kitchen";
	}
	@PostMapping("/addProduct")
	public String addProduct(@Valid @ModelAttribute("newProduct") KitchenProduct product, BindingResult bindingResult, Authentication authentication) {
//		if (bindingResult.hasErrors()) {
//			return "kitchen";
//		}
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		product.setUser(currentUser.getUser());
		kitchenService.addProduct(product);
		return "redirect:/kitchen/showProducts";
	}
	
	

}
