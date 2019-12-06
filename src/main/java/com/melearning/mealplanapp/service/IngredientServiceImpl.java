package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.IngredientRepository;
import com.melearning.mealplanapp.entity.Ingredient;

@Service
public class IngredientServiceImpl implements IngredientService {
	
	@Autowired
	IngredientRepository ingredientRepository;
	

}
