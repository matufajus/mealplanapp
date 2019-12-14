package com.melearning.mealplanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melearning.mealplanapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByName(String name);

}
