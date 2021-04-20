package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Question;
import com.uniovi.repositories.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerService answerService;
	
	public Question addQuestion(Question question) {
		Question q = questionRepository.save(question);
		for(Answer a: question.getAnswers())
			answerService.addAnswer(a);
		return q;
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
		answerService.deleteByQuestionId(id);
		questionRepository.deleteById(id);
	}
	
}
