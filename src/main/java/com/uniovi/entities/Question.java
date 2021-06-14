package com.uniovi.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Question extends BaseEntity {

	private String statement;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private List<Answer> answers = new ArrayList<>();

	@ManyToOne
	private Exercise exercise;

	public Question() {
	}

	public Question(String statement) {
		ArgumentChecks.isNotNull(statement);
		this.statement = statement;
	}

	public Question(String statement, Exercise exercise) {
		this(statement);
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	List<Answer> _getAnswers() {
		return answers;
	}

	public List<Answer> getAnswers() {
		return new ArrayList<>(answers);
	}

	public Set<Answer> getAnswersSet() {
		return new HashSet<>(answers);
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public void addAnswer(Answer a) {
		answers.add(a);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [statement=" + statement + ", answers=" + answers + "]";
	}

}
