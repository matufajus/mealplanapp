package com.melearning.mealplanapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.exception.UniqueProductConstraintValidationException;
import com.melearning.mealplanapp.service.FoodProductService;
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
	FoodProductService foodService;

	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/removeProduct")
	public @ResponseBody String removeProduct(@RequestParam int foodProductId) {
		FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
		long userId = userService.getCurrentUserId();
		kitchenService.removeKitchenProductByFoodProductId(userId, foodProduct.getId());
		return "success";
	}
	
	@GetMapping("/addProduct")
	public @ResponseBody String addProduct(@RequestParam int foodProductId) {
		FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
		KitchenProduct kitchenProduct = new KitchenProduct();
		kitchenProduct.setUser(userService.getCurrentUser());
		kitchenProduct.setFoodProduct(foodProduct);
		kitchenService.addProduct(kitchenProduct);
		return "success";
	}
	
	@GetMapping("/getUserProducts")
	public @ResponseBody List<KitchenProduct> getUserKitchenProducts(){
		List<KitchenProduct> products =  kitchenService.getAllProductsForUser(userService.getCurrentUserId());
		return products;
	}
	
	@GetMapping("/getFoodTypes")
	public @ResponseBody FoodType[] getFoodTypes(){
		return FoodType.values();
	}
	
	@GetMapping("/getAvailableRecipes")
	public @ResponseBody List<Recipe> getRecipesForUsersProducts(){
		List<KitchenProduct> kitchenProducts = kitchenService.getAllProductsForUser(userService.getCurrentUserId());
		return recipeService.getRecipesForUserProducts(kitchenProducts);
	}
	
	
	

}
