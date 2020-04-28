package com.melearning.mealplanapp.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "meal_dish")
public class MealDish {

	@EmbeddedId
	private MealDishId id;

	@ManyToOne
	@MapsId("mealId")
	@JoinColumn(name = "meal_id")
	private Meal meal;

	@ManyToOne
	@MapsId("dishId")
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@Column(name = "servings")
	private int servings;
	
	public MealDish() {
	}

	public MealDish(Meal meal, Dish dish, int servings) {
		this.id = new MealDishId(meal.getId(), dish.getId());
		this.meal = meal;
		this.dish = dish;
		this.servings = servings;
	}
	
	public MealDish(Meal meal, Dish dish) {
		this.id = new MealDishId(meal.getId(), dish.getId());
		this.meal = meal;
		this.dish = dish;
	}

	public MealDishId getId() {
		return id;
	}

	public void setId(MealDishId id) {
		this.id = id;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

}
