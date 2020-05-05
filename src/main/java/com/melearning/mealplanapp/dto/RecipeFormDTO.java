package com.melearning.mealplanapp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Nutrition;
import com.melearning.mealplanapp.entity.Preparation;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.MealType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

	private Nutrition nutritionForDish;

}
