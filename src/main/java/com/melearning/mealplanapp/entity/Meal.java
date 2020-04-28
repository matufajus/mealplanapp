package com.melearning.mealplanapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.melearning.mealplanapp.enumeration.MealType;

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
	@JoinColumn(name="plan_id", nullable=false)
	@JsonIgnore
	private Plan plan;
	

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MealDish> getMealDishes() {
		return mealDishes;
	}

	public void setMealDishes(List<MealDish> mealDishes) {
		this.mealDishes = mealDishes;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Meal(int id, List<MealDish> mealDishes, MealType mealType, LocalDate date, Plan plan) {
		this.id = id;
		this.mealDishes = mealDishes;
		this.mealType = mealType;
		this.date = date;
		this.plan = plan;
	}
	
	public Meal() {
		
	}

	public float getCalories() {
		float calories = 0;
		for (MealDish mealDish : mealDishes) {
			calories = calories + mealDish.getDish().getCalories() * mealDish.getServings();
		}
		return calories;
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
		else return false;
	}
}
