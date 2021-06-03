package com.uniovi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.UploadFile;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseFileUploadService;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UserService;
import com.uniovi.validators.ExerciseValidator;


@Controller
public class ExerciseFileUploadController {
	
	@Autowired
	private UserService usersService;

	@Autowired
	private ExerciseValidator exerciseValidator;
	
	@Autowired
	private ExerciseFileUploadService exerciseService;
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(value = "/exercise/upFile/add")
	public String getExercise(Model model, Pageable pageable) {
		model.addAttribute("exercise", new Exercise());
		model.addAttribute("subjectList", subjectService.getSubjects());
		return "exercise/upFile/add";
	}
	
	@RequestMapping(value = "/exercise/upFile/add", method = RequestMethod.POST)
	public String setExercise(Pageable pageable, @Validated Exercise exerciseVa, BindingResult result,
			Model model, @ModelAttribute UploadFile exercise, @ModelAttribute User user) {
		
		exerciseValidator.validate(exerciseVa, result);
		if(result.hasErrors()) {
			model.addAttribute("subjectList", subjectService.getSubjects());
			model.addAttribute("exercise", exerciseVa);
			return "exercise/upFile/add";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		exercise.setProfessor(activeUser);
		
		model.addAttribute("exercise", exercise);
		model.addAttribute("user", user);
		exerciseService.addExercise(exercise);
		return "redirect:/exercise/list";
	}
	
}
