package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.Dish;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.MealDish;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.SingleDishProduct;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.enumeration.UnitType;

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

	public void updateShoppingItems(int planId, String ingredientName, boolean isDone, UnitType unitType);

	public Map<String, List<ShoppingItemDTO>> getPreparedShoppingList(int planId);

	void addDishToMeal(Meal meal, Dish dish, int servings);

	void removeDishFromMeal(Meal meal, Dish dish);

	void saveSingleDish(SingleDishProduct singleDish);

	SingleDishProduct getSingleDishProduct(int id);

	void deleteSingleDish(SingleDishProduct singleDishProduct);

	void copyPlanMealsToPlan(Plan planWithMeals, Plan newPlan);
	
}
