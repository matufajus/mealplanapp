package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

public interface ShoppingService {

	public void updateShoppingItems(List<Integer> ids);

	public void addMealIngredientsToShoppingList(Meal meal);

	public List<ShoppingItem> getShoppingListForMeals(List<Meal> meals);

	public void removeMealIngredientsFromShoppingList(int mealId);



}
