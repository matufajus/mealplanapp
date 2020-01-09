package com.melearning.mealplanapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.FoodType;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.exception.UniqueProductConstraintValidationException;
import com.melearning.mealplanapp.security.CustomUserDetails;
import com.melearning.mealplanapp.service.KitchenService;
import com.melearning.mealplanapp.service.RecipeService;
import com.melearning.mealplanapp.service.UserService;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {

	@Autowired
	KitchenService kitchenService;
	
	@Autowired
	RecipeService recipeService;

	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showProducts")
	public String showKitchen(@ModelAttribute("errorMessage") String errorMessage, Model model, Authentication authentication) {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		List<KitchenProduct> products =  kitchenService.getAllProductsForUser(currentUser.getUserId());
		model.addAttribute("products", products);
		model.addAttribute("recipes", recipeService.getRecipesForUserProducts(products));
		model.addAttribute("foodTypes", FoodType.values());
		model.addAttribute("newProduct", new KitchenProduct());
		if(errorMessage != null) {
	        model.addAttribute("errorMessage", errorMessage);
	    }
		return "kitchen";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid @ModelAttribute("newProduct") KitchenProduct product, BindingResult bindingResult,
			Authentication authentication, RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			System.err.println(bindingResult.toString());
			return "kitchen";
		}
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
		product.setUser(currentUser.getUser());
		try {
			kitchenService.addProduct(product);
		} catch (UniqueProductConstraintValidationException e) {
			redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/kitchen/showProducts";
		}
		
		return "redirect:/kitchen/showProducts";
	}
	
	@GetMapping("/removeProduct")
	public String removeProduct(@RequestParam("productId") int id) {
		kitchenService.removeProduct(id);
		return "redirect:/kitchen/showProducts";
	}
	
	

}
