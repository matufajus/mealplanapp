package com.melearning.mealplanapp.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
