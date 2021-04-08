package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.ExerciseValidator;

@Controller
public class ExerciseController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ExerciseValidator exerciseValidator;
	
	@RequestMapping(value = "/exercise/add")
	public String getExercise(Model model, Pageable pageable) {
		model.addAttribute("exercise", new Exercise());
		model.addAttribute("subjectList", subjectService.getSubjects());
		return "exercise/add";
	}

	@RequestMapping(value = "/exercise/add", method = RequestMethod.POST)
	public String setExercise(Pageable pageable, @Validated Exercise exerciseVa, BindingResult result,
			Model model, @ModelAttribute Exercise exercise, @ModelAttribute User user) {
		
		exerciseValidator.validate(exerciseVa, result);
		if(result.hasErrors()) {
			model.addAttribute("exercise", new Exercise());
			model.addAttribute("subjectList", subjectService.getSubjects());
			return "exercise/add";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		exercise.setProfessor(activeUser);
		model.addAttribute("exercise", exercise);
		model.addAttribute("user", user);
		exerciseService.addExercise(exercise);
		return "question/add";
	}
	
	@RequestMapping(value = "/exercise/list")
	public String getExercises(Model model, Principal principal,  @RequestParam(value= "", required = false) String searchText) {
		String username = principal.getName(); // Username es el name de la autenticación 
		User user = usersService.getUserByUsername(username);
		
		List<Exercise> exercises = exerciseService.getExercises();

		model.addAttribute("exerciseList", exercises);
		return "exercise/list";
	}

	@RequestMapping("/exercise/delete/{id}")
	public String getDelete(@PathVariable Long id) {
		exerciseService.deleteExercise(id);
		return "redirect:/exercise/list";
	}
	
	@RequestMapping("/exercise/details/{id}")
	public String getDetails(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		model.addAttribute("questionList", exercise.getQuestions());
		return "exercise/details";
	}
	
	@RequestMapping("/exercise/edit/{id}")
	public String editExercise(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		model.addAttribute("questionList", exercise.getQuestions());
		return "exercise/edit";
	}
}
