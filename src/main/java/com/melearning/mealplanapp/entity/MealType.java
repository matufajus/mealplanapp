package com.melearning.mealplanapp.entity;

public enum MealType {
	BREAKFAST("Pusryčiai"),
	LUNCH("Pietūs"),
	DINNER("Vakarienė"),
	SNACKS("Užkandžiai");
	
	public final String label;
	
	private MealType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
