package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.repositories.SubjectRepository;

/**
 * Servicio encargado de la gestion de asignaturas
 * 
 * @author uo264033
 *
 */
@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UserService usersService;

	private Subject temporal = new Subject();
	private String[] idsStudent;

	/**
	 * Añade una asignatura al repositorio
	 * 
	 * @param subject
	 * @return
	 */
	public Subject addSubject(Subject subject) {
		return (getSubjectByName(subject.getName()) == null) ? subjectRepository.save(subject) : null;
	}

	/**
	 * Guarda una asignatura en el repositorio si es que esta no esta temporal
	 */
	public void addSubject() {
		if (getSubjectByName(temporal.getName()) == null)
			subjectRepository.save(temporal);
	}

	/**
	 * Guarda temporalmente una asignatura
	 * 
	 * @param subject
	 */
	public void addSubjectTemporal(Subject subject) {
		temporal = subject;
	}

	/**
	 * Elimina una asignatura por su id
	 * 
	 * @param id
	 */
	public void deleteSubject(Long id) {
		Subject subject = getSubject(id);
		subject.removeStudents();
		subjectRepository.save(subject);
		subjectRepository.deleteById(id);
	}

	/**
	 * Devuelve una asignatura por su id
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubject(Long id) {
		return subjectRepository.findById(id).get();
	}

	/**
	 * Devuelve una asignatura por su nombre
	 * 
	 * @param name
	 * @return
	 */
	public Subject getSubjectByName(String name) {
		return subjectRepository.findByName(name);
	}

	/**
	 * Devuelve una lista de todas las asignaturas
	 * 
	 * @return List<Subject>
	 */
	public List<Subject> getSubjects() {
		List<Subject> subjects = new ArrayList<Subject>();
		subjectRepository.findAll().forEach(subjects::add);
		return subjects;
	}

	/**
	 * Guarda una asignatura al repositorio
	 * 
	 * @param subject
	 */
	public void saveSubject(Subject subject) {
		subjectRepository.save(subject);
	}

	/**
	 * Añade a un listado de estudiantes a una asignatura
	 * 
	 * @param subject
	 * @param idSt
	 */
	public void addStudentsToASubject() {
		for (String s : idsStudent) {
			usersService.setStudent(Long.parseLong(s), temporal.getName());
		}
	}

	/**
	 * Añade a un listado de estudiantes a una asignatura de manera temporal
	 * 
	 * @param idSt
	 */
	public void addStudentsToASubjectTemporal(String idSt) {
		if (idSt != null) {
			String[] array = idSt.split(",");
			idsStudent = array;
		}
	}
	
	/**
	 * Añade a un listado de estudiantes a una asignatura
	 * 
	 * @param idSt
	 */
	public void addStudentsToASubject(Subject subject, String idSt) {
		String[] array = idSt.split(",");
		for (String s : array) {
			usersService.setStudent(Long.parseLong(s), subject.getName());
		}
	}


	/**
	 * Devuelve un lsitado de asignaturas asociadas a un usuario
	 * 
	 * @param user
	 * @return List<Subject>
	 */
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

	/**
	 * Devuelve un listado de asignaturas filtradas por texto en funcion de una
	 * palabra introducida por parametro para el username del alumno o nombre de la
	 * asignatura
	 * 
	 * @param searchText
	 * @param user
	 * @return
	 */
	public List<Subject> getSubjectsFiltered(String searchText, User user) {
		List<Subject> subjects = new ArrayList<Subject>();
		searchText = "%" + searchText + "%";
		subjectRepository.findByUsernameAndSName(searchText, user).forEach(subjects::add);
		return subjects;
	}

	/**
	 * Devuelve el array de ids de los estudiantes
	 * 
	 * @return
	 */
	public String[] getIdsStudent() {
		return idsStudent;
	}

}
