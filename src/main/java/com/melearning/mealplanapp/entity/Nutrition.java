package com.melearning.mealplanapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nutrition")
public class Nutrition {

	// All nutrition is for 100g of product

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

//	g/cm^3=g/ml 
	@Column(name = "density")
	private float density;

	@Column(name = "kcal")
	private float kcal;

	@Column(name = "protein")
	private float protein;

	@Column(name = "carbs")
	private float carbs;

	@Column(name = "fat")
	private float fat;

	public Nutrition(float kcal, float protein, float carbs, float fat) {
		this.kcal = kcal;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
	}

	public Nutrition getNutritionPerUnitType(UnitType unitType) {
		float conversionCoeff = 1;
		switch (unitType) {
		case GRAM:
			conversionCoeff = conversionCoeff / 100;
			break;
		case KILOGRAM:
			conversionCoeff = (conversionCoeff / 100) * 1000;
			break;
		case MILILITRE:
			conversionCoeff = (conversionCoeff / 100) * density;
			break;
		case LITRE:
			conversionCoeff = (conversionCoeff / 100) * density * 1000;
			break;
		case CUP:
			conversionCoeff = (conversionCoeff / 100) * density * 250;
			break;
		case TABLE_SPOON:
			conversionCoeff = (conversionCoeff / 100) * density * 15;
			break;
		case TEA_SPOON:
			conversionCoeff = (conversionCoeff / 100) * density * 5;
			break;
		default:
			break;
		}
		Nutrition nutrition = new Nutrition();
		nutrition.setKcal(this.getKcal() * conversionCoeff);
		nutrition.setProtein(this.getProtein() * conversionCoeff);
		nutrition.setCarbs(this.getCarbs() * conversionCoeff);
		nutrition.setFat(this.getFat() * conversionCoeff);
		return nutrition;
	}

}
