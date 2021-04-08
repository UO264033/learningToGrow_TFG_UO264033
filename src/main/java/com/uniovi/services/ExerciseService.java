package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Exercise;
import com.uniovi.repositories.ExerciseRepository;
import com.uniovi.repositories.SubjectRepository;

@Service
public class ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;

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

	public void deleteExercise(Long id) {
		exerciseRepository.deleteById(id);
	}

	public Exercise getExerciseByName(String name) {
		return exerciseRepository.findByName(name);
	}
	
	public List<Exercise> getExercisesBySubject(Long id) {
		List<Exercise> exercises = new ArrayList<Exercise>();
		if(subjectRepository.findById(id).isPresent())
			exerciseRepository.findBySubject(subjectRepository.findById(id).get()).forEach(exercises::add);
		return exercises;
	}

}
