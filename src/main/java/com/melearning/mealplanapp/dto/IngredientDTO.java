package com.melearning.mealplanapp.dto;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.enumeration.UnitType;

public class IngredientDTO {

	private int id;

	private float ammount;

	private FoodProduct foodProduct;

	private int foodProductId;
	
	public IngredientDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}

	public int getFoodProductId() {
		return foodProductId;
	}

	public void setFoodProductId(int foodProductId) {
		this.foodProductId = foodProductId;
	}

	public FoodProduct getFoodProduct() {
		return foodProduct;
	}

	public void setFoodProduct(FoodProduct foodProduct) {
		this.foodProduct = foodProduct;
	}

}
