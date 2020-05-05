package com.melearning.mealplanapp.service;

import java.util.List;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.enumeration.FoodType;

public interface FoodProductService {
	
	public List<FoodProduct> getFoodProductsByType(FoodType foodType);

	public List<FoodProduct> getFoodProducts();

	public FoodProduct getFoodProduct(int foodProductId);
	
	public FoodProduct getFoodProduct(String name);
	
	public void addFoodProduct(FoodProduct foodProduct);

	public void deleteFoodProduct(int id);

}
