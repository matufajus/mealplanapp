package com.melearning.mealplanapp.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
class MealDishId implements Serializable {

	@Column(name ="meal_id")
	private int mealId;

	@Column(name="dish_id")
	private int dishId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MealDishId that = (MealDishId) o;
		return Objects.equals(mealId, that.mealId) && Objects.equals(dishId, that.dishId);

	}

	@Override
	public int hashCode() {
		return Objects.hash(mealId, dishId);
	}
	
	public MealDishId() {
	}
	
	public MealDishId(int mealId, int dishId) {
		this.mealId = mealId;
		this.dishId = dishId;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
}