package com.melearning.mealplanapp.entity;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "single_dish_product")
public class SingleDishProduct extends Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "ingredient_id", nullable = false)
//	private Ingredient ingredient;

	@Transient
	private final int servings = 1;

	@Override
	public String getTitle() {
		return this.getIngredients().get(0).getFoodProduct().getName();
	}

	@Override
	public int getServings() {
		return servings;
	}

	public void setIngredient(Ingredient ingredient) {
		this.setIngredients(Collections.singletonList(ingredient));
	}

}
