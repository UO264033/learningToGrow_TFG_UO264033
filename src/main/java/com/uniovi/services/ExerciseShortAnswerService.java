package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Question;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseShortAnswerRepository;

/**
 * Servicio encargado de la gestion de ejercicios de respuesta corta
 * 
 * @author UO264033
 *
 */
@Service
public class ExerciseShortAnswerService {

	@Autowired
	private ExerciseShortAnswerRepository shortAnswerRepository;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService usersService;

	/**
	 * Añade un ejercicio de tipo test al repositorio
	 * @param shortAnswer
	 * @return ShortAnswer
	 */
	public ShortAnswer addExercise(ShortAnswer shortAnswer) {
		for(Question q: shortAnswer.getQuestions()) {
			questionService.addQuestion(q);
			for(Answer a: q.getAnswers())
				answerService.addAnswer(a);
		}
		shortAnswer.setType(ExerciseType.S);
		return shortAnswerRepository.save(shortAnswer);
	}

	/**
	 * Devuelve un listado de preguntas asociadas a un ejercicio cuyo id se pasa como parametro
	 * @param id
	 * @return List<Question>
	 */
	public List<Question> getQuestionsByExerciseId(Long id) {
		return questionService.getQuestionsByExerciseId(id);
	}

	/**
	 * Devuelve un ejercicio cuyo id es introducido como parametro
	 * @param id
	 * @return ShortAnswer
	 */
	public ShortAnswer getExercise(Long id) {
		return shortAnswerRepository.findById(id).get();
	}

	/**
	 * Elimina una pregunta de un ejercicio
	 * @param id
	 */
	public void deleteQuestion(Long id) {
		questionService.deleteQuestion(id);
	}

	/**
	 * Devuelve un ejercicio asociado a una pregunta cuyo id es pasado como parametro
	 * @param idQuestion
	 * @return Exercise
	 */
	public Exercise getExerciseByQuestionId(Long idQuestion) {
		Question q = questionService.getQuestion(idQuestion);
		return q.getExercise();
	}
	
	/**
	 * Configura el profesor a dicho ejercicio
	 * @param exercise
	 */
	public void setProfesor(ShortAnswer exercise) {
		User activeUser = usersService.activeUser();
		exercise.setProfessor(activeUser);
	}

	/**
	 * Asocia una pregunta y una respuesta a un ejercicio de respuesta corta
	 * @param model
	 * @param statement
	 * @param text
	 * @param exercise
	 */
	public void setQuestion(Model model, String statement, String text, ShortAnswer exercise) {
		Question question = new Question(statement, exercise);
		Answer answer = new Answer(text, question);
		question.addAnswer(answer);
		exercise.addQuestion(question);
	}


}
