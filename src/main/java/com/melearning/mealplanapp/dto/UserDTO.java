package com.melearning.mealplanapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.melearning.mealplanapp.validation.FieldMatch;
import com.melearning.mealplanapp.validation.ValidEmail;


@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserDTO {
	
	@ValidEmail
	@NotNull(message = "Email is required")
	@Size(min = 1, message = "Email is required")
	private String email;
	
	@NotNull(message = "Username is required")
	@Size(min = 1, message = "Username is required")
	private String username;
	
	@NotNull(message = "Password is required")
	@Size(min = 1, message = "Password is required")
	private String password;
	
	@NotNull(message = "This field is required")
	@Size(min = 1, message = "This field is required")
	private String matchingPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
