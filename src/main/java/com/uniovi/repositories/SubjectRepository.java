package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

	Subject findByName(String name);

	@Query("SELECT s FROM Subject s WHERE s.professor = ?1")
	List<Subject> findByProfessor(User user);

	@Query("SELECT s FROM Subject s WHERE s.professor = ?2 AND LOWER(s.name) LIKE LOWER(?1)")
	List<Subject> findByUsernameAndSName(String searchText, User user);

}
