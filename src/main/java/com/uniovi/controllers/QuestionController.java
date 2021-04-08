package com.uniovi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.uniovi.entities.Question;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.QuestionService;
import com.uniovi.validators.QuestionValidator;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private QuestionValidator questionValidator;
	
	@RequestMapping(value = "/question/add", method = RequestMethod.GET)
	public String getQuestion(Model model, @ModelAttribute Exercise exercise) {
		model.addAttribute("question", new Question());
		model.addAttribute("exercise", exercise);
		return "question/add";
	}
	
	@RequestMapping(value = "/question/add", method = RequestMethod.POST)
	public String setQuestion(@Validated Question questionVa, BindingResult result,
			Model model, @ModelAttribute Question question, @RequestParam("idExercise") Long idExercise) {
		
		Exercise exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		model.addAttribute("question", question);
		model.addAttribute("idExercise", idExercise);
		
		questionValidator.validate(questionVa, result);
		if(result.hasErrors()) {
			return "question/add";
		}
		
		Question q = new Question(question.getName(), question.getStatement(),exercise);
		model.addAttribute("question", q);
		questionService.addQuestion(q);
		return "answer/add";
	}

	@RequestMapping(value = "/question/addAnswer")
	public String addAnswers(Model model, @ModelAttribute Exercise exercise) {
		model.addAttribute("exercise", exercise);
		return "question/add :: addAnswers";
	}
	
	@RequestMapping(value = "/question/show")
	public String showQuestions(Model model) { 
		List<Question> questions = questionService.getQuestions();
		model.addAttribute("questionList", questions);
		return "question/show";
	}
	
	@RequestMapping(value = "/question/show", method = RequestMethod.POST)
	public String setExercise(Model model, @RequestParam("exerciseId") Long idExercise) {
		List<Question> questions = questionService.getQuestionsByExerciseId(idExercise);
		model.addAttribute("questionList", questions);
		return "question/show";
	}
	
	@RequestMapping("/question/delete/{id}")
	public String getDelete(Model model, @PathVariable Long id) {
		questionService.deleteQuestion(id);
		List<Question> questions = questionService.getQuestionsByExerciseId(id);
		model.addAttribute("questionList", questions);
		return "redirect:/question/show";
	}
}
