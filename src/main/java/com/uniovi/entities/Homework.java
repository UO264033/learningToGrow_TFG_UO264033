package com.uniovi.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Homework extends BaseEntity {

	private String description;
	private boolean send;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // ALUMNO

	@OneToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise; // EJERCICIO

	@OneToMany
	@JoinColumn(name = "homework_id")
	private List<Answer> answers = new ArrayList<>(); // RESPUESTAS

	private String file;

	public Homework() {
	}

	public Homework(String description, boolean send, Exercise exercise) {
		this.description = description;
		this.send = send;
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
	}

	public Homework(String description, boolean send, Exercise exercise, List<Answer> answers) {
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

	public boolean isSent() {
		return send;
	}

	public void setSend(boolean resend) {
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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void deleteAnswer(Answer answer) {
		answers.remove(answer);
	}

	@Override
	public String toString() {
		return "Homework [description=" + description + ", exercise=" + exercise + "]";
	}

}
