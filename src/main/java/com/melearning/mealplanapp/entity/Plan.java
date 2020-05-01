package com.melearning.mealplanapp.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "plan")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "start")
	private LocalDate startDate;

	@Column(name = "end")
	private LocalDate endDate;

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Meal> meals;

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShoppingItem> shoppingItems;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public long getDuration() {
		return ChronoUnit.DAYS.between(startDate, endDate) + 1;
	}

	public List<Meal> getMealsForToday() {
		if (meals == null) {
			return null;
		}
		return getMeals().stream().filter(meal -> (meal.getDate() == LocalDate.now())).collect(Collectors.toList());
	}

	public List<LocalDate> getDates() {
		return IntStream.iterate(0, i -> i + 1).limit(getDuration()).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	public float getCalories() {
		float calories = 0;
		for (Meal meal : meals) {
			calories = calories + meal.getCalories();
		}
		return calories;
	}

	public float getAverageCaloriesPerDay() {
		return getCalories() / getDuration();
	}

}
