package com.melearning.mealplanapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.melearning.mealplanapp.enumeration.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "food_product")
public class FoodProduct {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "food_type")
	@Enumerated(EnumType.ORDINAL)
	private FoodType foodType;

//	g/cm^3=g/ml 
	@Column(name = "density")
	private float density;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id")
	private Nutrition nutrition;

	public FoodProduct(String name, FoodType foodType, float density, Nutrition nutrition) {
		this.name = name;
		this.foodType = foodType;
		this.density = density;
		this.nutrition = nutrition;
	}

}
