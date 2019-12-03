package com.melearning.mealplanapp.service;

import java.util.List;

import com.melearning.mealplanapp.entity.Recipe;

public interface RecipeService {
	
	public List<Recipe> findAll();
	
	public Recipe findById(int id);
	
	public void save(Recipe recipe);
	
	public void deleteById(int id);

}
