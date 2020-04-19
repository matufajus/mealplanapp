package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.User;

public interface PlanService {

	Plan getCurrentPlan(User user);

	void save(Plan plan);

	Plan getPlanById(int id);

	void saveMeal(Meal meal);

	void deleteMeal(int mealId);

	Plan getPlanByMealId(int mealId);

	List<Plan> getAllUserPlans(long userId);

	List<Plan> getAllUserPlansFrom(long userId, LocalDate date);

	List<Plan> getAllUserPlansUntil(long userId, LocalDate date);
}
