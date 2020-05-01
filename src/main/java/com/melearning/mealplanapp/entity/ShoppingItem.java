package com.melearning.mealplanapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melearning.mealplanapp.enumeration.FoodType;
import com.melearning.mealplanapp.enumeration.UnitType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_item")
public class ShoppingItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "foodProduct_id")
	private FoodProduct foodProduct;

	@JoinColumn(name = "ammount")
	private float ammount;

	@Column(name = "is_done")
	private boolean done;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

}
