package com.melearning.mealplanapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="dish")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Dish {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "dish_id", nullable = false)
	private List<Ingredient> ingredients;
	
	public float getCalories() {
		float calories = 0;
		for (Ingredient ingredient : ingredients) {
			calories = calories + ingredient.getCalories();
		}
		return calories;
	}
	
	public abstract String getTitle();
	
	public abstract int getServings();
	
}
