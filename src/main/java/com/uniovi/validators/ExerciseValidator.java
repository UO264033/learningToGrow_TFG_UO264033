package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Exercise;
import com.uniovi.services.ExerciseService;

@Component
public class ExerciseValidator implements Validator {

	@Autowired
	private ExerciseService exerciseService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Exercise.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Exercise exercise = (Exercise) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

		if (exerciseService.getExerciseByName(exercise.getName()) != null) {
			errors.rejectValue("name", "Error.addExercise.name");
		}
	}

}
