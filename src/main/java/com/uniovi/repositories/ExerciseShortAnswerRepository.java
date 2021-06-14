package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.ShortAnswer;

public interface ExerciseShortAnswerRepository extends CrudRepository<ShortAnswer, Long> {

	@Query("SELECT q FROM ShortAnswer q WHERE q.id = ?1")
	List<ShortAnswer> findQuestionsByExerciseId(Long id);
}
