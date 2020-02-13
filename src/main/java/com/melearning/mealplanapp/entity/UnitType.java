package com.melearning.mealplanapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UnitType {
	KILOGRAM("kg."),
	GRAM("g."),
	LITRE("l."),
	MILILITRE("ml."),
	TABLE_SPOON("v.š."),
	TEA_SPOON("a.š."),
	LITTLE("truputis"),
	SOME("šiek tiek"),
	LOT("daug"),
	UNIT("vnt.");
	
	public final String label;
	
	private UnitType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getName() {
		return this.name();
	}

}
