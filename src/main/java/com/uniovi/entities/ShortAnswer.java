package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class ShortAnswer extends BaseEntity {

	private String statement;
	private String answer;
	
	@ManyToOne
	private Exercise exercise;

	public ShortAnswer() {
	}

	public ShortAnswer(String statement) {
		ArgumentChecks.isNotEmpty(statement);
		this.statement = statement;
	}
	
	public ShortAnswer(String statement, String answer) {
		this(statement);
		ArgumentChecks.isNotEmpty(answer);
		this.answer = answer;
	}
	
	public ShortAnswer(String statement, String answer, Exercise exercise) {
		this(statement, answer);
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
	}


	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	@Override
	public String toString() {
		return "ShortAnswer [statement=" + statement + ", answer=" + answer + "]";
	}

	
}
