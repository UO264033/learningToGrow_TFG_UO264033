package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.*;

import com.uniovi.entities.Question;
import com.uniovi.services.QuestionService;

@Component
public class QuestionValidator implements Validator {

	@Autowired
	private QuestionService questionService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Question.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Question question = (Question) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statement", "Error.empty");
		
		if (questionService.getQuestionByName(question.getName()) != null) {
			errors.rejectValue("name", "Error.addQuestion.duplicate");
		}
	}
}
