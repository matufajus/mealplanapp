package com.melearning.mealplanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.melearning.mealplanapp.service.IngredientService;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
	
	@Autowired
	IngredientService ingredientService;
	


}
