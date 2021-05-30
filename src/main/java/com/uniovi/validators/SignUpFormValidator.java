package com.uniovi.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class SignUpFormValidator implements Validator{

	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Error.empty");
		if (user.getUsername().length() < 6 || user.getUsername().length() > 30) {
			errors.rejectValue("username", "Error.signup.dni.length");
		}
		if (usersService.getUserByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Error.signup.dni.duplicate");
		}
		if (user.getName().length() < 2 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (user.getLastName().length() < 2 || user.getLastName().length() > 30) {
			errors.rejectValue("lastName", "Error.signup.lastName.length");
		}
		Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(user.getEmail());
		if (!mather.find()) {
			errors.rejectValue("email", "Error.signup.email");
		}
		if (user.getPassword().length() < 8 || user.getPassword().length() > 60) {   //VALIDAR MEJOR
			errors.rejectValue("password", "Error.signup.password.length");
		}
		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
		}
	}
}
