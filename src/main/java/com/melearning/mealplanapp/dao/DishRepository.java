package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer>{

}
