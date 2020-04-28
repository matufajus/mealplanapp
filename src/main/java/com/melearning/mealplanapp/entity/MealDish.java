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

import com.sun.xml.fastinfoset.algorithm.IntegerEncodingAlgorithm;

@Entity
@Table(name = "meal_dish")
public class MealDish implements Comparable<MealDish> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@Column(name = "servings")
	private int servings;

	public MealDish() {
	}

	public MealDish(Meal meal, Dish dish, int servings) {
		this.meal = meal;
		this.dish = dish;
		this.servings = servings;
	}

	public MealDish(Meal meal, Dish dish) {
		this.meal = meal;
		this.dish = dish;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int compareTo(MealDish o) {
		 return Integer.compare(this.getId(), o.getId());
	}

}
