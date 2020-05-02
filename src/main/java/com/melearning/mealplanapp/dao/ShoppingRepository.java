package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.ShoppingItem;
import com.melearning.mealplanapp.enumeration.UnitType;

public interface ShoppingRepository extends JpaRepository<ShoppingItem, Integer> {

	List<ShoppingItem> findAllByPlanId(int planId);

	List<ShoppingItem> findAllByPlanIdAndIngredientFoodProductNameAndDoneAndIngredientUnitType(int planId,
			String foodProductName, boolean isDone, UnitType unitType);

}
