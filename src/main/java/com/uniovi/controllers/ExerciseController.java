package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.TestType;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.UserService;

@Controller
public class ExerciseController {

	@Autowired
	private UserService usersService;

	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value = "/exercise/list")
	public String getExercises(Model model, Principal principal, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<Exercise> exercises = new PageImpl<Exercise>(new LinkedList<Exercise>());
		String username = principal.getName(); // Username es el name de la autenticación
		User activeUser = usersService.getUserByUsername(username);
		exercises = exerciseService.getExercisesByUser(pageable, activeUser, searchText);
		model.addAttribute("exerciseList", exercises.getContent());
		model.addAttribute("page", exercises);
		return "exercise/list";
	}

	@RequestMapping("/exercise/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		exerciseService.deleteInsideExercise(id);
//		exerciseService.deleteExercise(id);
		model.addAttribute("mensaje", "El ejercicio se ha eliminado correctamente");
		return "redirect:/exercise/list";
	}

	@RequestMapping("/exercise/details/{id}")
	public String getDetails(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		if (exercise.getType() == ExerciseType.S) {
			model.addAttribute("questionList", ((ShortAnswer) exercise).getQuestions());
			model.addAttribute("short", "short");
		} else if (exercise.getType() == ExerciseType.T) {
			model.addAttribute("questionList", ((TestType) exercise).getQuestions());
			model.addAttribute("test", "test");
		} else
			model.addAttribute("questionList", null);
		return "exercise/details";
	}

	@RequestMapping("/exercise/edit/{id}")
	public String editExercise(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		return "exercise/edit";
	}
}
