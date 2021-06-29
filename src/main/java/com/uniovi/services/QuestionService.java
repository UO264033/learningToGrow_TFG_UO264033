package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.entities.TestType;
import com.uniovi.repositories.QuestionRepository;

/**
 * Servicio encargado de gestión de las preguntas
 * 
 * @author UO264033
 *
 */
@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private ExerciseService exerciseService;

	/**
	 * Añade una pregunta al repositorio
	 * 
	 * @param question
	 * @return Question
	 */
	public Question addQuestion(Question question) {
		Question q = questionRepository.save(question);
		for (Answer a : question.getAnswers())
			answerService.addAnswer(a);
		return q;
	}

	/**
	 * Añade una pregunta de tipo test con sus diferentes respuestas
	 * 
	 * @param model
	 * @param statement
	 * @param texto1
	 * @param texto2
	 * @param texto3
	 * @param correct1
	 * @param correct2
	 * @param correct3
	 * @param exercise
	 * @return Question
	 */
	public Question addNewQuestion(String statement, String texto1, String texto2, String texto3, boolean correct1,
			boolean correct2, boolean correct3, Exercise exercise) {
		Question q = new Question(statement, exercise);
		q.addAnswer(new Answer(texto1, correct1, q));
		q.addAnswer(new Answer(texto2, correct2, q));
		q.addAnswer(new Answer(texto3, correct3, q));

		q = addQuestion(q);
		exerciseService.addExercise((TestType) exercise);
		return q;
	}

	/**
	 * Devuelve todas las preguntas del repositorio
	 * 
	 * @return List<Question>
	 */
	public List<Question> getQuestions() {
		List<Question> questions = new ArrayList<Question>();
		questionRepository.findAll().forEach(questions::add);
		return questions;
	}

	/**
	 * Devuelve una lista de preguntas asociadas a un ejercicio cuyo id es
	 * introducido como parametro
	 * 
	 * @param id
	 * @return List<Question>
	 */
	public List<Question> getQuestionsByExerciseId(Long id) {
		List<Question> questions = new ArrayList<Question>();
		questions = questionRepository.findQuestionsByExerciseId(id);
		return questions;
	}

	/**
	 * Devuelve una pregunta por su id
	 * 
	 * @param id
	 * @return Question
	 */
	public Question getQuestion(Long id) {
		return questionRepository.findById(id).get();
	}

	/**
	 * Elimina una pregunta por su id
	 * 
	 * @param id
	 */
	public void deleteQuestion(Long id) {
		answerService.deleteByQuestionId(id);
		questionRepository.deleteById(id);
	}

}
