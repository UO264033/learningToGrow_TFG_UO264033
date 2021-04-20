package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Question;

@Component
public class QuestionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Question.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statement", "Error.empty");
		
	}
}
