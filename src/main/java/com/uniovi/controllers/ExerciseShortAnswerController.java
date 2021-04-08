package com.uniovi.controllers;


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
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.ShortAnswerService;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.ExerciseValidator;


@Controller
public class ExerciseShortAnswerController {
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private ExerciseValidator exerciseValidator;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ShortAnswerService shortAnswerService;
	
	@RequestMapping(value = "/exercise/shortAnswer/add")
	public String getExercise(Model model, Pageable pageable) {
		model.addAttribute("exercise", new Exercise());
		model.addAttribute("subjectList", subjectService.getSubjects());
		return "exercise/shortAnswer/add";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/add", method = RequestMethod.POST)
	public String setExercise(Pageable pageable, @Validated Exercise exerciseVa, BindingResult result,
			Model model, @ModelAttribute Exercise exercise, @ModelAttribute User user) {
		
		exerciseValidator.validate(exerciseVa, result);
		if(result.hasErrors()) {
			model.addAttribute("subjectList", subjectService.getSubjects());
			model.addAttribute("exercise", new Exercise());
			return "exercise/shortAnswer/add";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		exercise.setProfessor(activeUser);
		
		model.addAttribute("exercise", exercise);
		model.addAttribute("user", user);
		exerciseService.addExercise(exercise);
		return "exercise/shortAnswer/q&a/add";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/q&a/add", method = RequestMethod.GET)
	public String getQuestion(Model model, @ModelAttribute Exercise exercise) {
		model.addAttribute("question", new ShortAnswer());
		model.addAttribute("exercise", exercise);
		return "exercise/shortAnswer/q&a/add";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/q&a/add", method = RequestMethod.POST)
	public String setQuestion(@Validated ShortAnswer shortAnswerVa, BindingResult result,
			Model model, @ModelAttribute ShortAnswer shortAnswer, @RequestParam("idExercise") Long idExercise) {
		
		Exercise exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		model.addAttribute("question", shortAnswer);
		model.addAttribute("idExercise", idExercise);
		
//		questionValidator.validate(questionVa, result);
//		if(result.hasErrors()) {
//			return "question/add";
//		}
		
		ShortAnswer sh = new ShortAnswer( shortAnswer.getStatement(), shortAnswer.getAnswer(), exercise);
		model.addAttribute("question", sh);
		shortAnswerService.addQuestion(sh);
		return "exercise/shortAnswer/q&a/add";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/show/{id}")
	public String showQuestions(Model model, @PathVariable Long id) { 
		List<ShortAnswer> questions = shortAnswerService.getQuestionsByExerciseId(id);
		model.addAttribute("questionList", questions);
		return "exercise/shortAnswer/show";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/show", method = RequestMethod.POST)
	public String setExercise(Model model, @RequestParam("exerciseId") Long idExercise) {
		List<ShortAnswer> questions = shortAnswerService.getQuestionsByExerciseId(idExercise);
		model.addAttribute("questionList", questions);
		return "exercise/shortAnswer/show";
	}
	
	@RequestMapping("/exercise/shortAnswer/delete/{id}")
	public String getDelete(Model model, @PathVariable Long id) {
		Exercise exercise = shortAnswerService.getExerciseByQuestionId(id);
		shortAnswerService.deleteQuestion(id);
		return "redirect:/exercise/shortAnswer/show/" + exercise.getId();
	}
	
}