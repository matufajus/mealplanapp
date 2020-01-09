package com.melearning.mealplanapp.exception;

public class UniqueProductConstraintValidationException extends RuntimeException {

	public UniqueProductConstraintValidationException(String name) {
		super("There already is product with name: "+ name);
	}
	
}
