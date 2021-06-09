package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.Question;
import com.uniovi.entities.Test;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseTestRepository;

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
	
	public void addExercise(Test test) {
		for(Question q: test.getQuestions()) {
			questionService.addQuestion(q);
			for(Answer a: q.getAnswers())
				answerService.addAnswer(a);
		}
		test.setType(ExerciseType.T);
		testRepository.save(test);
	}

	public Test getExercise(Long id) {
		if(testRepository.findById(id).isPresent())
			return testRepository.findById(id).get();
		return null;
	}

	public void setProfesor(Test exercise) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		exercise.setProfessor(activeUser);
	}
}
