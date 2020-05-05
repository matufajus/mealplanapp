package com.melearning.mealplanapp.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
	
	public String getValue() {
		return this.name();
	}
	
	public String getLabel() {
		return label;
	}
}
