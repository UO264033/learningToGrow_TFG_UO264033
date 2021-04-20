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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniovi.entities.Answer;
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

	@RequestMapping(value = "/exercise/test/question/add", method = RequestMethod.GET)
	public String getQuestion(Model model, @ModelAttribute Exercise exercise, @RequestParam Long idExercise) {
		model.addAttribute("question", new Question());
		if (exercise == null)
			exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		return "exercise/test/question/add";
	}

	@RequestMapping(value = "/exercise/test/question/add", method = RequestMethod.POST)
	public String setQuestion(@Validated Question questionVa, BindingResult result, RedirectAttributes redirectAttrs,
			Model model, @RequestParam String statement, @RequestParam("idExercise") Long idExercise,
			@RequestParam String texto1, @RequestParam String texto2, @RequestParam String texto3,
			@RequestParam(required = false) boolean correct1, @RequestParam(required = false) boolean correct2,
			@RequestParam(required = false) boolean correct3) {

		Exercise exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		model.addAttribute("question", new Question());
		model.addAttribute("idExercise", idExercise);

		questionValidator.validate(questionVa, result);
		if (result.hasErrors()) {
			return "exercise/test/question/add";
		}

		Question q = new Question(statement, exercise);
		q.addAnswer(new Answer(texto1, correct1, q));
		q.addAnswer(new Answer(texto2, correct2, q));
		q.addAnswer(new Answer(texto3, correct3, q));
		model.addAttribute("question", q);
		q = questionService.addQuestion(q);

		if (q != null) {
			model.addAttribute("mensaje", "La pregunta se ha añadido correctamente");
		}

		return "exercise/test/question/add";
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
		model.addAttribute("idExercise", idExercise);
		return "question/show";
	}

	@RequestMapping("/question/delete/{id}")
	public String getDelete(Model model, @PathVariable Long id) {
		Question q = questionService.getQuestion(id);
		questionService.deleteQuestion(id);
		List<Question> questions = questionService.getQuestionsByExerciseId(q.getExercise().getId());
		model.addAttribute("questionList", questions);
		model.addAttribute("exercise", q.getExercise());
		return "redirect:/exercise/test/show/" + q.getExercise().getId();
	}
}
