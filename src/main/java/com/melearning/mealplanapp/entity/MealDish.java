package com.melearning.mealplanapp.entity;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;
import com.sun.xml.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meal_dish")
public class MealDish implements Comparable<MealDish> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "meal_id")
	@JsonIgnore
	private Meal meal;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	@JsonIgnore
	private Dish dish;

	@Column(name = "servings")
	private int servings;

	public MealDish(Meal meal, Dish dish, int servings) {
		this.meal = meal;
		this.dish = dish;
		this.servings = servings;
	}

	public MealDish(Meal meal, Dish dish) {
		this.meal = meal;
		this.dish = dish;
	}

	@Override
	public int compareTo(MealDish o) {
		return Integer.compare(this.getId(), o.getId());
	}
	
	public Nutrition getNutritionForMealDish() {
		Nutrition nutrition = getDish().getNutritionForDish();
		//ratio between servings for meal dish and recipe servings  
		float ratio =  (float)this.getServings() / (float)this.getDish().getServings();
		float kcal = nutrition.getKcal() * ratio;
		float carbs = nutrition.getCarbs() * ratio;
		float fat = nutrition.getFat() * ratio;
		float protein = nutrition.getProtein() * ratio;
		return new Nutrition(kcal, protein, carbs, fat);
	}

}
