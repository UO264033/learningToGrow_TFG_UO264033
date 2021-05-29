package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.repositories.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UsersService usersService;

	public void addSubject(Subject subject) {
		subjectRepository.save(subject);
	}

	public void deleteSubject(Long id) {
		Subject subject = getSubject(id);
		subject.removeStudents();
		subjectRepository.save(subject);
		subjectRepository.deleteById(id);
	}

	public Subject getSubject(Long id) {
		return subjectRepository.findById(id).get();
	}

	public Subject getSubjectByName(String name) {
		return subjectRepository.findByName(name);
	}

	public List<Subject> getSubjects() {
		List<Subject> subjects = new ArrayList<Subject>();
		subjectRepository.findAll().forEach(subjects::add);
		return subjects;
	}

	public void addStudentsToASubject(Subject subject, String idSt) {
		String[] array = idSt.split(",");
		for (String s : array) {
			usersService.setStudent(Long.parseLong(s), subject.getName());
		}
	}

	public List<Subject> getSubjectsByRole(User user) {
		List<Subject> subjects = new ArrayList<Subject>();
		if (user != null) {
			if (user.getRole().equals("ROLE_STUDENT"))
				user.getSubjects().forEach(subjects::add);
			else if (user.getRole().equals("ROLE_PROFESSOR"))
				subjectRepository.findByProfessor(user).forEach(subjects::add);
		}
		return subjects;
	}

	public List<Subject> getSubjectsFiltered(String searchText, User user) {
		List<Subject> subjects = new ArrayList<Subject>();
		searchText = "%" + searchText + "%";
		subjectRepository.findByUsernameAndSName(searchText, user).forEach(subjects::add);
		return subjects;
	}

}
