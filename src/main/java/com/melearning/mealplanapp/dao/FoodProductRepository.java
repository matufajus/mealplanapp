package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.melearning.mealplanapp.entity.FoodProduct;

public interface FoodProductRepository extends JpaRepository<FoodProduct, Integer>{

	@Query("SELECT name FROM FoodProduct where name like %:keyword%")
	List<String> findByNameContaining(@Param("keyword") String keyword);
	
	FoodProduct findByName(String name);
	
//	List<FoodProduct> findByNameContaining(String keyword);
}
