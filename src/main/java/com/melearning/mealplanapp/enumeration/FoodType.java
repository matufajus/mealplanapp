package com.melearning.mealplanapp.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FoodType {
	VEGETABLE("Daržovės", 0),
	FRUIT("Vaisiai", 1),
	GRAINS_BEANS_NUTS("Grūdai, pupelės, riešutai", 2),
	MEAT_POULTRY("Mėsa, paukštiena", 3),
	FISH_SEAFOOD("Žuvis, jūros gėrybės", 4),
	DAIRY("Pieno produktai", 5),
	OTHER("Kita", 6);
	
	public final String label;
	
	public final int position;

	
	private FoodType(String label, int position) {
		this.label = label;
		this.position = position;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getPosition() {
		return position;
	}
}
