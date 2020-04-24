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

	public Plan() {
	}	

	public Plan(int id, String title, LocalDate fromDate, LocalDate endDate, List<Meal> meals, User user) {
		this.id = id;
		this.title = title;
		this.startDate = fromDate;
		this.endDate = endDate;
		this.meals = meals;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate fromDate) {
		this.startDate = fromDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public List<ShoppingItem> getShoppingItems() {
		return shoppingItems;
	}

	public void setShoppingItems(List<ShoppingItem> shoppingItems) {
		this.shoppingItems = shoppingItems;
	}
	
	public long getDuration() {
		return ChronoUnit.DAYS.between(startDate, endDate)+1;
	}

	public List<Meal> getMealsForToday() {
		if (meals == null) {
			return null;
		}
		return getMeals().stream().filter(meal -> (meal.getDate() == LocalDate.now())).collect(Collectors.toList());
	}
	
	public List<LocalDate> getDates() {
		return IntStream.iterate(0, i -> i + 1)
			      .limit(getDuration())
			      .mapToObj(i -> startDate.plusDays(i))
			      .collect(Collectors.toList());
	}

}
