package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melearning.mealplanapp.dao.KitchenRepository;
import com.melearning.mealplanapp.entity.FoodProduct;
import com.melearning.mealplanapp.entity.KitchenProduct;
import com.melearning.mealplanapp.exception.UniqueProductConstraintValidationException;

@Service
public class KitchenServiceImpl implements KitchenService {

	@Autowired
	KitchenRepository kitchenRepository;

	@Override
	public List<KitchenProduct> getAllProductsForUser(long userId) {
		return kitchenRepository.findByUserId(userId);
	}

	@Override
	public void addProduct(KitchenProduct product) {
		try {
			kitchenRepository.save(product);
		} catch (DataIntegrityViolationException e) {
			throw new UniqueProductConstraintValidationException(product.getFoodProduct().getName());
		}
		
	}
	
	@Override
	public void removeProduct(int id) {
		kitchenRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void removeKitchenProductByFoodProductId(long userId, int id) {
		kitchenRepository.deleteByUserIdAndFoodProductId(userId, id);
	}

}
