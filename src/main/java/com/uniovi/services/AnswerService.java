package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.Answer;
import com.uniovi.entities.Question;
import com.uniovi.repositories.AnswerRepository;

/**
 * Servicio encargado de gestión de las respuestas
 * 
 * @author UO264033
 *
 */
@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	/**
	 * Añade una lista de respuestas al repositorio
	 * 
	 * @param answers
	 */
	public void addAnswers(List<Answer> answers) {
		for (Answer a : answers) {
			answerRepository.save(a);
		}
	}

	/**
	 * Añade una respuesta al repositorio
	 * 
	 * @param answer
	 */
	public void addAnswer(Answer answer) {
		answerRepository.save(answer);
	}

	/**
	 * Devuelve una respuesta cuyo id es el introducido como parametro
	 * 
	 * @param checkAnswers
	 * @return Answer
	 */
	public Answer getById(int id) {
		if (answerRepository.findById((long) id).isPresent())
			return answerRepository.findById((long) id).get();
		return null;
	}

	/**
	 * Devuelve una respuesta a partir de introducir una pregunta
	 * 
	 * @param text
	 * @param correct
	 * @param question
	 * @return Answer
	 */
	public Answer getQuestionByAll(String text, boolean correct, Question question) {
		return answerRepository.findByTextAndCorrectAndQuestion(text, correct, question);
	}

	/**
	 * Elimina una respuesta relacionada con el id de una pregunta (introducido como
	 * parametro)
	 * 
	 * @param id
	 */
	public void deleteByQuestionId(Long id) {
		answerRepository.deleteByQuestionId(id);
	}

	/**
	 * Elimina una respuesta del repositorio
	 * 
	 * @param answer
	 */
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}

}
