package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Question;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.repositories.ExerciseShortAnswerRepository;

@Service
public class ExerciseShortAnswerService {

	@Autowired
	private ExerciseShortAnswerRepository shortAnswerRepository;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;

	public ShortAnswer addExercise(ShortAnswer shortAnswer) {
		for(Question q: shortAnswer.getQuestions()) {
			questionService.addQuestion(q);
			for(Answer a: q.getAnswers())
				answerService.addAnswer(a);
		}
		shortAnswer.setType(ExerciseType.S);
		return shortAnswerRepository.save(shortAnswer);
	}

	public List<ShortAnswer> getQuestions() {
		List<ShortAnswer> questions = new ArrayList<ShortAnswer>();
		shortAnswerRepository.findAll().forEach(questions::add);
		return questions;
	}

	public List<Question> getQuestionsByExerciseId(Long id) {
		return questionService.getQuestionsByExerciseId(id);
	}

	public ShortAnswer getExercise(Long id) {
		return shortAnswerRepository.findById(id).get();
	}

	public void deleteQuestion(Long id) {
		questionService.deleteQuestion(id);
	}

	public Exercise getExerciseByQuestionId(Long idQuestion) {
		Question q = questionService.getQuestion(idQuestion);
		return q.getExercise();
	}

}
