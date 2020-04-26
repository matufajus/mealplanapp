package com.melearning.mealplanapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;
import com.melearning.mealplanapp.service.FoodProductService;

@Controller
@RequestMapping("/foodProduct")
public class FoodProductController {
	
	@Autowired
	FoodProductService foodProductService;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/add")
	public void addNewFoodProduct() {
		Nutrition nutrition = new Nutrition(100, 10, 5, 20);
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setName("Bananas");
		foodProduct.setFoodType(FoodType.FRUIT);
		foodProduct.setUnitType(UnitType.WHOLE);
		foodProduct.setNutrition(nutrition);
		foodProductService.addFoodProduct(foodProduct);
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
