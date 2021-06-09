package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Homework;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.HomeworkService;

@Controller
public class DoHomeworkController {

	@Autowired
	private HomeworkService homeworksService;

	@Autowired
	private ExerciseService exerciseService;
	
	@RequestMapping("/homework/do/{id}")
	public String doHomework(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		if (exercise.getType() == ExerciseType.T)
			return "homework/do/test";
		else if (exercise.getType() == ExerciseType.S)
			return "homework/do/shortAnswer";
		else if (exercise.getType() == ExerciseType.U)
			return "homework/do/uploadFile";
		return "homework/do";
	}

	@RequestMapping("/homework/do/uploadFile")
	public String getHomeworkUploadFile(Model model, @RequestParam Exercise exercise) {
		model.addAttribute("exercise", exercise);
		return "homework/do/uploadFile";
	}

	@RequestMapping("/homework/do/test")
	public String getHomeworkTest(Model model, @RequestParam Exercise exercise) {
		model.addAttribute("exercise", exercise);
		return "homework/do/test";
	}

	@RequestMapping("/homework/do/shortAnswer")
	public String getHomeworkShortAnswer(Model model, @RequestParam Exercise exercise) {
		model.addAttribute("exercise", exercise);
		return "homework/do/uploadFile";
	}

	@RequestMapping(value = "/homework/do/shortAnswer", method = RequestMethod.POST)
	public String savesHomeworkShortAnswer(Model model, @RequestParam(value = "idExercise") Long idExercise,
			@RequestParam(value = "answerStrings[]") String[] answerStrings, Principal principal,
			@RequestParam(value = "description", required = false) String description) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = homeworksService.saveShortAnswers(answerStrings, principal, description, realExercise);
		homeworksService.addHomework(homework);
		return "redirect:/homework/exercise/list";
	}

	@RequestMapping(value = "/homework/do/test", method = RequestMethod.POST)
	public String savesHomeworkTest(Model model, @RequestParam(value = "idExercise") Long idExercise,
			@RequestParam(value = "checkAnswers[]") int[] checkAnswers, Principal principal,
			@RequestParam(value = "description", required = false) String description) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = homeworksService.saveAnswersTest(checkAnswers, principal, description, realExercise);
		homeworksService.addHomework(homework);
		return "redirect:/homework/exercise/list";
	}

	@RequestMapping(value = "/homework/do/uploadFile", method = RequestMethod.POST)
	public String savesHomeworkUploadFile(Model model, @RequestParam(value = "idExercise") Long idExercise,
			@RequestParam("file") MultipartFile file,
			@RequestParam(value = "description", required = false) String description, RedirectAttributes attributes,
			Principal principal) {
		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor, seleccione un archivo.");
			return "homework/do/uploadFile";
		}
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = homeworksService.saveFiles(file, description, principal, realExercise);
		homeworksService.addHomework(homework);
		return "redirect:/homework/exercise/list";
	}
}
