package com.melearning.mealplanapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PlanTest {

	@Test
	void testGetDuration() {
		Plan plan = new Plan();
		long duration;
		plan.setStartDate(LocalDate.parse("2020-04-10"));
		plan.setEndDate(LocalDate.parse("2020-04-15"));
		duration = plan.getDuration();
		assertEquals(6, duration);
		
		plan.setStartDate(LocalDate.parse("2020-04-15"));
		plan.setEndDate(LocalDate.parse("2020-04-15"));
		duration = plan.getDuration();
		assertEquals(1, duration);
	}
	
	@Test
	void testGetPlanDates() {
		Plan plan = new Plan();
		plan.setStartDate(LocalDate.parse("2020-04-10"));
		plan.setEndDate(LocalDate.parse("2020-04-13"));
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates.add(LocalDate.parse("2020-04-10"));
		dates.add(LocalDate.parse("2020-04-11"));
		dates.add(LocalDate.parse("2020-04-12"));
		dates.add(LocalDate.parse("2020-04-13"));
		assertEquals(dates, plan.getDates());
		
	}
	
	

}
