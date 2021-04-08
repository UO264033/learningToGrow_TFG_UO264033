package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Question;
import com.uniovi.repositories.AnswerRepository;
import com.uniovi.repositories.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void addQuestion(Question question) {
		questionRepository.save(question);
	}
	
	public List<Question> getQuestions() {
		List<Question> questions = new ArrayList<Question>();
		questionRepository.findAll().forEach(questions::add);
		return questions;
	}
	
	public List<Question> getQuestionsByExerciseId(Long id) {
		List<Question> questions = new ArrayList<Question>();
		questions = questionRepository.findQuestionsByExerciseId(id);
		return questions;
	}
	
	public Question getQuestion(Long id) {
		return questionRepository.findById(id).get();
	}

	public void deleteQuestion(Long id) {
		answerRepository.deleteByQuestionId(id);
		questionRepository.deleteById(id);
	}
	
	public Question getQuestionByName(String name) {
		return questionRepository.findByName(name);
	}
	
	

}
