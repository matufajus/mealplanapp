package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.MealType;

public interface PlanService {

	Plan getCurrentPlan(User user);

	void save(Plan plan);

	Plan getPlanById(int id);
	
	Meal getMeal(int id);
	
	Meal getMeal(int planId, LocalDate mealDate, MealType type);

	Plan getPlanByMealId(int mealId);

	List<Plan> getAllUserPlans(long userId);

	List<Plan> getAllUserPlansFrom(long userId, LocalDate date);

	List<Plan> getAllUserPlansUntil(long userId, LocalDate date);

	public void updateShoppingItems(int planId, String ingredientName, boolean isDone);

	public Map<String, List<ShoppingItemDTO>> getPreparedShoppingList(int planId);

	void addRecipeToMeal(Meal meal, Recipe recipe);

	void removeRecipeFromMeal(Meal meal, Recipe recipe);

	

}
