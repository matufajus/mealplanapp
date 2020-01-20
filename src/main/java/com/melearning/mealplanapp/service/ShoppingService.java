package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

public interface ShoppingService {

	public void updateShoppingItem(List<Meal> meals, String name);

	public void addMealIngredientsToShoppingList(Meal meal);

	public List<ShoppingItem> getShoppingListForMeals(List<Meal> meals);

	

}
