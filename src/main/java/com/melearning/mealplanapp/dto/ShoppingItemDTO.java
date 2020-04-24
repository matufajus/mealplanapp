package com.melearning.mealplanapp.dto;

import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

public class ShoppingItemDTO {

	private String name;

	private float ammount;

	private String units;

	private boolean isDone;

	public ShoppingItemDTO(String name, float ammount, String units, boolean isDone) {
		this.name = name;
		this.ammount = ammount;
		this.setUnits(units);
		this.isDone = isDone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
