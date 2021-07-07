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

/**
 * Servicio encargado de la gestion de todo tipo de ejercicios
 * 
 * @author UO264033
 *
 */
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

	/**
	 * Añade un ejercicio al repositorio
	 * 
	 * @param exercise
	 */
	public void addExercise(Exercise exercise) {
		exerciseRepository.save(exercise);
	}

	/**
	 * Devuelve una lista de todos los ejercicios existentes
	 * 
	 * @return List<Exercise>
	 */
	public List<Exercise> getExercises() {
		List<Exercise> exercises = new ArrayList<Exercise>();
		exerciseRepository.findAll().forEach(exercises::add);
		return exercises;
	}

	/**
	 * Devuelve un ejercicio por su id
	 * 
	 * @param id
	 * @return Exercise
	 */
	public Exercise getExercise(Long id) {
		return exerciseRepository.findById(id).get();
	}

	/**
	 * Elimina todas las relaciones de un ejercicio, además de eliminarse a el mismo
	 * 
	 * @param id
	 */
	public void deleteExercise(Long id) {
		List<Question> questions = questionService.getQuestionsByExerciseId(id);
		for (Question q : questions)
			questionService.deleteQuestion(q.getId());
		if (exerciseRepository.findById(id).isPresent()) {
			Exercise exercise = exerciseRepository.findById(id).get();
			homeworkService.deleteByExerciseId(exercise);
			exercise.setProfessor(null);
			exercise.setSubject(null);
			exercise.setName(null);
			exerciseRepository.save(exercise);
			exerciseRepository.delete(exercise);
		}
	}
	
	/**
	 * Devuelve un ejercicio por su nombre
	 * 
	 * @param name
	 * @return
	 */
	public Exercise getExerciseByName(String name) {
		return exerciseRepository.findByName(name);
	}

	/**
	 * Devuelve un listado de ejercicios concretos a una asignatura, cuyo id se
	 * introduce como parametro
	 * 
	 * @param id
	 * @return List<Exercise>
	 */
	public List<Exercise> getExercisesBySubject(Long id) {
		List<Exercise> exercises = new ArrayList<Exercise>();
		if (subjectRepository.findById(id).isPresent())
			exerciseRepository.findBySubject(subjectRepository.findById(id).get()).forEach(exercises::add);
		return exercises;
	}

	/**
	 * Devuelve un listado de ejercicios asociados a un usuario
	 * 
	 * @param pageable
	 * @param activeUser
	 * @param searchText
	 * @return Page<Exercise>
	 */
	public Page<Exercise> getExercisesByUser(Pageable pageable, User activeUser, String searchText) {
		Page<Exercise> exercises = new PageImpl<Exercise>(new LinkedList<Exercise>());
		if (searchText != null && !searchText.isEmpty()) {
			searchText = "%" + searchText + "%";
			exercises = exerciseRepository.findByUserFiltered(pageable, activeUser, searchText);
		} else
			exercises = exerciseRepository.findByUser(pageable, activeUser);
		return exercises;
	}

	/**
	 * Marca un ejercicio como enviado
	 * 
	 * @param realExercise
	 */
	public void markAsSend(Exercise realExercise) {
		realExercise.setSend(true);
		exerciseRepository.save(realExercise);
	}

}
