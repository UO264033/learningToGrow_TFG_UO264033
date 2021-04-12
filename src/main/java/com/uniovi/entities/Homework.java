package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Homework extends BaseEntity {

	private String description;
	private Double score;
	
	private Boolean resend = false;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; //ALUMNO
	
	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise; //EJERCICIO
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question; //PREGUNTA
	
	@ManyToOne
	@JoinColumn(name = "answer_id")
	private Answer answer; //RESPUESTA

	public Homework() {
	}

	public Homework(long id, String description, Double score) {
		super();
//		this.id = id;
		this.description = description;
		this.score = score;

	}

	public Homework(String description, Double score, User user) {
		super();
		this.description = description;
		this.score = score;
		this.user = user;
	}
	
	public Homework(String description, Exercise exercise, Question question, Answer aswner) {
		this.description = description;
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
		ArgumentChecks.isNotNull(question);
		this.question = question;
		ArgumentChecks.isNotNull(answer);
		this.answer = answer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getResend() {
		return resend;
	}

	public void setResend(Boolean resend) {
		this.resend = resend;
	}

	@Override
	public String toString() {
		return "Homework [description=" + description + ", exercise=" + exercise + "]";
	}

	

}
