package com.melearning.mealplanapp.entity;

public enum FoodType {
	VEGETABLE("Vegetable"),
	FRUIT("Fruit"),
	GRAINS_BEANS_NUTS("Grains, beans or nuts"),
	MEAT_POULTRY("Meat or poultry"),
	FISH_SEAFOOD("Fish or Seafood"),
	DAIRY("Dairy"),
	OTHER("Other");
	
	public final String label;
	
	private FoodType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
