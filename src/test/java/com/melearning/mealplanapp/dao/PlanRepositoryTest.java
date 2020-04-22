package com.melearning.mealplanapp.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.melearning.mealplanapp.entity.Plan;

@DataJpaTest
class PlanRepositoryTest {
	
	@Autowired
	PlanRepository planRep;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	void whenCreateNewPlan_thenPlanIdIsSet() {
		Plan plan = new Plan();
		plan.setStartDate(LocalDate.parse("2020-06-15"));
		plan.setEndDate(LocalDate.parse("2020-06-15"));
		logger.debug("Plan id before save: " +plan.getId());
		planRep.save(plan);
		logger.debug("Plan id after save: " +plan.getId());
		assertNotNull(plan.getId());
		assertNotEquals(0, plan.getId());
	}

}
