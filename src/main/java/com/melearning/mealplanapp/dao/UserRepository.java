package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
