package com.melearning.mealplanapp.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.MealRepository;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;

@Service
public class MealServiceImpl implements MealService{
	
	@Autowired
	MealRepository mealRepository;

	@Override
	public List<Meal> getAllUserMeals(long userId) {
		return mealRepository.findAllByUserId(userId);
	}
	
	@Override
	public List<LocalDate> extractDatesFromMeals(List<Meal> meals){
		
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (Meal meal : meals) {
			LocalDate date = meal.getDate();
			if (!dates.contains(date))
				dates.add(date);
		}
		
		Collections.sort(dates);
		
		return dates;
	}
	
	@Override
	public void saveMeal(Meal meal) {
		mealRepository.save(meal);
	}

	@Override
	public List<LocalDate> getDatesForMealPlan(int days) {
		LocalDate startDate = LocalDate.now();
		return getSpecificNumberOfDatesFrom(startDate, days);
	}
	
	public static List<LocalDate> getSpecificNumberOfDatesFrom(LocalDate startDate, int days) { 		   
	    return IntStream.iterate(0, i -> i + 1)
	      .limit(days)
	      .mapToObj(i -> startDate.plusDays(i))
	      .collect(Collectors.toList()); 
	}
	
	@Override
	public List<Meal> getUserMealsFromTodayUntil(long userId, int planDays) {
		return mealRepository.findByUserIdAndDateAfterAndDateBefore(userId, LocalDate.now().minusDays(1), LocalDate.now().plusDays(planDays));
	}

	@Override
	public void deleteMeal(int mealId) {
		mealRepository.deleteById(mealId);
	}

}
