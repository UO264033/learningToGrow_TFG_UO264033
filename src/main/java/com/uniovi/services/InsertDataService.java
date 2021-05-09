package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.Subject;
import com.uniovi.entities.Test;
import com.uniovi.entities.UploadFile;
import com.uniovi.entities.User;

//Sí quisieramos desactivar el servicio bastaría con eliminar la anotación etiqueta @Service
@Service
public class InsertDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private QuestionService qService;

	@Autowired
	private AnswerService aService;

	@Autowired
	private ExerciseService eService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SubjectService sService;

//	@PostConstruct
//	public void init() {
//		User user1 = new User("alumno", "Pedro", "Díaz");
//		user1.setPassword("123456");
//		user1.setRole(rolesService.getRoles()[0]);
//		User user2 = new User("99999991B", "Lucas", "Núñez");
//		user2.setPassword("123456");
//		user2.setRole(rolesService.getRoles()[0]);
//		User user3 = new User("99999992C", "María", "Rodríguez");
//		user3.setPassword("123456");
//		user3.setRole(rolesService.getRoles()[0]);
//		User user4 = new User("profesor", "Marta", "Almonte");
//		user4.setPassword("123456");
//		user4.setRole(rolesService.getRoles()[1]);
//		User user5 = new User("99999977E", "Pelayo", "Valdes");
//		user5.setPassword("123456");
//		user5.setRole(rolesService.getRoles()[1]);
//		User user6 = new User("mariagg", "María", "González");
//		user6.setPassword("Dasdas33");
//		user6.setRole(rolesService.getRoles()[2]);
//
//		usersService.addUser(user4);
//		usersService.addUser(user5);
//		usersService.addUser(user6);
//
//		Subject s1 = new Subject("Matemáticas", user4);
//		s1.addStudent(user1);
//		s1.addStudent(user2);
//		Subject s2 = new Subject("Inglés", user5);
//		s2.addStudent(user3);
//		
//		user1.addSubject(s1);
//		user1.addSubject(s2);
//		user2.addSubject(s1);
//		user1.addSubject(s2);
//		
//		sService.addSubject(s1);
//		sService.addSubject(s2);
//
//		usersService.addUser(user1);
//		usersService.addUser(user2);
//		usersService.addUser(user3);
//
//		Test e1 = new Test("E1", "Ejercicio de colores");
//		e1.setProfessor(user4);
//		e1.setSubject(s1);
//		eService.addExercise(e1);
//		Question q1 = new Question("¿De qué color es el coche?", e1);
//		qService.addQuestion(q1);
//		Answer a1 = new Answer("Azul", true, q1);
//		Answer a2 = new Answer("Verde", false, q1);
//		Answer a3 = new Answer("Rojo", false, q1);
//		aService.addAnswer(a1);
//		aService.addAnswer(a2);
//		aService.addAnswer(a3);
//
//		Exercise f1 = new UploadFile("E2", "Subidme la ficha que hicimos en clase");
//		f1.setProfessor(user4);
//		f1.setSubject(s1);
//		eService.addExercise(f1);
//
//		Exercise sA1 = new ShortAnswer("E3", "Responder a las siguientes cuestiones");
//		sA1.setProfessor(user4);
//		sA1.setSubject(s1);
//		eService.addExercise(sA1);
//		Question q2 = new Question("¿De qué color es el cielo?", sA1);
//		qService.addQuestion(q2);
//		Answer a4 = new Answer("Azul celeste", q2);
//		aService.addAnswer(a4);
//		Set<Question> questions2 = new HashSet<Question>();
//		questions2.add(q2);
//
//	}

}