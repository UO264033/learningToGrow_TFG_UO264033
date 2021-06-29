package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.ExerciseType;
import com.uniovi.entities.UploadFile;
import com.uniovi.entities.User;
import com.uniovi.repositories.ExerciseFileUploadRepository;

/**
 * Servicio encargado de la gestion de los ejercicios de subida de fichero
 * 
 * @author UO264033
 *
 */
@Service
public class ExerciseFileUploadService {

	@Autowired
	private ExerciseFileUploadRepository exerciseRepository;

	@Autowired
	private UserService usersService;

	@Autowired
	private ExerciseService exerciseService;

	/**
	 * Añade un ejercicio de tipo de subida de fichero al repositorio
	 * 
	 * @param exercise
	 * @return UploadFile
	 */
	public UploadFile addExercise(UploadFile exercise) {
		if (exerciseService.getExerciseByName(exercise.getName()) != null)
			return null;
		exercise.setType(ExerciseType.U);
		return exerciseRepository.save(exercise);
	}

	/**
	 * Configura el profesor a dicho ejercicio
	 * 
	 * @param exercise
	 */
	public void setProfessor(UploadFile exercise) {
		User activeUser = usersService.activeUser();
		exercise.setProfessor(activeUser);
	}

}
