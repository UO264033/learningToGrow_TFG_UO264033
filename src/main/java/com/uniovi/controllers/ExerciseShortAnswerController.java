package com.uniovi.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseShortAnswerService;
import com.uniovi.services.SubjectService;
import com.uniovi.validators.ExerciseValidator;

@Controller
public class ExerciseShortAnswerController {

	@Autowired
	private ExerciseValidator exerciseValidator;

	@Autowired
	private ExerciseShortAnswerService exerciseService;

	@Autowired
	private SubjectService subjectService;

	@RequestMapping(value = "/exercise/shortAnswer/add")
	public String getExercise(Model model, Pageable pageable) {
		model.addAttribute("exercise", new Exercise());
		model.addAttribute("subjectList", subjectService.getSubjects());
		return "exercise/shortAnswer/add";
	}

	@RequestMapping(value = "/exercise/shortAnswer/add", method = RequestMethod.POST)
	public String setExercise(Pageable pageable, @Validated Exercise exerciseVa, BindingResult result, Model model,
			@ModelAttribute ShortAnswer exercise, @ModelAttribute User user) {

		exerciseValidator.validate(exerciseVa, result);
		if (result.hasErrors()) {
			model.addAttribute("subjectList", subjectService.getSubjects());
			model.addAttribute("exercise", exerciseVa);
			return "exercise/shortAnswer/add";
		}

		exerciseService.setProfesor(exercise);
		exerciseService.addExercise(exercise);
		
		model.addAttribute("exercise", exercise);
		model.addAttribute("user", user);
		return "exercise/shortAnswer/q&a/add";
	}

	@RequestMapping(value = "/exercise/shortAnswer/q&a/add", method = RequestMethod.GET)
	public String addQuestion(Model model, @ModelAttribute Exercise exercise, @RequestParam("idExercise") Long idExercise) {
		if(exercise == null)
			exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		return "exercise/shortAnswer/q&a/add";
	}
	
	@RequestMapping(value = "/exercise/shortAnswer/q&a/add/{id}", method = RequestMethod.GET)
	public String addAnotherQuestion(Model model, @ModelAttribute Exercise exercise, @PathVariable Long id) {
		if(exercise == null)
			exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		model.addAttribute("exerciseId", id);
		return "exercise/shortAnswer/q&a/add";
	}

	@RequestMapping(value = "/exercise/shortAnswer/q&a/add", method = RequestMethod.POST)
	public String setQuestion(@Validated ShortAnswer shortAnswerVa, BindingResult result, Model model,
			@ModelAttribute ShortAnswer shortAnswer, @RequestParam("idExercise") Long idExercise,
			@RequestParam String statement, @RequestParam String text) {

		ShortAnswer exercise = exerciseService.getExercise(idExercise);
		model.addAttribute("exercise", exercise);
		model.addAttribute("question", shortAnswer);
		model.addAttribute("idExercise", idExercise);

		exerciseService.setQuestion(model, statement, text, exercise);
		exercise = exerciseService.addExercise(exercise);
		if (exercise != null) {
			model.addAttribute("mensaje", "La pregunta se ha añadido correctamente");
		}
		return "exercise/shortAnswer/q&a/add";
	}


	@RequestMapping(value = "/exercise/shortAnswer/show/{id}")
	public String showQuestions(Model model, @PathVariable Long id) {
		ShortAnswer exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		model.addAttribute("questionList", exercise.getQuestionsSet());
		return "exercise/shortAnswer/show";
	}

	@RequestMapping(value = "/exercise/shortAnswer/show", method = RequestMethod.POST)
	public String setExercise(Model model, @RequestParam("exerciseId") Long idExercise) {
		List<Question> questions = exerciseService.getQuestionsByExerciseId(idExercise);
		model.addAttribute("questionList", questions);
		return "exercise/shortAnswer/show";
	}

	@RequestMapping("/exercise/shortAnswer/delete/{idQuestion}")
	public String getDelete(Model model, @PathVariable Long idQuestion) {
		exerciseService.deleteQuestion(idQuestion);
		Exercise exercise = exerciseService.getExerciseByQuestionId(idQuestion);
		return "redirect:/exercise/shortAnswer/show/" + exercise.getId();
	}

}