package com.melearning.mealplanapp.exception;

import java.time.LocalDate;

public class OverlappingPlanDatesExceptions extends RuntimeException {
	public OverlappingPlanDatesExceptions(LocalDate startDate, LocalDate endDate) {
		super("Jau yra sukurtas planas nors vienai dienai šiame intervale: " + startDate + " - " + endDate);
	}
}
