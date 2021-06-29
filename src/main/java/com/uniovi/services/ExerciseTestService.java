package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.Answer;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Question;
import com.uniovi.entities.TestType;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseTestRepository;

/**
 * Servicio encargado de la gestion de ejercicios de tipo test
 * 
 * @author UO264033
 *
 */
@Service
public class ExerciseTestService {

	@Autowired
	private UserService usersService;

	@Autowired
	private ExerciseTestRepository testRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	/**
	 * Añade un ejercicio de tipo test al repositorio
	 * 
	 * @param test
	 */
	public void addExercise(TestType test) {
		for (Question q : test.getQuestions()) {
			questionService.addQuestion(q);
			for (Answer a : q.getAnswers())
				answerService.addAnswer(a);
		}
		test.setType(ExerciseType.T);
		testRepository.save(test);
	}

	/**
	 * Devuelve un ejercicio de tipo test por su id
	 * 
	 * @param id
	 * @return TestType
	 */
	public TestType getExercise(Long id) {
		if (testRepository.findById(id).isPresent())
			return testRepository.findById(id).get();
		return null;
	}

	/**
	 * Configura el profesor a dicho ejercicio
	 * 
	 * @param exercise
	 */
	public void setProfesor(TestType exercise) {
		User activeUser = usersService.activeUser();
		exercise.setProfessor(activeUser);
	}
}
