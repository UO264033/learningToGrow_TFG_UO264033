package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.repositories.SubjectRepository;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	public void addSubject(Subject subject) {
		subjectRepository.save(subject);
	}
	
	public void deleteSubject(Long id) {
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
	
	public void addStudentToASubject(Subject subject, Set<User> students) {
		if(subjectRepository.findById(subject.getId()).isPresent()) {
			subject.setStudents(students);
			subjectRepository.save(subject);
			
			System.out.print("añadir");
		}
		else
			System.out.print("No se por que no puedo añadir");
	}

	public List<Subject> getSubjectsByRole(User user) {
		List<Subject> subjects = new ArrayList<Subject>();
		if(user.getRole().equals("ROLE_STUDENT"))
			user.getSubjects().forEach(subjects::add);
		else if(user.getRole().equals("ROLE_PROFESSOR"))
			subjectRepository.findByProfessor(user).forEach(subjects::add);
		return subjects;
	}

}
