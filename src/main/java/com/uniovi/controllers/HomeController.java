package com.uniovi.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UsersService;

@Controller
public class HomeController {
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		Page<Exercise> exercises = new PageImpl<Exercise>(new LinkedList<Exercise>());
		exercises = exerciseService.getExercisesByUser(pageable, activeUser);
		List<Subject> subjects = subjectService.getSubjectsByRole(activeUser);

		model.addAttribute("subjectList", subjects);
		model.addAttribute("user", activeUser);
		model.addAttribute("exerciseList", exercises.getContent());
		model.addAttribute("page", exercises);
		return "home";
	}
}
