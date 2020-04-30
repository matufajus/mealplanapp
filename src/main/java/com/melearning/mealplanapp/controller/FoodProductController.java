package com.melearning.mealplanapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;
import com.melearning.mealplanapp.service.FoodProductService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/foodProduct")
public class FoodProductController {
	
	@Autowired
	FoodProductService foodProductService;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/list")
	public String displayAllFoodProducts(Model model) {
		List<FoodProduct> foodProducts = foodProductService.getFoodProducts();
		model.addAttribute("foodProducts", foodProducts);
		model.addAttribute("foodProduct", new FoodProduct());
		return "food-products";
	}
	
	@PostMapping("/addFoodProduct")
	public String addFoodProduct(@ModelAttribute("foodProduct") FoodProduct foodProduct, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			//TODO:add error message
			return "food-products";
		}	
		foodProductService.addFoodProduct(foodProduct);
		return "redirect:/foodProduct/list";
	}
	
	@GetMapping("/deleteFoodProduct")
	public String deleteFoodProduct(@RequestParam(name="id") int id) {
		foodProductService.deleteFoodProduct(id);
		return "redirect:/foodProduct/list";
	}
	
	@GetMapping("/getAll")
	public @ResponseBody List<FoodProduct> getFoodProducts() {
		return foodProductService.getFoodProducts();
	}
	
	@GetMapping("/get")
	public @ResponseBody FoodProduct getFoodProduct(@RequestParam(name="productId") int id) {
		return foodProductService.getFoodProduct(id);
	}
	
	

}
