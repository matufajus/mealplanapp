package com.melearning.mealplanapp.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melearning.mealplanapp.dao.RoleRepository;
import com.melearning.mealplanapp.dao.UserRepository;
import com.melearning.mealplanapp.dto.UserDTO;
import com.melearning.mealplanapp.entity.Role;
import com.melearning.mealplanapp.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Override
	@Transactional
	public void save(UserDTO userDTO) {
		User user = new User();
		 // assign user details to the user object
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setEmail(userDTO.getEmail());
		user.setPlanDays(7);

		// give user default role of "user"
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

		 // save user in the database
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return userRepository.findByUsername(currentPrincipalName);
	}

	@Override
	public long getCurrentUserId() {
		User user = getCurrentUser();
		return user.getId();
	}

}
