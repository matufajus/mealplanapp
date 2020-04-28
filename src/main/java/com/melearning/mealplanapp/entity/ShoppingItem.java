package com.melearning.mealplanapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "shopping_item")
public class ShoppingItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "foodProduct_id")
	private FoodProduct foodProduct;
	
	@JoinColumn(name = "ammount")
	private float ammount;

	@Column(name = "is_done")
	private boolean done;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public ShoppingItem() {

	}

	public ShoppingItem(int id, FoodProduct foodProduct, Dish dish, float ammount, boolean done, Plan plan) {
		this.id = id;
		this.foodProduct = foodProduct;
		this.dish = dish;
		this.ammount = ammount;
		this.done = done;
		this.plan = plan;
	}

	public FoodProduct getFoodProduct() {
		return foodProduct;
	}

	public void setFoodProduct(FoodProduct foodProduct) {
		this.foodProduct = foodProduct;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

}
