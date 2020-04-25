package com.melearning.mealplanapp.entity;

import java.time.LocalDate;
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

	@ManyToMany
	@JoinTable(
	        name = "meal_recipe", 
	        joinColumns = { @JoinColumn(name = "meal_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "recipe_id") }
	    )
	@JsonIgnore
	private List<Recipe> recipes;
	
	@Column(name = "meal_type")
	@Enumerated(EnumType.ORDINAL)
	private MealType mealType;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "servings")
	private int servings;
	
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

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipe) {
		this.recipes = recipes;
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

	public Meal(int id, List<Recipe> recipes, MealType mealType, LocalDate date, int servings, Plan plan) {
		this.id = id;
		this.recipes = recipes;
		this.mealType = mealType;
		this.date = date;
		this.servings = servings;
		this.plan = plan;
	}
	
	public Meal() {
		
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}
	
	public float getCalories() {
		float calories = 0;
		for (Recipe recipe : recipes) {
			calories = calories + recipe.getCalories() * servings;
		}
		return calories;
	}
	
	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}
	
	public void removeRecipe(Recipe recipe) {
		recipes.remove(recipe);
	}

}
