package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.entities.Test;
import com.uniovi.repositories.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	public Question addQuestion(Question question) {
		Question q = questionRepository.save(question);
		for(Answer a: question.getAnswers())
			answerService.addAnswer(a);
		return q;
	}
	
	public Question addNewQuestion(Model model, String statement, String texto1, String texto2, String texto3,
			boolean correct1, boolean correct2, boolean correct3, Exercise exercise) {
		Question q = new Question(statement, exercise);
		q.addAnswer(new Answer(texto1, correct1, q));
		q.addAnswer(new Answer(texto2, correct2, q));
		q.addAnswer(new Answer(texto3, correct3, q));
		model.addAttribute("question", q);
		q = addQuestion(q);
		exerciseService.addExercise((Test)exercise);
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
