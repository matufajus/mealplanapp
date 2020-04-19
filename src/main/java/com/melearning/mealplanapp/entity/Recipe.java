package com.melearning.mealplanapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.melearning.mealplanapp.enumeration.MealType;

@Entity
@Table(name = "recipe")
public class Recipe {

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
	private List<Ingredient> ingredients;

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

	@ManyToOne
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

	public Recipe() {
	}
	
	public Recipe(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public Recipe(int id, String title, String description, List<Ingredient> ingredients,
			List<Preparation> preparations, List<MealType> mealTypes, String image, User owner, boolean shared,
			boolean inspected, boolean published, String author) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
		this.preparations = preparations;
		this.mealTypes = mealTypes;
		this.image = image;
		this.owner = owner;
		this.shared = shared;
		this.inspected = inspected;
		this.published = published;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Preparation> getPreparations() {
		return preparations;
	}

	public void setPreparations(List<Preparation> preparations) {
		this.preparations = preparations;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<MealType> getMealTypes() {
		return mealTypes;
	}

	public void setMealTypes(List<MealType> mealTypes) {
		this.mealTypes = mealTypes;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public boolean isInspected() {
		return inspected;
	}

	public void setInspected(boolean inspected) {
		this.inspected = inspected;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

}
