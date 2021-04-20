package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class ShortAnswer extends Exercise {

	@OneToMany(mappedBy="exercise")
	private Set<Question> questions = new HashSet<>();

	public ShortAnswer() {
	}

	public ShortAnswer(String name, String description) {
		super(name, description, ExerciseType.S);
	}
		
	public ShortAnswer(String name, String description, Set<Question> questions) {
		this(name, description);
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

	@Override
	public String toString() {
		return "ShortAnswer [questions=" + questions + "]";
	}
	
	

}
