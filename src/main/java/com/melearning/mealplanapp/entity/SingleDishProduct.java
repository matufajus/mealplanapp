package com.melearning.mealplanapp.entity;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="single_dish_product")
public class SingleDishProduct extends Dish{
	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "food_product_id")
	private FoodProduct foodProduct;
	
	@Column(name="ammount")
	private float ammount;
	
	@Transient
	private final int servings = 1;
	
	@Override
	public float getCalories() {
		return foodProduct.getNutrition().getKcal() * ammount;
	}

	@Override
	public String getTitle() {
		return foodProduct.getName();
	}

	@Override
	public List<Ingredient> getIngredients() {
		Ingredient ingredient = new Ingredient(ammount, foodProduct);
		return Collections.singletonList(ingredient);
	}

	@Override
	public int getServings() {
		return servings;
	}

}
