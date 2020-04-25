package com.melearning.mealplanapp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.MealType;

public class RecipeFormDTO {
	
	private int id;
	
	@NotNull(message = "Įveskite recepto pavadinimą")
	@Size(min = 1, message = "Įveskite recepto pavadinimą")
	private String title;
	
	@NotNull(message = "Recepte turi būti bent vienas ingredientas")
	private List<IngredientDTO> ingredients;
	
	@NotNull(message = "Recepte turi būti paruošimo instrukcija")
	private List<Preparation> preparations;
	
	@NotNull(message = "Pasirinkite bent vieną patiekalo tipą")
	@Size(min = 1, message = "Pasirinkite bent vieną patiekalo tipą")
	private List<MealType> mealTypes;
	
	private String image;
	
	private MultipartFile imageFile;
	
	private String owner;
	
	private String author;
	
	private String description;
	
	private boolean shared;
	
	private boolean inspected;
	
	private boolean published;
	
	private int servings;
	
	private float calories;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDTO> ingredients) {
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

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MealType> getMealTypes() {
		return mealTypes;
	}

	public void setMealTypes(List<MealType> mealTypes) {
		this.mealTypes = mealTypes;
	}

	public RecipeFormDTO() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isInspected() {
		return inspected;
	}

	public void setInspected(boolean inspected) {
		this.inspected = inspected;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public float getCalories() {
		return calories;
	}

	public void setCalories(float calories) {
		this.calories = calories;
	}
	
	
	
}
