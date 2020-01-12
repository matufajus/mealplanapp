package com.melearning.mealplanapp.entity;

public enum MealType {
	BREAKFAST("Breakfast"),
	LUNCH("Lunch"),
	DINNER("Dinner"),
	SNACKS("Snacks");
	
	public final String label;
	
	private MealType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
