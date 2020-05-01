package com.melearning.mealplanapp.dto;

import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDTO {

	private int id;

	private float ammount;
	
	private UnitType unitType;

	private FoodProduct foodProduct;

	private int foodProductId;

}
