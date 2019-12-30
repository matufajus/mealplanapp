package com.melearning.mealplanapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="preparation")
public class Preparation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@Column(name="description")
	private String description;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Preparation(int id, Recipe recipe, String description) {
		this.id = id;
		this.recipe = recipe;
		this.description = description;
	}

	public Preparation() {
	}
	

}
