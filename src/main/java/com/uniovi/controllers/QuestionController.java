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

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.services.ExerciseTestService;
import com.uniovi.services.QuestionService;
import com.uniovi.validators.QuestionValidator;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExerciseTestService exerciseService;

	@Autowired
	private QuestionValidator questionValidator;

	@RequestMapping(value = "/exercise/test/question/add", method = RequestMethod.GET)
	public String getQuestion(Model model, @ModelAttribute Exercise exercise, @RequestParam("exerciseId") Long exerciseId) {
		model.addAttribute("question", new Question());
		if (exercise == null) {
			exercise = exerciseService.getExercise(exerciseId);
		}
		model.addAttribute("exercise", exercise);
		return "exercise/test/question/add";
	}
	
	@RequestMapping(value = "/exercise/test/question/add/{id}", method = RequestMethod.GET)
	public String addAnotherQuestion(Model model, @ModelAttribute Exercise exercise, @PathVariable Long id) {
		model.addAttribute("question", new Question());
		if (exercise == null) {
			exercise = exerciseService.getExercise(id);
		}
		model.addAttribute("exercise", exercise);
		model.addAttribute("exerciseId", id);
		return "exercise/test/question/add";
	}
	
	@RequestMapping(value = "/exercise/test/question/add", method = RequestMethod.POST)
	public String addQuestion(@Validated Question questionVa, BindingResult result, RedirectAttributes redirectAttrs,
			Model model, @RequestParam String statement, @RequestParam("exerciseId") Long exerciseId,
			@RequestParam String texto1, @RequestParam String texto2, @RequestParam String texto3,
			@RequestParam(required = false) boolean correct1, @RequestParam(required = false) boolean correct2,
			@RequestParam(required = false) boolean correct3) {

		Exercise exercise = exerciseService.getExercise(exerciseId);
		model.addAttribute("exercise", exercise);
		model.addAttribute("question", new Question());
		model.addAttribute("exerciseId", exerciseId);

		questionValidator.validate(questionVa, result);
		if (result.hasErrors()) {
			return "exercise/test/question/add";
		}
		Question q = questionService.addNewQuestion(model, statement, texto1, texto2, texto3, correct1, correct2, correct3, exercise);
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
