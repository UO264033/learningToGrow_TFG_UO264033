package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		model.addAttribute("studentList", students);
		return "subject/add";
	}

	@RequestMapping(value = "/subject/{name}/modalSubject")
	public String getModalSubject(Model model, Principal principal, @PathVariable String name) {
		String username = principal.getName();
		User professor = usersService.getUserByUsername(username);
		Subject subject = new Subject(name, professor);
		subjectService.addSubject(subject);
		
		model.addAttribute("subject", subject);
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add";
	}

	@RequestMapping("/subject/{name}/addStudent/{idSt}")
	public String setStudents(@PathVariable String name, @PathVariable String idSt, Principal principal) {
		Subject subject = subjectService.getSubjectByName(name);
		if (subject == null) {
			String username = principal.getName();
			User professor = usersService.getUserByUsername(username);
			subject = new Subject(name, professor);
			subjectService.addSubject(subject);
		}
		String[] array = idSt.split(",");
		for(String s: array) {
			usersService.setStudent(Long.parseLong(s), name);
		}
		return "redirect:/subject/list";
	}

	@RequestMapping(value = "/subject/add/update")
	public String getSubjectUpdate(Model model) {
		model.addAttribute("subject", new Subject());
		List<User> students = usersService.getStudentsByRole("ROLE_STUDENT");
		model.addAttribute("studentList", students);
		return "subject/add :: tableUsers";
	}

	@RequestMapping(value = "/subject/add", method = RequestMethod.POST)
	public String addSubject(@RequestParam String name, Principal principal) { //
		Subject subject = subjectService.getSubjectByName(name);
		if(subject == null) {
			System.out.println("escribe un nombre anda");
		}
		
		return "redirect:/subject/list";
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
