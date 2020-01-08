package com.melearning.mealplanapp.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "kitchen_product")
public class KitchenProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="name")
	private String name;
	
	@Column(name="quantity")
	private String quantity;
	
	@Column(name="expiration_date")
	private Date expirationDate;
	
	@Column(name="food_type")
	@Enumerated(EnumType.ORDINAL)
	private FoodType foodType;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	
	public KitchenProduct() {
		
	}

	public KitchenProduct(User user, String name, String quantity, Date expirationDate, String location,
			FoodType foodType) {
		this.user = user;
		this.name = name;
		this.quantity = quantity;
		this.expirationDate = expirationDate;
		this.foodType = foodType;
	}
}
