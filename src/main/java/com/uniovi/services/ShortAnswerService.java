package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.repositories.ExerciseRepository;
import com.uniovi.repositories.ShortAnswerRepository;

@Service
public class ShortAnswerService {
	
	@Autowired
	private ShortAnswerRepository shortAnswerRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	public void addQuestion(ShortAnswer shortAnswer) {
		shortAnswerRepository.save(shortAnswer);
	}
	
	public List<ShortAnswer> getQuestions() {
		List<ShortAnswer> questions = new ArrayList<ShortAnswer>();
		shortAnswerRepository.findAll().forEach(questions::add);
		return questions;
	}
	
	public List<ShortAnswer> getQuestionsByExerciseId(Long id) {
		List<ShortAnswer> questions = new ArrayList<ShortAnswer>();
		questions = shortAnswerRepository.findQuestionsByExerciseId(id);
		return questions;
	}
	
	public ShortAnswer getQuestion(Long id) {
		return shortAnswerRepository.findById(id).get();
	}

	public void deleteQuestion(Long id) {
		shortAnswerRepository.deleteById(id);
	}

	public Exercise getExerciseByQuestionId(Long id) {
		ShortAnswer sh = shortAnswerRepository.findById(id).get();
		return exerciseRepository.findById(sh.getExercise().getId()).get();
	}
	
}
