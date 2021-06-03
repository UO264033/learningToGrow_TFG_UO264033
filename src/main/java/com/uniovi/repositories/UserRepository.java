package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (LOWER(u.username) LIKE LOWER(?1) OR LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.role) LIKE LOWER(?1))")
	Page<User> findByUsernameAndLastNameAndName(Pageable pageable, String searchtext);

	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.role = ?1")
	List<User> findStudentsByRole(String role);

	@Query("SELECT u FROM User u WHERE u.role = ?1")
	Page<User> findUsersByRole(Pageable pageable, String role);

	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1)) AND u.role = ?2")
	Page<User> findStudentsByNameAndLastname(Pageable pageable, String searchText, String role);

	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.username) LIKE LOWER(?1)) AND u.role = ?2")
	List<User> findStudentsFiltered(String searchText, String string);

}
