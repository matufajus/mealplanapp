package com.melearning.mealplanapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.FoodProductRepository;
import com.melearning.mealplanapp.dao.IngredientRepository;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.enumeration.FoodType;

@Service
public class FoodProductServiceImpl implements FoodProductService {

	@Autowired
	FoodProductRepository foodProductRepository;

	@Override
	public FoodProduct getFoodProduct(String name) {
		return foodProductRepository.findByName(name);
	}

	@Override
	public void addFoodProduct(FoodProduct foodProduct) {
		foodProductRepository.save(foodProduct);
	}

	@Override
	public List<FoodProduct> getFoodProductsByType(FoodType foodType) {
		return foodProductRepository.findByFoodType(foodType);
	}

	@Override
	public List<FoodProduct> getFoodProducts() {
		return foodProductRepository.findAll();
	}

	@Override
	public FoodProduct getFoodProduct(int foodProductId) {
		Optional<FoodProduct> result = foodProductRepository.findById(foodProductId);
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public void deleteFoodProduct(int id) {
		foodProductRepository.deleteById(id);
	}


}
