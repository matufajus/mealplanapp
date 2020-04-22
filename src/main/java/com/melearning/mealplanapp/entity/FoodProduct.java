package com.melearning.mealplanapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;


@Entity
@Table(name="food_product")
public class FoodProduct {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="food_type")
	@Enumerated(EnumType.ORDINAL)
	private FoodType foodType;
	
	@Column(name="unit_type")
	@Enumerated(EnumType.ORDINAL)
	private UnitType unitType;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
	private Nutrition nutrition;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	
	public FoodProduct(String name, FoodType foodType, Nutrition nutrition) {
		this.name = name;
		this.foodType = foodType;
		this.nutrition = nutrition;
	}
	
	public FoodProduct() {
		
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}
	
	public Nutrition getNutrition() {
		return nutrition;
	}

	public void setNutrition(Nutrition nutrition) {
		this.nutrition = nutrition;
	}
	
}
