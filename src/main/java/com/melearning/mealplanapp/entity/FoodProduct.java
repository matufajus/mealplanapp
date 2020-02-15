package com.melearning.mealplanapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="food_product")
public class FoodProduct {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="food_type")
	@Enumerated(EnumType.ORDINAL)
	private FoodType foodType;
	
	@Column(name="unit_type")
	@Enumerated(EnumType.ORDINAL)
	private UnitType unitType;
	
	@Column(name="kcal")
	private int kcal;

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

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}

	public FoodProduct(int id, String name, FoodType foodType, int kcal) {
		super();
		this.id = id;
		this.name = name;
		this.foodType = foodType;
		this.kcal = kcal;
	}
	
	public FoodProduct() {
		
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}
	
}
