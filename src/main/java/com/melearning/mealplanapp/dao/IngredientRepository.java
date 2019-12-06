package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
