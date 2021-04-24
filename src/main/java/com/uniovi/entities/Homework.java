package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Homework extends BaseEntity {

	private String description;

	private Boolean send = false;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // ALUMNO

	@OneToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise; // EJERCICIO

	@OneToMany
	@JoinColumn(name = "homework_id")
	private Set<Answer> answers = new HashSet<>(); // RESPUESTAS
	
	private String file;

	public Homework() {
	}

	public Homework(String description, boolean send, Exercise exercise) {
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
		this.send = send;
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
	}

	public Homework(String description, boolean send, Exercise exercise, Set<Answer> answers) {
		this(description, send, exercise);
		this.answers = answers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean isSent() {
		return send;
	}

	public void setSend(Boolean resend) {
		this.send = resend;
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Homework [description=" + description + ", exercise=" + exercise + "]";
	}

}
