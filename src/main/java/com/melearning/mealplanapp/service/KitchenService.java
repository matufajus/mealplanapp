package com.melearning.mealplanapp.service;

import java.util.List;

import com.melearning.mealplanapp.entity.KitchenProduct;

public interface KitchenService {
	
	public List<KitchenProduct> getAllProductsForUser(long id);
	
	public void addProduct(KitchenProduct product);
	
	public void removeProduct(int id);

}
