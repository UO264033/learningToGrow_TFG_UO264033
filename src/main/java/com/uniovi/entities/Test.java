package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Test extends Exercise {

	@OneToMany(mappedBy = "exercise")
	private Set<Question> questions = new HashSet<>();

	public Test() {
	}

	public Test(String name, String description) {
		super(name, description, ExerciseType.T);
	}

	public Test(String name, String description, Set<Question> questions) {
		super(name, description, ExerciseType.T);
		ArgumentChecks.isNotNull(questions);
		this.questions = questions;
	}

	Set<Question> _getQuestions() {
		return questions;
	}

	public Set<Question> getQuestions() {
		return new HashSet<>(questions);
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}

}
