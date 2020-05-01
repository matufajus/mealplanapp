package com.melearning.mealplanapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe extends Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "recipe_id", nullable = false)
	private List<Preparation> preparations;

	@ElementCollection(targetClass = MealType.class)
	@JoinTable(name = "recipe_meal_Type", joinColumns = @JoinColumn(name = "recipe_id"))
	@Column(name = "meal_type")
	@Enumerated(EnumType.ORDINAL)
	private List<MealType> mealTypes;

	@Column(name = "image")
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;

	// requested to be made public so everyone could see
	@Column(name = "shared")
	private boolean shared;

	// approved by admin
	@Column(name = "inspected")
	private boolean inspected;

	// is public
	@Column(name = "published")
	private boolean published;

	@Column(name = "author")
	private String author;

	@Column(name = "servings")
	private int servings;

	public Recipe(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public Recipe(Recipe recipe) {
		this.title = recipe.title;
		this.description = recipe.description;
		cloneIngredients(recipe.getIngredients());
		clonePreparations(recipe.getPreparations());
		this.mealTypes = new ArrayList<MealType>(recipe.mealTypes);
		this.image = recipe.image;
		this.owner = recipe.owner;
		this.shared = recipe.shared;
		this.servings = recipe.servings;
		this.inspected = recipe.inspected;
		this.published = recipe.published;
		this.author = recipe.author;
	}

	private void cloneIngredients(List<Ingredient> ingredients) {
		List<Ingredient> copyOfIngredients = new ArrayList<Ingredient>();
		for (Ingredient ingredient : ingredients) {
			copyOfIngredients.add(new Ingredient(ingredient));
		}
		this.setIngredients(copyOfIngredients);
	}

	private void clonePreparations(List<Preparation> preparations) {
		List<Preparation> copyOfPreparations = new ArrayList<Preparation>();
		for (Preparation preparation : preparations) {
			copyOfPreparations.add(new Preparation(preparation));
		}
		this.setPreparations(copyOfPreparations);
	}

}
