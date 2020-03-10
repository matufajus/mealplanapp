package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.FoodProductRepository;
import com.melearning.mealplanapp.dao.ShoppingRepository;
import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.FoodType;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;

@Service
public class ShoppingServiceImpl implements ShoppingService{
	
	@Autowired
	ShoppingRepository shoppingRepository;
	
	@Autowired
	FoodProductRepository foodProductRepository;
	
	@Override
	public List<ShoppingItemDTO> getShoppingListForMeals(List<Meal> meals){
		List<ShoppingItem> shoppingItems = shoppingRepository.findByMealInOrderByName(meals);
		return convertShoppingListToDTO(shoppingItems);
	}	



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



	@Override
	public List<ShoppingItemDTO> convertShoppingListToDTO(List<ShoppingItem> shoppingItems) {
		List<FoodProduct> foodProducts = foodProductRepository.findAll();
		List<ShoppingItemDTO> shoppingItemsDTO = new ArrayList<ShoppingItemDTO>();
		for (ShoppingItem shoppingItem : shoppingItems) {
			FoodType foodType = FoodType.OTHER;
			for (FoodProduct foodProduct : foodProducts) {
				if(shoppingItem.getName().equalsIgnoreCase(foodProduct.getName())) {
					foodType = foodProduct.getFoodType();
				}
			}
			shoppingItemsDTO.add(new ShoppingItemDTO(shoppingItem, foodType));
		}
		return shoppingItemsDTO;
	}



	@Override
	public List<ShoppingItem> updateSimpleShoppingItems(List<ShoppingItem> shoppingList, List<Integer> ids) {
		for (Integer id : ids) {
			for (ShoppingItem shoppingItem: shoppingList) {
				if (shoppingItem.getId() == id) {
					if (!shoppingItem.isDone())
						shoppingItem.setDone(true);
					else shoppingItem.setDone(false);	
					}	
				}
			}	
		return shoppingList;
	}
	
	
}
