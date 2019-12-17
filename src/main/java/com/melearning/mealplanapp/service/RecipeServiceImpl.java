package com.melearning.mealplanapp.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melearning.mealplanapp.dao.RecipeRepository;
import com.melearning.mealplanapp.dto.RecipeFormDTO;
import com.melearning.mealplanapp.entity.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	RecipeRepository recipeRepository;

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public List<Recipe> findAll() {
		// TODO Auto-generated method stub
		return recipeRepository.findAll();
	}

	@Override
	public Recipe findById(int id) {
		// TODO Auto-generated method stub
		Optional<Recipe> result = recipeRepository.findById(id);
		Recipe recipe = null;
		if (result.isPresent()) {
			recipe= result.get();
		}else {
			throw new RuntimeException("Did not find recipe id - " + id);
		}
		return recipe;
	}

	@Override
	public void save(Recipe recipe) {
		// TODO Auto-generated method stub
		recipeRepository.save(recipe);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		recipeRepository.deleteById(id);
	}
	
	@Override
	public Recipe findByTitle(String title) {
		return recipeRepository.findByTitle(title);
	}

}
