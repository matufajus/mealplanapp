package com.melearning.mealplanapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.User;

public interface UserService extends UserDetailsService{
	
	public User findByUsername(String username);
	
	public void save(UserDTO userDTO);
	
	public void save(User user);

}
