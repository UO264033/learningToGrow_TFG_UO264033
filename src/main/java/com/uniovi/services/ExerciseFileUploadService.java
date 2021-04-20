package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.UploadFile;
import com.uniovi.repositories.ExerciseFileUploadRepository;

@Service
public class ExerciseFileUploadService {
	
	@Autowired
	private ExerciseFileUploadRepository exerciseRepository;
	
	public void addExercise(UploadFile exercise) {
		exercise.setType(ExerciseType.U);
		exerciseRepository.save(exercise);
	}


}
