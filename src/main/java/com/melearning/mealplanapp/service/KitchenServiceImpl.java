package com.melearning.mealplanapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.KitchenRepository;
import com.melearning.mealplanapp.entity.KitchenProduct;

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
		kitchenRepository.save(product);
	}
}
