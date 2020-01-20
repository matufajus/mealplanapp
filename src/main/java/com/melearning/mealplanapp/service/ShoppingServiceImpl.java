package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.ShoppingRepository;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

@Service
public class ShoppingServiceImpl implements ShoppingService{
	
	@Autowired
	ShoppingRepository shoppingRepository;
	
	@Override
	public List<ShoppingItem> getShoppingListFromToday(long userId){
		List<ShoppingItem> shoppingItems = shoppingRepository.findByUserIdAndDateAfterOrderByName(userId, LocalDate.now().minusDays(1));
		
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
	public void addMealIngredientsToShoppingList(User user, List<Ingredient> ingredients, LocalDate date) {
		for (Ingredient ingredient : ingredients) {
			ShoppingItem newShoppingItem = new ShoppingItem(0, user, ingredient.getName(), ingredient.getAmmount(), false, date);
			shoppingRepository.save(newShoppingItem);
		}
		
	}
	
	
}
