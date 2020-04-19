package com.melearning.mealplanapp.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "username")
	private String username;


	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	@Column(name="plan_style")
	private String planStyle;

	public User() {
	}

	public User(String password, String email, String username) {
		this.password = password;
		this.email = email;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(String password, String email, String username,
			Collection<Role> roles) {
		this.password = password;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getPlanStyle() {
		return planStyle;
	}

	public void setPlanStyle(String planStyle) {
		this.planStyle = planStyle;
	}

}
