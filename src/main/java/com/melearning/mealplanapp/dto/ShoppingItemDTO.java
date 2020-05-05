package com.melearning.mealplanapp.dto;

import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingItemDTO {

	private String name;

	private float ammount;

	private UnitType unitType;

	private boolean isDone;

	public ShoppingItemDTO(String name, float ammount, UnitType unitType, boolean isDone) {
		this.name = name;
		this.ammount = ammount;
		this.unitType = unitType;
		this.isDone = isDone;
	}

}
