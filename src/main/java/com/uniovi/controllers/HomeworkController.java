package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Homework;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.Test;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.UsersService;

@Controller
public class HomeworkController {

	@Autowired
	private HomeworkService homeworksService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping("/homework/list")
	public String getList(Model model, Pageable pageable, Principal principal) {
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		homeworks = homeworksService.getHomeworksToCorrect(pageable, user);

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

		List<Exercise> homeworks = new ArrayList<>();
		if (!exerciseService.getExercisesBySubject(id).isEmpty()) {
			for (Exercise e : exerciseService.getExercisesBySubject(id)) {
				homeworks.add(e);
			}
		}
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

	@RequestMapping(value = "/homework/edit/{id}")
	public String getEdit(Model model, Pageable pageable, @PathVariable Long id) {
		model.addAttribute("homework", homeworksService.getHomework(id));
		model.addAttribute("usersList", usersService.getUsers(pageable).getContent());
		return "homework/edit";
	}

	@RequestMapping(value = "/homework/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Homework homework) {
		Homework original = homeworksService.getHomework(id);
		// modificar solo score y description
		original.setDescription(homework.getDescription());
		homeworksService.addHomework(original);
		return "redirect:/homework/details/" + id;
	}

	@RequestMapping(value = "/homework/{id}/resend", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		homeworksService.setHomeworkResend(true, id);
		return "redirect:/homework/list";
	}

	@RequestMapping(value = "/homework/{id}/noresend", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		homeworksService.setHomeworkResend(false, id);
		return "redirect:/homework/list";
	}

	@RequestMapping("/homework/correct/{id}")
	public String correctHomework(Model model, @PathVariable Long id) {
		Homework homework = homeworksService.getHomework(id);
		model.addAttribute("homework", homework);
		model.addAttribute("markList", homeworksService.differentMarks());
		if (homework.getExercise().getType() == ExerciseType.T) {
			List<Answer> correctAnswers = new ArrayList<Answer>();
			Test exercise = (Test) homework.getExercise();
			for (int i = 0; i < exercise.getQuestions().size(); i++) {
				correctAnswers.addAll(exercise.getQuestions().get(i).getAnswers());
			}
			model.addAttribute("correctAnswers", correctAnswers);
			return "homework/correct/test";
		}
		else if (homework.getExercise().getType() == ExerciseType.S) {
			List<Answer> correctAnswers = new ArrayList<Answer>();
			ShortAnswer exercise = (ShortAnswer) homework.getExercise();
			for (int i = 0; i < exercise.getQuestions().size(); i++) {
				correctAnswers.addAll(exercise.getQuestions().get(i).getAnswers());
			}
			model.addAttribute("correctAnswers", correctAnswers);
			return "homework/correct/shortAnswer";
		} else if (homework.getExercise().getType() == ExerciseType.U)
			return "homework/correct/uploadFile";
		return "homework/correct";
	}

}
