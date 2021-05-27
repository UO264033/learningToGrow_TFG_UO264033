package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Question;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Answer WHERE question.id = ?1")
	void deleteByQuestionId(Long id);

	@Query("SELECT a FROM Answer a WHERE (LOWER(a.text) LIKE LOWER(?1) AND a.correct = ?2 AND a.question = ?3)")
	Answer findByTextAndCorrectAndQuestion(String text, boolean correct, Question question);

}
