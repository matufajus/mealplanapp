package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.List;

import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

public interface ShoppingService {

	public void addMealIngredientsToShoppingList(User user, List<Ingredient> ingredients, LocalDate date);

	public List<ShoppingItem> getShoppingListFromToday(long userId);

	public void updateShoppingItem(long userId, String name);

	

}
