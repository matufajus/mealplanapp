package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		return shoppingItems;
	}	

//	private List<ShoppingItem> removeDuplicatesByName(List<ShoppingItem> shoppingItems) {	
//		for(int i = shoppingItems.size()-1; i >= 0; i--) {
//			if (i != 0) {
//				ShoppingItem item1 = shoppingItems.get(i);
//				ShoppingItem item2 = shoppingItems.get(i-1);
//				 if (item1.getName().equals(item2.getName())) {
//					if ((item1.getUnit().equals(item2.getUnit())) && (item1.isDone() == (item2.isDone()))) {
//						item2.setAmmount(item1.getAmmount() + item2.getAmmount());
//						shoppingItems.remove(i);
//					}
//				}	
//			}
//		}	
//		return shoppingItems;		
//	}



	@Override
	public void addMealIngredientsToShoppingList(Meal meal) {
		for (Ingredient ingredient : meal.getRecipe().getIngredients()) {
			ShoppingItem newShoppingItem = new ShoppingItem(0, meal, ingredient.getName(), ingredient.getAmmount() * meal.getServings(), ingredient.getUnit(), false);
			shoppingRepository.save(newShoppingItem);
		}
		
	}



	@Override
	public void updateShoppingItems(List<Integer> ids) {
		for (Integer id : ids) {
			Optional<ShoppingItem> result = shoppingRepository.findById(id);
			if (result.isPresent()) {
				ShoppingItem shoppingItem = result.get();
				if (!shoppingItem.isDone())
					shoppingItem.setDone(true);
				else shoppingItem.setDone(false);
			shoppingRepository.save(shoppingItem);
			}
		}	
	}

	@Override
	public void removeMealIngredientsFromShoppingList(int mealId) {
		shoppingRepository.deleteByMealId(mealId);
		
	}
	
	
}
