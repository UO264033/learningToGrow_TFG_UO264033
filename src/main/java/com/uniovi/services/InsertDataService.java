package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Question;
import com.uniovi.entities.Subject;
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
//		Set user1Homeworks = new HashSet<Homework>() {
//			{
//				add(new Homework("Nota A1", 10.0, user1));
//				add(new Homework("Nota A2", 9.0, user1));
//				add(new Homework("Nota A3", 7.0, user1));
//				add(new Homework("Nota A4", 6.5, user1));
//			}
//		};
//		user1.setHomeworks(user1Homeworks);
//		Set user2Homeworks = new HashSet<Homework>() {
//			{
//				add(new Homework("Nota B1", 5.0, user2));
//				add(new Homework("Nota B2", 4.3, user2));
//				add(new Homework("Nota B3", 8.0, user2));
//				add(new Homework("Nota B4", 3.5, user2));
//			}
//		};
//		user2.setHomeworks(user2Homeworks);
//		Set user3Homeworks = new HashSet<Homework>() {
//			{
//				;
//				add(new Homework("Nota C1", 5.5, user3));
//				add(new Homework("Nota C2", 6.6, user3));
//				add(new Homework("Nota C3", 7.0, user3));
//			}
//		};
//		user3.setHomeworks(user3Homeworks);
//		Set user4Homeworks = new HashSet<Homework>() {
//			{
//				add(new Homework("Nota D1", 10.0, user4));
//				add(new Homework("Nota D2", 8.0, user4));
//				add(new Homework("Nota D3", 9.0, user4));
//			}
//		};
//		user4.setHomeworks(user4Homeworks);
////		usersService.addUser(user1);
//		usersService.addUser(user2);
//		usersService.addUser(user3);
//		usersService.addUser(user4);
//		usersService.addUser(user5);
//		usersService.addUser(user6);
//		
//		Exercise e1 = new Exercise("E1", "Ejercicio de colores", user4);
//		Question q1 = new Question("P1", "¿De qué color es el coche?");
//		Answer a1 = new Answer("Azul", true, q1);
//		Answer a2 = new Answer("Verde", false, q1);
//		Answer a3 = new Answer("Rojo", false, q1);
//		
//		eService.addExercise(e1);
//		qService.addQuestion(q1);
//		aService.addAnswer(a1);
//		aService.addAnswer(a2);
//		aService.addAnswer(a3);
//		
//		Subject s1 = new Subject("Matemáticas", user4);
//		Subject s2 = new Subject("Inglés", user5);
//		Set<Subject> subjects = new HashSet<Subject>() {
//			{
//				add(s1);
//			}
//			
//		};
//		user1.setSubjects(subjects);
//		usersService.addUser(user1);
//		sService.addSubject(s1);
//		sService.addSubject(s2);
//}
		
}