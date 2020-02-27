package com.melearning.mealplanapp.entity;

public enum FoodType {
	VEGETABLE("Daržovės"),
	FRUIT("Vaisiai"),
	GRAINS_BEANS_NUTS("Grūdai, pupelės, riešutai"),
	MEAT_POULTRY("Mėsa, paukštiena"),
	FISH_SEAFOOD("Žuvis, jūros gėrybės"),
	DAIRY("Pieno produktai"),
	OTHER("Kita");
	
	public final String label;

	
	private FoodType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
