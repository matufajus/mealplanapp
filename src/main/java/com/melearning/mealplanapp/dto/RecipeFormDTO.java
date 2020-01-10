package com.melearning.mealplanapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Preparation;

public class RecipeFormDTO {
	
	private int id;
	
	@NotNull(message = "The title is required")
	@Size(min = 1, message = "The title is required")
	private String title;
	
	@NotNull(message = "At least one ingredient must be added")
	private List<Ingredient> ingredients;
	
	@NotNull(message = "At least one instruction must be added")
	private List<Preparation> preparations;
	
	private String image;
	
	private MultipartFile imageFile;


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
	
}
