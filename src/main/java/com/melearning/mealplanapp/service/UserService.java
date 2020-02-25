package com.melearning.mealplanapp.service;

import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.User;

public interface UserService{
	
	public User findByUsername(String username);
	
	public void save(UserDTO userDTO);
	
	public void save(User user);
	
	public User getCurrentUser();
	
	public long getCurrentUserId();
	
	public String getCurrentUserName();

	public boolean hasCurrentUserRole(String role);

}
