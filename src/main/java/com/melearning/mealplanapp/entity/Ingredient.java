package com.melearning.mealplanapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ammount")
	private float ammount;
	
	@Column(name="unit_type")
	@Enumerated(EnumType.ORDINAL)
	private UnitType unitType;

	@ManyToOne
	@JoinColumn(name = "food_product_id")
	private FoodProduct foodProduct;

	public Ingredient(float ammount, UnitType unitType, FoodProduct foodProduct) {
		this.ammount = ammount;
		this.unitType = unitType;
		this.foodProduct = foodProduct;
	}

	public Ingredient(Ingredient ingredient) {
		this.ammount = ingredient.ammount;
		this.unitType = ingredient.unitType;
		this.foodProduct = ingredient.foodProduct;
	}

	public float getCalories() {
		return foodProduct.getNutrition().getKcal() * ammount;
	}

}
