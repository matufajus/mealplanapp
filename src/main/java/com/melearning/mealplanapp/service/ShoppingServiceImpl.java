package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.ShoppingRepository;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

@Service
public class ShoppingServiceImpl implements ShoppingService{
	
	@Autowired
	ShoppingRepository shoppingRepository;
	
	@Override
	public List<ShoppingItem> getShoppingListForMeals(List<Meal> meals){
		List<ShoppingItem> shoppingItems = shoppingRepository.findByMealInOrderByName(meals);
		return removeDuplicatesByName(shoppingItems);
	}	

	private List<ShoppingItem> removeDuplicatesByName(List<ShoppingItem> shoppingItems) {	
		for(int i = shoppingItems.size()-1; i >= 0; i--) {
			if ((i != 0) && (shoppingItems.get(i).getName().equals(shoppingItems.get(i-1).getName()))) {
				shoppingItems.get(i-1).setAmmount(shoppingItems.get(i).getAmmount() + " + " + shoppingItems.get(i-1).getAmmount());
				shoppingItems.remove(i);
			}
		}	
		return shoppingItems;		
	}



	@Override
	public void addMealIngredientsToShoppingList(Meal meal) {
		for (Ingredient ingredient : meal.getRecipe().getIngredients()) {
			ShoppingItem newShoppingItem = new ShoppingItem(0, meal, ingredient.getName(), ingredient.getAmmount(), false);
			shoppingRepository.save(newShoppingItem);
		}
		
	}



	@Override
	public void updateShoppingItem(List<Meal> meals, String name) {
		List<ShoppingItem> items = shoppingRepository.findByNameAndMealIn(name, meals);
		for (ShoppingItem shoppingItem : items) {
			if (!shoppingItem.isDone())
				shoppingItem.setDone(true);
			else shoppingItem.setDone(false);
		}
		shoppingRepository.saveAll(items);
		
	}
	
	
}
