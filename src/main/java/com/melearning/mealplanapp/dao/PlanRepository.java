package com.melearning.mealplanapp.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.melearning.mealplanapp.entity.Plan;
import com.melearning.mealplanapp.entity.User;

public interface PlanRepository extends JpaRepository<Plan, Integer>{
	
	@Query("select p from Plan p where p.user = ?1 AND p.startDate >= ?1 AND p.endDate <= ?1")
	Plan findByUserIdAndStartDateAfterAndEndDateBefore(User user, LocalDate today);

	List<Plan> findAllByUserId(long userId);

	List<Plan> findAllByUserIdAndEndDateGreaterThanEqual(long userId, LocalDate date);

	List<Plan> findAllByUserIdAndEndDateLessThan(long userId, LocalDate date);

}
