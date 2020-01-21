package com.melearning.mealplanapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.ShoppingItem;

public interface ShoppingRepository extends JpaRepository<ShoppingItem, Integer> {
	
	List<ShoppingItem> findByMealInOrderByName(List<Meal> meals);
	
	List<ShoppingItem> findByNameAndMealIn(String name, List<Meal> meals);

	public void deleteByMealId(int mealId);

}
