package com.melearning.mealplanapp.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="shopping_item")
public class ShoppingItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "ammount")
	private String ammount;
	
	@Column(name = "is_done")
	private boolean done;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmmount() {
		return ammount;
	}

	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


	public ShoppingItem(int id, Meal meal, String name, String ammount, boolean done) {
		this.id = id;
		this.meal = meal;
		this.name = name;
		this.ammount = ammount;
		this.done = done;
	}

	public ShoppingItem() {
	
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	
	
	
	
}
