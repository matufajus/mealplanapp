package com.melearning.mealplanapp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.MealType;
import com.melearning.mealplanapp.entity.Preparation;

public class RecipeFormDTO {
	
	private int id;
	
	@NotNull(message = "Įveskite recepto pavadinimą")
	@Size(min = 1, message = "Įveskite recepto pavadinimą")
	private String title;
	
	@NotNull(message = "Recepte turi būti bent vienas ingredientas")
	private List<Ingredient> ingredients;
	
	@NotNull(message = "Recepte turi būti paruošimo instrukcija")
	private List<Preparation> preparations;
	
	@NotNull(message = "Pasirinkite bent vieną patiekalo tipą")
	@Size(min = 1, message = "Pasirinkite bent vieną patiekalo tipą")
	private List<MealType> mealTypes;
	
	private String image;
	
	private MultipartFile imageFile;
	
	private String description;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	
	
}
