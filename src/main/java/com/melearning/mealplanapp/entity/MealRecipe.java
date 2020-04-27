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
@Table(name = "meal_recipe")
public class MealRecipe {

	@EmbeddedId
	private MealRecipeId id;

	@ManyToOne
	@MapsId("mealId")
	@JoinColumn(name = "meal_id")
	private Meal meal;

	@ManyToOne
	@MapsId("recipeId")
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@Column(name = "servings")
	private int servings;
	
	public MealRecipe() {
	}

	public MealRecipe(Meal meal, Recipe recipe, int servings) {
		this.id = new MealRecipeId(meal.getId(), recipe.getId());
		this.meal = meal;
		this.recipe = recipe;
		this.servings = servings;
	}
	
	public MealRecipe(Meal meal, Recipe recipe) {
		this.id = new MealRecipeId(meal.getId(), recipe.getId());
		this.meal = meal;
		this.recipe = recipe;
	}

	public MealRecipeId getId() {
		return id;
	}

	public void setId(MealRecipeId id) {
		this.id = id;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

}
