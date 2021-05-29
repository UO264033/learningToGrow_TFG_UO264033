package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.services.AnswerService;
import com.uniovi.services.QuestionService;

@Controller
public class AnswerController {

	@Autowired 
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@RequestMapping(value = "/answer/add")
	public String getAnswer(Model model, @ModelAttribute Question question, @ModelAttribute Exercise exercise) {
		model.addAttribute("answer", new Answer());
		model.addAttribute("question", question);
		model.addAttribute("exercise", exercise);
		return "answer/add";
	}

	@RequestMapping(value = "/answer/add", method = RequestMethod.POST)
	public String setAnswers(@Validated Answer answerVa, BindingResult result, Model model,
			@RequestParam("texto") String texto, @RequestParam(value = "correct", required = false) boolean correct,
			@RequestParam("idQuestion") Long idQuestion) {
//		answerValidator.validate(answerVa, result);
//		if(result.hasErrors()) {
//			return "/answer/add";
//		}

		Question question = questionService.getQuestion(idQuestion);
		Answer answer = new Answer(texto, correct, question);
		answerService.addAnswer(answer);
		model.addAttribute("question", question);
		return "answer/add";
	}
}
