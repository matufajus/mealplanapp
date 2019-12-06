package com.melearning.mealplanapp.demodata;

import java.util.List;

public class Record{
	
	private int number;
	
	private String title;
	
	private String ammountUnits;
	
	private String ingredient;
	
	private String preparation;
	
	private String image;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmmountUnits() {
		return ammountUnits;
	}

	public void setAmmountUnits(String ammountUnits) {
		this.ammountUnits = ammountUnits;
	}

	public String getIngredients() {
		return ingredient;
	}

	public void setIngredients(String ingredients) {
		this.ingredient = ingredients;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Record [number=" + number + ", title=" + title + ", ammountUnits=" + ammountUnits + ", ingredients="
				+ ingredient + ", preparation=" + preparation + ", image=" + image + "]";
	}
	
	
}
