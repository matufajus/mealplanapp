package com.melearning.mealplanapp.exception;

public class UniqueProductConstraintValidationException extends RuntimeException {

	public UniqueProductConstraintValidationException(String name) {
		super("Jau yra produktas pavadinimu: "+ name);
	}
	
}
