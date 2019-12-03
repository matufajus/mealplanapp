package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
