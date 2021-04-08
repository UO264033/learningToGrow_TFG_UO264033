package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UsersService;

@Controller
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/subject/list")
	public String getSubjects(Model model, Principal principal) {
		String username = principal.getName();
		User user = usersService.getUserByUsername(username);
		List<Subject> subjects = subjectService.getSubjectsByRole(user);
		model.addAttribute("subjectList", subjects);
		return "subject/list";
	}

	@RequestMapping(value = "/subject/add")
	public String getSubject(Model model) {
		model.addAttribute("subject", new Subject());
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		StudentsCreationSubject studentsToAdd = new StudentsCreationSubject();
		model.addAttribute("studentList", students);
		model.addAttribute("form", studentsToAdd);
		return "subject/add";
	}

	@RequestMapping(value = "/subject/add", method = RequestMethod.POST)
	public String addSubject(@ModelAttribute Subject subject, @ModelAttribute StudentsCreationSubject form,
			Principal principal) { //
		String username = principal.getName();
		User professor = usersService.getUserByUsername(username);

		subject.setProfessor(professor);
		for (User user : form.getStudents())
			System.out.print(user);

		// por cada fila de student
		// si está include a true
		// añadir student a la lista de alumnos de la asignatura

		subjectService.addSubject(subject);
		return "subject/list";
	}

	@RequestMapping("/subject/delete/{id}")
	public String getDelete(@PathVariable Long id) {
		subjectService.deleteSubject(id);
		return "redirect:/subject/list";
	}

	@RequestMapping("/subject/details/{id}")
	public String getDetails(Model model, @PathVariable Long id) {
		Subject subject = subjectService.getSubject(id);
		model.addAttribute("subject", subject);
		model.addAttribute("studentList", usersService.getUsersBySubject(subject));
		User professor = usersService.getUser(subject.getProfessor().getId());
		model.addAttribute("user", professor);
		return "subject/details";
	}

}
