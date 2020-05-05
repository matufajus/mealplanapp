package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.SingleDishProduct;

public interface SingleDishProductRepository extends JpaRepository<SingleDishProduct, Integer>{

}
