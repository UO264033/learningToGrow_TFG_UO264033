package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Answer;
import com.uniovi.services.AnswerService;

@Component
public class AnswerValidator implements Validator{
	
	@Autowired
	private AnswerService answerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Answer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Answer answer = (Answer) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "Error.empty");
		
		if (answerService.getQuestionByAll(answer.getText(), answer.isCorrect(), answer.getQuestion()) != null) {
			errors.rejectValue("text", "Error.addAnswer.repeat");
		}
		
	}

}
