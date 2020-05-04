package com.melearning.mealplanapp.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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
		return getMeals().stream().filter(meal -> (meal.getDate().equals(LocalDate.now()))).collect(Collectors.toList());
	}

	public List<LocalDate> getDates() {
		return IntStream.iterate(0, i -> i + 1).limit(getDuration()).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	public Nutrition getNutritionForPlan() {
		List<Nutrition> nutritions = meals.stream().map(i -> i.getNutritionForMeal())
				.collect(Collectors.toList());
		return Nutrition.sumNutritions(nutritions);
	}

	public Nutrition getAverageNutritionPerDay() {
		return Nutrition.divideNutritionsByLong(this.getNutritionForPlan(), getDuration());	
	}
	
	public HashMap<LocalDate, Nutrition> getIndividualDatesNutrition(){
		HashMap<LocalDate, Nutrition> nutritionOfDays = new HashMap<LocalDate, Nutrition>();
		for (LocalDate date : getDates()) {
			List<Meal> mealsForDate = meals.stream().filter(m -> m.getDate().equals(date)).collect(Collectors.toList());
			List<Nutrition> nutritionOfMeals = mealsForDate.stream().map(m -> m.getNutritionForMeal()).collect(Collectors.toList());
			Nutrition nutrition = Nutrition.sumNutritions(nutritionOfMeals);
			nutritionOfDays.put(date, nutrition);
		}
		return nutritionOfDays;
	}
	

}
