package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;

public interface FeedbackRepository extends CrudRepository<Feedback, Long>{

	@Query("SELECT f FROM Feedback f WHERE f.homework = ?1")
	Feedback getFeedbackByHomeworkId(Homework h);

}
