package com.melearning.mealplanapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melearning.mealplanapp.dao.MealRepository;
import com.melearning.mealplanapp.dao.PlanRepository;
import com.melearning.mealplanapp.entity.Meal;
import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.User;
import com.melearning.mealplanapp.exception.OverlappingPlanDatesExceptions;

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	MealRepository mealRepository;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Plan getCurrentPlan(User user) {
		return planRepository.findByUserIdAndStartDateAfterAndEndDateBefore(user, LocalDate.now());
	}

	@Override
	public void save(Plan plan) {
		if (checkIfDatesAreAvailable(plan)) {
			planRepository.save(plan);
		}else {
			throw new OverlappingPlanDatesExceptions(plan.getStartDate(), plan.getEndDate());
		}
	
	}

	private boolean checkIfDatesAreAvailable(Plan plan) {
		List<Plan> upcomingPlans = getAllUserPlansFrom(plan.getUser().getId(), LocalDate.now());
		List<LocalDate> unavailableDates = upcomingPlans.stream().map(p -> p.getDates()).collect(Collectors.toList())
				.stream().flatMap(List::stream).collect(Collectors.toList());
		if (Collections.disjoint(unavailableDates, plan.getDates())) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Plan> getAllUserPlans(long userId) {
		return planRepository.findAllByUserId(userId);
	}

	@Override
	public Plan getPlanById(int id) {
		return planRepository.findById(id).get();
	}
	
	@Override
	public void saveMeal(Meal meal) {
		mealRepository.save(meal);
	}

	@Override
	public void deleteMeal(int mealId) {
		mealRepository.deleteById(mealId);
	}

	@Override
	public Plan getPlanByMealId(int mealId) {
		Meal meal = mealRepository.findById(mealId).get();
		return meal.getPlan();
	}
	
	@Override
	public List<Plan> getAllUserPlansFrom(long userId, LocalDate date){
		return planRepository.findAllByUserIdAndEndDateGreaterThanEqual(userId, date);
	}
	
	@Override
	public List<Plan> getAllUserPlansUntil(long userId, LocalDate date){
		return planRepository.findAllByUserIdAndEndDateLessThan(userId, date);
	}
	

}
