package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Homework;
import com.uniovi.entities.User;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.UserService;

@Controller
public class HomeworkController {

	@Autowired
	private HomeworkService homeworksService;

	@Autowired
	private UserService usersService;

	@RequestMapping("/homework/list")
	public String getList(Model model, Pageable pageable, Principal principal, @RequestParam(value= "", required = false) String searchText) {
		String username = principal.getName(); 
		User user = usersService.getUserByUsername(username);
		Page<Homework> homeworks = homeworksService.homeworkList(pageable, user, searchText);
		model.addAttribute("homeworkList", homeworks.getContent());
		model.addAttribute("page", homeworks);
		return "homework/list";
	}

	@RequestMapping("/homework/exercise/list")
	public String getListOfExercises(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("exerciseList", homeworksService.getListOfExercises(username));
		return "homework/exercise/list";
	}

	@RequestMapping("/homework/exercise/list/{id}")
	public String getListOfExercisesBySubject(Model model, @PathVariable Long id) {
		List<Exercise> homeworks = homeworksService.listOfExercisesBySubject(id);
		model.addAttribute("exerciseList", homeworks);
		return "homework/exercise/list";
	}

	@RequestMapping("/homework/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal) {
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);
		Page<Homework> homeworks = homeworksService.getHomeworksForUser(pageable, user);
		model.addAttribute("homeworkList", homeworks.getContent());
		return "homework/list :: tableHomework";
	}

	@RequestMapping("/homework/delete/{id}")
	public String getDelete(@PathVariable Long id) {
		homeworksService.deleteHomework(id);
		return "redirect:/homework/list";
	}

	@RequestMapping("/homework/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("homework", homeworksService.getHomework(id));
		return "homework/details";
	}

	@RequestMapping("/homework/correct/{id}")
	public String correctHomework(Model model, @PathVariable Long id) {
		Homework homework = homeworksService.getHomework(id);
		model.addAttribute("homework", homework);
		model.addAttribute("markList", homeworksService.differentMarks());
		if (homework.getExercise().getType() == ExerciseType.T) {
			List<Answer> correctAnswers = homeworksService.correctTest(homework);
			model.addAttribute("correctAnswers", correctAnswers);
			return "homework/correct/test";
		} else if (homework.getExercise().getType() == ExerciseType.S) {
			List<Answer> correctAnswers = homeworksService.correctShortAnswer(homework);
			model.addAttribute("correctAnswers", correctAnswers);
			return "homework/correct/shortAnswer";
		} else if (homework.getExercise().getType() == ExerciseType.U)
			return "homework/correct/uploadFile";
		return "homework/correct";
	}
}
