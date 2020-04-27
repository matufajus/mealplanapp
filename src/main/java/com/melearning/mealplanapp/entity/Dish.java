package com.melearning.mealplanapp.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="dish")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Dish {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	public abstract float getCalories();
	
	public abstract String getTitle();
	
	public abstract List<Ingredient> getIngredients();

}
