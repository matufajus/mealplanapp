package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Preparation;

public interface PreparationRepository extends JpaRepository<Preparation, Integer> {

}
