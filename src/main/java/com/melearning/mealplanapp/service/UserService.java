package com.melearning.mealplanapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.User;

public interface UserService extends UserDetailsService{
	
	User findByUsername(String username);
	
	void save(UserDTO userDTO);

}
