package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Subject;
import com.uniovi.entities.User;

public interface ExerciseRepository  extends CrudRepository<Exercise, Long>  {

	Exercise findByName(String name);

	@Query("SELECT e FROM Exercise e WHERE e.subject = ?1")
	List<Exercise> findBySubject(Subject id);

	@Query("SELECT e FROM Exercise e WHERE e.professor = ?1")
	Page<Exercise> findByUser(Pageable pageable, User activeUser);

}