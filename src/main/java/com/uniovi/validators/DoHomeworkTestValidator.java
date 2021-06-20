package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniovi.entities.TestType;
import com.uniovi.services.HomeworkService;

@Component
public class DoHomeworkTestValidator  implements Validator {
	
	@Autowired
	private HomeworkService homeworkService;

	@Override
	public boolean supports(Class<?> aClass) {
		return TestType.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(homeworkService.getIdsAnswers() == null) {
			errors.rejectValue("name", "Error.studentAnswer.empty");
		}
	}

}
