package com.melearning.mealplanapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.enumeration.UnitType;

public interface ShoppingRepository extends JpaRepository<ShoppingItem, Integer> {
	
//	List<ShoppingItem> findByMealInOrderByName(List<Meal> meals);
//	
//	List<ShoppingItem> findByNameAndMealIn(String name, List<Meal> meals);

	List<ShoppingItem> findAllByPlanId(int planId);

	void deleteByPlanIdAndIngredientId(int id, int id2);

	List<ShoppingItem> findAllByPlanIdAndIngredientFoodProductNameAndDoneAndIngredientUnitType(int planId,
			String foodProductName, boolean isDone, UnitType unitType);

}
