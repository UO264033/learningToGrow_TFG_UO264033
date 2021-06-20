package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.UploadFile;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseFileUploadRepository;

@Service
public class ExerciseFileUploadService {
	
	@Autowired
	private ExerciseFileUploadRepository exerciseRepository;
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private ExerciseService exerciseService;
	
	public UploadFile addExercise(UploadFile exercise) {
		if(exerciseService.getExerciseByName(exercise.getName())!= null)
			return null;
		exercise.setType(ExerciseType.U);
		return exerciseRepository.save(exercise);
	}
	
	public void setProfessor(UploadFile exercise) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		exercise.setProfessor(activeUser);
	}

}
