package com.melearning.mealplanapp.entity;

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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "ammount")
	private float ammount;
	
	@Column(name = "unit")
	@Enumerated(EnumType.ORDINAL)
	private UnitType unit;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.substring(0, 1).toUpperCase()+name.substring(1);
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}
	
	public Ingredient() {
		
	}

	public Ingredient(int id, String name, float ammount, UnitType unit, Recipe recipe) {
		this.id = id;
		this.name = name.substring(0, 1).toUpperCase()+name.substring(1);;
		this.ammount = ammount;
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", ammount=" + ammount + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UnitType getUnit() {
		return unit;
	}

	public void setUnit(UnitType unit) {
		this.unit = unit;
	}
	
	
	

}
