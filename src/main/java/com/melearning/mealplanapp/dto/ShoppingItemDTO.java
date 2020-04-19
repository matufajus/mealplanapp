package com.melearning.mealplanapp.dto;

import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.enumeration.FoodType;

public class ShoppingItemDTO {
	
	private ShoppingItem shoppingItem;
	
	private FoodType foodType;

	public ShoppingItemDTO(ShoppingItem shoppingItem, FoodType foodType) {
		this.shoppingItem = shoppingItem;
		this.foodType = foodType;
	}

	public ShoppingItem getShoppingItem() {
		return shoppingItem;
	}

	public void setShoppingItem(ShoppingItem shoppingItem) {
		this.shoppingItem = shoppingItem;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

}
