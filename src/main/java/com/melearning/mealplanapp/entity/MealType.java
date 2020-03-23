package com.melearning.mealplanapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
	
	public String getName() {
		return this.name();
	}

}
