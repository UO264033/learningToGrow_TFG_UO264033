package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Question extends BaseEntity {
		
	@Column(unique = true)
	private String name;
	private String statement;
	
	@OneToMany(mappedBy="question")
	private Set<Answer> answers = new HashSet<>();
	
	@ManyToOne
	private Exercise exercise;
	
	public Question() {
	}
	
	public Question(String name, String statement) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		ArgumentChecks.isNotNull(statement);
		this.statement = statement;
	}
	
	public Question(String name, String statement, Exercise exercise) {
		this(name, statement);
		ArgumentChecks.isNotNull(exercise);
		this.exercise = exercise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	Set<Answer> _getAnswers() {
		return answers;
	}

	public Set<Answer> getAnswers() {
		return new HashSet<>(answers);
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [name=" + name + ", statement=" + statement + "]";
	}
	
}
