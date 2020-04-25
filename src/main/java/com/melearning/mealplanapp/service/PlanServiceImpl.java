package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melearning.mealplanapp.dao.FoodProductRepository;
import com.melearning.mealplanapp.dao.MealRepository;
import com.melearning.mealplanapp.dao.PlanRepository;
import com.melearning.mealplanapp.dao.ShoppingRepository;
import com.melearning.mealplanapp.dto.ShoppingItemDTO;
import com.melearning.mealplanapp.entity.Ingredient;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.Recipe;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.MealType;
import com.melearning.mealplanapp.exception.DuplicateRecipeInMealException;
import com.melearning.mealplanapp.exception.OverlappingPlanDatesExceptions;

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	MealRepository mealRepository;
	
	@Autowired
	ShoppingRepository shoppingRepository;
	
	@Autowired
	FoodProductRepository foodProductRepository;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Plan getCurrentPlan(User user) {
		return planRepository.findByUserIdAndStartDateAfterAndEndDateBefore(user, LocalDate.now());
	}

	@Override
	public void save(Plan plan) {
		if (checkIfDatesAreAvailable(plan)) {
			planRepository.save(plan);
		}else {
			throw new OverlappingPlanDatesExceptions(plan.getStartDate(), plan.getEndDate());
		}
	
	}

	private boolean checkIfDatesAreAvailable(Plan plan) {
		List<Plan> upcomingPlans = getAllUserPlansFrom(plan.getUser().getId(), LocalDate.now());
		List<LocalDate> unavailableDates = upcomingPlans.stream().map(p -> p.getDates()).collect(Collectors.toList())
				.stream().flatMap(List::stream).collect(Collectors.toList());
		if (Collections.disjoint(unavailableDates, plan.getDates())) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Plan> getAllUserPlans(long userId) {
		return planRepository.findAllByUserId(userId);
	}

	@Override
	public Plan getPlanById(int id) {
		return planRepository.findById(id).get();
	}
	
	@Transactional
	@Override
	public void addRecipeToMeal(Meal meal, Recipe recipe) {
		if (meal.getRecipes().contains(recipe)) {
			throw new DuplicateRecipeInMealException(recipe, meal);
		}
		meal.addRecipe(recipe);
		mealRepository.save(meal);
		addRecipeIngredientsToShoppingList(meal, recipe);
	}

	@Transactional
	@Override
	public void removeRecipeFromMeal(Meal meal, Recipe recipe) {
		meal.removeRecipe(recipe);
		removeRecipeIngredientsFromShoppingList(meal, recipe);
		mealRepository.save(meal);
	}

	@Override
	public Plan getPlanByMealId(int mealId) {
		Meal meal = mealRepository.findById(mealId).get();
		return meal.getPlan();
	}
	
	@Override
	public List<Plan> getAllUserPlansFrom(long userId, LocalDate date){
		return planRepository.findAllByUserIdAndEndDateGreaterThanEqual(userId, date);
	}
	
	@Override
	public List<Plan> getAllUserPlansUntil(long userId, LocalDate date){
		return planRepository.findAllByUserIdAndEndDateLessThan(userId, date);
	}

	@Override
	public Meal getMeal(int id) {
		return mealRepository.findById(id).get();
	}
	
	@Override
	public Meal getMeal(int planId, LocalDate date, MealType mealType) {
		return mealRepository.findByPlanIdAndDateAndMealType(planId, date, mealType);
	}
	
	@Override
	public Map<String, List<ShoppingItemDTO>> getPreparedShoppingList(int planId){
		List<ShoppingItem> shoppingItems = shoppingRepository.findAllByPlanId(planId);
		
		//HashMap for containing shopping items grouped by foodType
		Map<String, List<ShoppingItemDTO>> groupedShoppingList = new LinkedHashMap<String, List<ShoppingItemDTO>>();
		
		//creating empty lists for each of foodtypes
		for (FoodType foodType : FoodType.values()) {
			groupedShoppingList.put(foodType.label, new ArrayList<ShoppingItemDTO>());
		}
		
		//convert shoppingItems to shoppingItemsDTO, merge similar items by name and group by foodType
		for (ShoppingItem item : shoppingItems) {
			String name = item.getFoodProduct().getName();
			float ammount = item.getAmmount();
			String units = item.getFoodProduct().getUnitType().getLabel();
			boolean isDone = item.isDone();
			FoodType foodType = item.getFoodProduct().getFoodType();
			
			//getting list for specific foodType
			List<ShoppingItemDTO> shoppingItemsDTO = groupedShoppingList.get(foodType.label);
			
			//getting object with the similar name and same state
			ShoppingItemDTO itemDTO = shoppingItemsDTO.stream().filter(i->(i.getName().equals(name) && (i.isDone() == isDone)))
					.findAny().orElse(null);
			
			//if similar object exists add ammounts, if not add new object
			if (itemDTO!= null)
				itemDTO.setAmmount(itemDTO.getAmmount()+ammount);
			else
				shoppingItemsDTO.add(new ShoppingItemDTO(name, ammount, units, isDone));
			
			//setting modified list for same specific foodType
			groupedShoppingList.put(foodType.label, shoppingItemsDTO);		
		}
 
		return groupedShoppingList;
	}

	@Override
	public void updateShoppingItems(int planId, String foodProductName, boolean isDone) {
		List<ShoppingItem> shoppingItems = shoppingRepository.findAllByPlanIdAndFoodProductNameAndDone(planId, foodProductName, isDone);
		for (ShoppingItem shoppingItem : shoppingItems) {
			if (!shoppingItem.isDone())
				shoppingItem.setDone(true);
			else shoppingItem.setDone(false);
		}
		shoppingRepository.saveAll(shoppingItems);
	}
	
	public void addRecipeIngredientsToShoppingList(Meal meal, Recipe recipe) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			float ammount = (ingredient.getAmmount() / recipe.getServings()) * meal.getServings();
			ShoppingItem shoppingItem = new ShoppingItem(0, ingredient.getFoodProduct(), ammount, false, meal.getPlan());
			shoppingRepository.save(shoppingItem);
		}
	}

	public void removeRecipeIngredientsFromShoppingList(Meal meal, Recipe recipe) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			shoppingRepository.deleteFirstByPlanIdAndFoodProductId(meal.getPlan().getId(), ingredient.getFoodProduct().getId());
		}
	}

}
