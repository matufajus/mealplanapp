package com.melearning.mealplanapp.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.melearning.mealplanapp.entity.Meal;

public interface MealService {
	
	public List<Meal> getAllUserMeals(long userId);
	
	public List<Date> extractDatesFromMeals(List<Meal> meals);
	
	public void saveMeal(Meal meal);

}
