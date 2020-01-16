package com.melearning.mealplanapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.MealRepository;
import com.melearning.mealplanapp.entity.Meal;

@Service
public class MealServiceImpl implements MealService{
	
	@Autowired
	MealRepository mealRepository;

	@Override
	public List<Meal> getAllUserMeals(long userId) {
		return mealRepository.findAllByUserId(userId);
	}
	
	public List<Date> extractDatesFromMeals(List<Meal> meals){
		
		List<Date> dates = new ArrayList<Date>();
		for (Meal meal : meals) {
			Date date = meal.getDate();
			if (!dates.contains(date))
				dates.add(date);
		}
		
		Collections.sort(dates);
		
		return dates;
	}
	
	public void saveMeal(Meal meal) {
		mealRepository.save(meal);
	}
	
	
}
