package com.melearning.mealplanapp.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
class MealRecipeId implements Serializable {

	@Column(name ="meal_id")
	private int mealId;

	@Column(name="recipe_id")
	private int recipeId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MealRecipeId that = (MealRecipeId) o;
		return Objects.equals(mealId, that.mealId) && Objects.equals(recipeId, that.recipeId);

	}

	@Override
	public int hashCode() {
		return Objects.hash(mealId, recipeId);
	}
	
	public MealRecipeId() {
	}
	
	public MealRecipeId(int mealId, int recipeId) {
		this.mealId = mealId;
		this.recipeId = recipeId;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
}