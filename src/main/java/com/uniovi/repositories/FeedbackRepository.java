package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Subject;

public interface FeedbackRepository extends CrudRepository<Feedback, Long>{

	@Query("SELECT f FROM Feedback f WHERE f.homework = ?1")
	Feedback getFeedbackByHomeworkId(Homework h);

	@Query("SELECT f FROM Feedback f WHERE f.homework.exercise.subject = ?1")
	List<Feedback> getFeedbackBySubject(Subject subject);

}
