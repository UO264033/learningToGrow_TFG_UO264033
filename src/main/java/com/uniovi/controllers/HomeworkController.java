package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.FormSubmitEvent.MethodType;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UsersService;

@Controller
public class HomeworkController {

	@Autowired 
	private HomeworkService homeworksService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping("/homework/list")
	public String getList(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);

		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());

		if (searchText != null && !searchText.isEmpty())
			homeworks = homeworksService.searchMarksByDescriptionAndNameForUser(pageable, searchText, user);
		else
			homeworks = homeworksService.getHomeworksForUser(pageable, user);

		model.addAttribute("homeworkList", homeworks.getContent());
		model.addAttribute("page", homeworks);

		return "homework/list";
	}

	@RequestMapping("/homework/exercise/list")
	public String getListOfExercises(Model model, Principal principal) {
		String username = principal.getName(); // Username es el name de la autenticación
		User user = usersService.getUserByUsername(username);

		List<Subject> subjects = new ArrayList<>();
		subjects = subjectService.getSubjectsByRole(user);
		List<Exercise> homeworks = new ArrayList<>();
		for (Subject s : subjects) {
			if (!exerciseService.getExercisesBySubject(s.getId()).isEmpty())
				;
			for (Exercise e : exerciseService.getExercisesBySubject(s.getId())) {
				homeworks.add(e);
			}
		}
		model.addAttribute("exerciseList", homeworks);

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

	@RequestMapping(value = "/homework/add")
	public String getHomework(Model model, Pageable pageable) {
		model.addAttribute("usersList", usersService.getUsers(pageable).getContent());
		return "homework/add";
	}

	@RequestMapping(value = "/homework/add", method = RequestMethod.POST)
	public String sethomework(@ModelAttribute Homework homework) {
		homeworksService.addHomework(homework);
		return "redirect:/homework/list";
	}

	@RequestMapping(value = "/homework/correct")
	public String getHomeworkCorrect(Model model, Pageable pageable) {
		model.addAttribute("usersList", usersService.getUsers(pageable).getContent());
		return "homework/correct";
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
	
	@RequestMapping("/homework/do/{id}")
	public String doHomework(Model model, @PathVariable Long id) {
		Exercise exercise = exerciseService.getExercise(id);
		model.addAttribute("exercise", exercise);
		if(exercise.getType() == ExerciseType.T)
			return "homework/do/test";
		else if(exercise.getType() == ExerciseType.S)
			return "homework/do/shortAnswer";
		else if(exercise.getType() == ExerciseType.U )
			return "homework/do/uploadFile";
		return "homework/do";
	}
	
	@RequestMapping(value= "/homework/save", method = RequestMethod.POST)
	public String saveHomework(Model model, @RequestParam Long idExercise, @ModelAttribute Homework homework, @RequestParam boolean corrected) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
//		homeworksService.saveExerciseDone(homework, idExercise, answer);
//		System.out.println(homework + "" + idExercise + "" + answer);
		
//		homeworksService.compareExercise(exercise, realExercise);
//		model.addAttribute("exercise", exercise);
//		model.addAttribute("questionList", questions);
		return "redirect:/homework/exercise/list";
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
	
	@RequestMapping(value="/homework/do/shortAnswer", method = RequestMethod.POST)
	public String savesHomeworkShortAnswer(Model model, @RequestParam(value = "idExercise") Long idExercise) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = new Homework("hola", true, realExercise);		
		homeworksService.addHomework(homework);
		return "homework/exercise/list";
	}
	
	@RequestMapping(value="/homework/do/test", method = RequestMethod.POST)
	public String savesHomeworkTest(Model model, @RequestParam(value = "idExercise") Long idExercise) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = new Homework("hola", true, realExercise);		
		homeworksService.addHomework(homework);
		return "homework/exercise/list";
	}
	
	@RequestMapping(value="/homework/do/uploadFile", method = RequestMethod.POST)
	public String savesHomeworkUploadFile(Model model, @RequestParam(value = "idExercise") Long idExercise) {
		Exercise realExercise = exerciseService.getExercise(idExercise);
		Homework homework = new Homework("hola", true, realExercise);		
		homeworksService.addHomework(homework);
		return "homework/exercise/list";
	}

}
