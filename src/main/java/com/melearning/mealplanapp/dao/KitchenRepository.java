package com.melearning.mealplanapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.KitchenProduct;

public interface KitchenRepository extends JpaRepository<KitchenProduct, Integer>{
	
	List<KitchenProduct> findByUserId(long userId);

	void deleteByUserIdAndFoodProductId(long userId, int id);
	
}
