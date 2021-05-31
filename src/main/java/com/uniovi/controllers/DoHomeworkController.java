package com.uniovi.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Homework;
import com.uniovi.entities.User;
import com.uniovi.services.AnswerService;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.UsersService;

@Controller
public class DoHomeworkController {

	@Autowired
	private HomeworkService homeworksService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private AnswerService answerService;

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
		Homework homework = new Homework(description, true, realExercise);
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);
		homework.setUser(user);
		for (int i = 0; i < answerStrings.length; i++) {
			System.out.println(answerStrings[i]);
			homework.addAnswer(new Answer(answerStrings[i]));
		}
		homeworksService.addHomework(homework);
		return "redirect:/homework/exercise/list";
	}

	//fhadaisbdfajsdbfkajsdf
	@RequestMapping(value = "/homework/do/test", method = RequestMethod.POST)
	public String savesHomeworkTest(Model model, @RequestParam(value = "idExercise") Long idExercise,
			@RequestParam(value = "checkAnswers[]") int[] checkAnswers, Principal principal,
			@RequestParam(value = "description", required = false) String description) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = new Homework(description, true, realExercise);
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);
		homework.setUser(user);
		for (int i = 0; i < checkAnswers.length; i++) {
			homework.addAnswer(new Answer( answerService.getById(checkAnswers[i]).getText()));
		}
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
//		Path directory = Paths.get("C:\\tfg_ltg");
//		String absRoute = directory.toFile().getAbsolutePath();
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = new Homework(description, true, realExercise);
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);
		homework.setUser(user);
		try {
			byte[] bytesImg = file.getBytes();
			File directorioAsignatura = new File("C:\\tfg_ltg//" + realExercise.getSubject().getName());
			directorioAsignatura.mkdir();
			File directorioEjercicio = new File(directorioAsignatura + "//" + realExercise.getName());
			directorioEjercicio.mkdir();
			Path completeRoute = Paths
					.get(directorioEjercicio + "//" + user.getFullName() + "_" + file.getOriginalFilename());
			Files.write(completeRoute, bytesImg);

			homework.setFile(file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		homeworksService.addHomework(homework);
		return "redirect:/homework/exercise/list";
	}

}
