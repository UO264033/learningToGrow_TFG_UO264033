package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Question;
import com.uniovi.repositories.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;
	
	public void addAnswers(List<Answer> answers) {
		for (Answer a : answers) {
			answerRepository.save(a);
		}
	}
	
	public void addAnswer(Answer answer) {
		answerRepository.save(answer);
	}

	public void addAnswer(Answer answer, Question question) {
		System.out.println("aaa" + question);
//		Optional<Question> q = questionRepository.findById(question.getId());
//		if(q.isPresent())
//			answer._setQuestion(q.get());
		System.out.println("hola" + answer.toString());
		answerRepository.save(answer);
	}
	
	public Answer getQuestionByAll(String text, boolean correct, Question question) {
		return answerRepository.findByTextAndCorrectAndQuestion(text, correct, question);
	}

	public void deleteByQuestionId(Long id) {
		answerRepository.deleteByQuestionId(id);
	}

}
