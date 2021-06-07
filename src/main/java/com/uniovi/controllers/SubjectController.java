package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UserService;
import com.uniovi.validators.SubjectValidator;

@Controller
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UserService usersService;

	@Autowired
	private SubjectValidator subjectValidator;

	@RequestMapping(value = "/subject/list")
	public String getSubjects(Model model, Principal principal) {
		String username = principal.getName();
		User user = usersService.getUserByUsername(username);
		List<Subject> subjects = subjectService.getSubjectsByRole(user);
		model.addAttribute("subjectList", subjects);
		return "subject/list";
	}

	@RequestMapping(value = "/subject/list", method = RequestMethod.POST)
	public String searchSubjects(Model model, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String username = principal.getName();
		User user = usersService.getUserByUsername(username);
		List<Subject> subjects = new ArrayList<Subject>();
		if (searchText != null && !searchText.isEmpty())
			subjects = subjectService.getSubjectsFiltered(searchText, user);
		else
			subjects = subjectService.getSubjectsByRole(user);
		model.addAttribute("subjectList", subjects);
		return "subject/list";
	}

	@RequestMapping(value = "/subject/add")
	public String getSubject(Model model) {
		model.addAttribute("subject", new Subject());
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add";
	}
	
	@RequestMapping(value = "/subject/add/{message}") //COMO VALIDAR??
	public String getSubjectWithMessage(Model model, @PathVariable String message) {
		model.addAttribute("subject", new Subject());
		model.addAttribute("message", message);
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add";
	}

	@RequestMapping(value = "/addStudent/{idSt}", method = RequestMethod.GET)
	public String addSubject(Model model, @Validated Subject subject, BindingResult result) {
		System.out.println("hola");
		String message =  "Debes añadir un nombre a la asignatura.";
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add/" + message;
	}

	@RequestMapping("/subject/{name}/addStudent/{idSt}")
	public String setStudents(Model model, @PathVariable String name, @PathVariable String idSt, Principal principal) {
		Subject subject = subjectService.getSubjectByName(name);
		if (subject == null) {
			String username = principal.getName();
			User professor = usersService.getUserByUsername(username);
			subject = new Subject(name, professor);
			subjectService.addSubject(subject);
		}
		subjectService.addStudentsToASubject(subject, idSt);
		return "redirect:/subject/list";
	}

	@RequestMapping(value = "/subject/add/update")
	public String getSubjectUpdate(Model model) {
		model.addAttribute("subject", new Subject());
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add :: tableUsers";
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
