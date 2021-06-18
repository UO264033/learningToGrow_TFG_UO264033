package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Subject;
import com.uniovi.services.SubjectService;

@Component
public class SubjectValidator  implements Validator {
	
	@Autowired
	private SubjectService subjectService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Subject.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Subject subject = (Subject) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
		
		if (subjectService.getSubjectByName(subject.getName()) != null) {
			errors.rejectValue("name", "Error.subject.duplicate");
		}
		
		if(subjectService.getIdsStudent() == null) {
			errors.rejectValue("students", "Error.subject.noStudents");
		}
	}

}
