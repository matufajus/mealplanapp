package com.melearning.mealplanapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.enumeration.MealType;

public interface MealRepository extends JpaRepository<Meal, Integer>{

	Meal findByPlanIdAndDateAndMealType(int planId, LocalDate date, MealType mealType);

}
