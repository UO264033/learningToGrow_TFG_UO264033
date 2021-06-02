package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.uniovi.util.ArgumentChecks;

@Entity
public class User {

	@Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true)
	private String username;
	private String name;
	private String lastName;
	private String email;

	private String role;

	private String password;
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	/**
	 * Atributo del subsistema de cursos
	 */
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Set<Subject> subjectsTaught = new HashSet<Subject>();
	
	@ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
	private Set<Subject> subjects = new HashSet<Subject>();


	/**
	 * Atributos del subsistema de deberes
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Homework> homeworks = new HashSet<Homework>();

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Set<Exercise> exercises = new HashSet<Exercise>();
	
	
	/**
	 * Atributos del subsistema de retroalimentación
	 */
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Set<Feedback> feedback = new HashSet<Feedback>();

	public User(String username, String name, String lastName, String email) {
		super();
		ArgumentChecks.isNotNull(username);
		this.username = username;
		ArgumentChecks.isNotNull(name);
		this.name = name;
		ArgumentChecks.isNotNull(lastName);
		this.lastName = lastName;
		ArgumentChecks.isNotNull(email);
		this.email = email;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Homework> getHomeworks() {
		return homeworks;
	}

	public void setHomeworks(Set<Homework> homeworks) {
		this.homeworks = homeworks;
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	Set<Subject> _getSubjects() {
		return subjects;
	}

	public Set<Subject> getSubjects() {
		return new HashSet<>(subjects);
	}
	
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	
	public void addSubject(Subject s) {
		subjects.add(s);
	}
	
	public void removeSubject(Subject s) {
		subjects.remove(s);
	}
	
	public String getRoleString() {
		if (role.equals("ROLE_STUDENT"))
			return "student";
		else if (role.equals("ROLE_PROFESSOR"))
			return "professor";
		else if (role.equals("ROLE_ADMIN"))
			return "administrator";
		return "";
	}

	public String getNameAndSurname() {
		return name + " " + lastName;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", lastName=" + lastName + ", role=" + role + "]";
	}
}
