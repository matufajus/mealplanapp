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
	
	public SingleDishProduct() {
	}
	
	public SingleDishProduct(int id, FoodProduct foodProduct, float ammount) {
		this.id = id;
		this.foodProduct = foodProduct;
		this.ammount = ammount;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public FoodProduct getFoodProduct() {
		return foodProduct;
	}

	public void setFoodProduct(FoodProduct foodProduct) {
		this.foodProduct = foodProduct;
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}
	
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
