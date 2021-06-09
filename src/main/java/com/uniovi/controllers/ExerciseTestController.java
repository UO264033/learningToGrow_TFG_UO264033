package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Test;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseTestService;
import com.uniovi.services.SubjectService;
import com.uniovi.validators.ExerciseValidator;

@Controller
public class ExerciseTestController {
	
	@Autowired
	private ExerciseValidator exerciseValidator;
	
	@Autowired
	private ExerciseTestService exerciseService;
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(value = "/exercise/test/add")
	public String getExercise(Model model, Pageable pageable) {
		model.addAttribute("exercise", new Exercise());
		model.addAttribute("subjectList", subjectService.getSubjects());
		return "exercise/test/add";
	}
	
	@RequestMapping(value = "/exercise/test/add", method = RequestMethod.POST)
	public String setExercise(Pageable pageable, @Validated Exercise exerciseVa, BindingResult result,
			Model model, @ModelAttribute Test exercise, @ModelAttribute User user) {
		
		exerciseValidator.validate(exerciseVa, result);
		if(result.hasErrors()) {
			model.addAttribute("exercise", exerciseVa);
			model.addAttribute("subjectList", subjectService.getSubjects());
			return "exercise/test/add";
		}
		
		exerciseService.setProfesor(exercise);
		exerciseService.addExercise(exercise);
		
		model.addAttribute("exercise", exercise);
		model.addAttribute("user", user);
		return "exercise/test/question/add";
	}

	@RequestMapping(value = "/exercise/test/show/{id}")
	public String showQuestions(Model model, @PathVariable Long id) {
		Test exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		model.addAttribute("idExercise", id);
		model.addAttribute("questionList", exercise.getQuestionsSet());
		return "exercise/test/show";
	}
	

}
