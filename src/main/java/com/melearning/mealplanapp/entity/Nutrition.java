package com.melearning.mealplanapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nutrition")
public class Nutrition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "kcal")
	private Integer kcal;

	@Column(name = "protein")
	private Integer protein;

	@Column(name = "carbs")
	private Integer carbs;

	@Column(name = "fat")
	private Integer fat;

	public Nutrition() {
	}

	public Nutrition(Integer kcal, Integer protein, Integer carbs, Integer fat) {
		this.kcal = kcal;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getKcal() {
		return kcal;
	}

	public void setKcal(Integer kcal) {
		this.kcal = kcal;
	}

	public Integer getProtein() {
		return protein;
	}

	public void setProtein(Integer protein) {
		this.protein = protein;
	}

	public Integer getCarbs() {
		return carbs;
	}

	public void setCarbs(Integer carbs) {
		this.carbs = carbs;
	}

	public Integer getFat() {
		return fat;
	}

	public void setFat(Integer fat) {
		this.fat = fat;
	}
}
