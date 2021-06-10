package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Homework;
import com.uniovi.entities.User;

public interface HomeworkRepository extends CrudRepository<Homework, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE Homework SET resend = ?1 WHERE id = ?2")
	void updateResend(Boolean resend, Long id);

	@Query("SELECT h FROM Homework h WHERE h.user = ?1 ORDER BY h.id ASC ")
	Page<Homework> findAllByUser(Pageable pageable, User user);

	@Query("SELECT h FROM Homework h WHERE (LOWER(h.description) LIKE LOWER(?1) OR LOWER(h.user.name) LIKE LOWER(?1))")
	Page<Homework> findByDescriptionAndName(Pageable pageable, String searchtext);

	@Query("SELECT h FROM Homework h WHERE (LOWER(h.description) LIKE LOWER(?1) OR LOWER(h.user.name) LIKE LOWER(?1)) AND h.user = ?2")
	Page<Homework> findByDescriptionNameAndUser(Pageable pageable, String searchText, User user);

	Page<Homework> findAll(Pageable pageable);

	@Query("SELECT h FROM Homework h WHERE h.exercise.professor = ?1 ORDER BY h.id ASC ")
	Page<Homework> findByProfessor(Pageable pageable, User user);

	@Transactional
	@Modifying
	@Query("DELETE FROM Homework h WHERE h.exercise = ?1 ")
	void deleteByExercise(Exercise exercise);

	@Query("SELECT h FROM Homework h WHERE h.exercise = ?1")
	Homework findByExercise(Exercise exercise);

	@Query("SELECT h FROM Homework h WHERE h.exercise = ?1 AND h.user= ?2")
	Homework findByExerciseAndUser(Exercise exercise, User user);

	@Query("SELECT h FROM Homework h WHERE h.exercise.professor = ?1 AND (h.user.name = ?2 OR h.exercise.name = ?2 OR h.exercise.subject.name = ?2) ORDER BY h.id ASC ")
	Page<Homework> findByProfessorFiltered(Pageable pageable, User user, String searchText);


}
