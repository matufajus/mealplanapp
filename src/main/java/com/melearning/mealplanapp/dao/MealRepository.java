package com.melearning.mealplanapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer>{

}
