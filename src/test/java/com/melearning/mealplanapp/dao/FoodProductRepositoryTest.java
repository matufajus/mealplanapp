package com.melearning.mealplanapp.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.enumeration.FoodType;

@DataJpaTest
class FoodProductRepositoryTest {
	
	@Autowired
	FoodProductRepository foodProductRep;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	void whenSaveNewFoodProduct_thenProductIdIsSet() {
		Nutrition nutrition = new Nutrition(100, 10, 5, 20);
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setName("Bananas");
		foodProduct.setNutrition(nutrition);
		foodProductRep.save(foodProduct);
		assertNotNull(foodProduct.getId());
		assertNotEquals(0, foodProduct.getId());
	}

}
