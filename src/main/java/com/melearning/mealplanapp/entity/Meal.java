package com.melearning.mealplanapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
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
@Table(name = "meal")
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<MealDish> mealDishes;

	@Column(name = "meal_type")
	@Enumerated(EnumType.ORDINAL)
	private MealType mealType;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", nullable = false)
	@JsonIgnore
	private Plan plan;

	public Nutrition getNutritionForMeal() {
		float kcal = 0;
		float carbs = 0;
		float fat = 0;
		float protein = 0;
		for (MealDish mealDish : mealDishes) {
			Nutrition nutrition = mealDish.getDish().getNutritionForDish();
			kcal = kcal + (nutrition.getKcal() / mealDish.getDish().getServings()) * mealDish.getServings();
			carbs = carbs + (nutrition.getCarbs() / mealDish.getDish().getServings()) * mealDish.getServings();
			fat = fat + (nutrition.getFat() / mealDish.getDish().getServings()) * mealDish.getServings();
			protein = protein + (nutrition.getProtein() / mealDish.getDish().getServings()) * mealDish.getServings();
		}
		return new Nutrition(kcal, protein, carbs, fat);
	}

	public void addDish(Dish dish, int servings) {
		MealDish mealDish = new MealDish(this, dish, servings);
		mealDishes.add(mealDish);
	}

	public void removeDish(Dish dish) {
		MealDish mealDish = findMealDish(dish);
		mealDishes.remove(mealDish);
	}

	public MealDish findMealDish(Dish dish) {
		for (MealDish mealDish : mealDishes) {
			if (mealDish.getDish().equals(dish)) {
				return mealDish;
			}
		}
		return null;
	}

	public boolean hasDish(Dish dish) {
		if (findMealDish(dish) != null)
			return true;
		else
			return false;
	}
}
