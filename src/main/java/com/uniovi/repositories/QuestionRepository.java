package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Question;

public interface QuestionRepository extends CrudRepository<Question, Long>{

	Question findByName(String name);

	@Query("SELECT q FROM Question q WHERE q.exercise.id = ?1")
	List<Question> findQuestionsByExerciseId(Long id);
}
