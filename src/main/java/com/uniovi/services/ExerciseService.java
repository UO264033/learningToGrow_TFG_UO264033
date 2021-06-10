package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Question;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseRepository;
import com.uniovi.repositories.SubjectRepository;

@Service
public class ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private HomeworkService homeworkService;

	public void addExercise(Exercise exercise) {
		exerciseRepository.save(exercise);
	}

	public List<Exercise> getExercises() {
		List<Exercise> exercises = new ArrayList<Exercise>();
		exerciseRepository.findAll().forEach(exercises::add);
		return exercises;
	}

	public Exercise getExercise(Long id) {
		return exerciseRepository.findById(id).get();
	}

	public void deleteInsideExercise(Long id) {
		List<Question> questions = questionService.getQuestionsByExerciseId(id);
		for (Question q : questions)
			questionService.deleteQuestion(q.getId());
		if (exerciseRepository.findById(id).isPresent()) {
			Exercise exercise = exerciseRepository.findById(id).get();
			homeworkService.deleteByExerciseId(exercise);
//			exerciseRepository.deleteById(id);
		}
	}

	public void deleteExercise(Long id) {
		if (exerciseRepository.findById(id).isPresent()) {
			Exercise exercise = exerciseRepository.findById(id).get();
			exerciseRepository.delete(exercise);
		}
	}

	public Exercise getExerciseByName(String name) {
		return exerciseRepository.findByName(name);
	}

	public List<Exercise> getExercisesBySubject(Long id) {
		List<Exercise> exercises = new ArrayList<Exercise>();
		if (subjectRepository.findById(id).isPresent())
			exerciseRepository.findBySubject(subjectRepository.findById(id).get()).forEach(exercises::add);
		return exercises;
	}

	public Page<Exercise> getExercisesByUser(Pageable pageable, User activeUser, String searchText) {
		Page<Exercise> exercises = new PageImpl<Exercise>(new LinkedList<Exercise>());
		if (searchText != null && !searchText.isEmpty()) {
			searchText = "%" + searchText + "%";
			exercises = exerciseRepository.findByUserFiltered(pageable, activeUser, searchText);
		}
		else
			exercises = exerciseRepository.findByUser(pageable, activeUser);
		return exercises;
	}

	public void markAsSend(Exercise realExercise) {
		realExercise.setSend(true);
		exerciseRepository.save(realExercise);
	}

}
