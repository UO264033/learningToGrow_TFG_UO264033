package com.uniovi.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class TestType extends Exercise {

	@OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER)
	private List<Question> questions = new ArrayList<>();

	public TestType() {
	}

	public TestType(String name, String description) {
		super(name, description, ExerciseType.T);
	}

	public TestType(String name, String description, List<Question> questions) {
		super(name, description, ExerciseType.T);
		ArgumentChecks.isNotNull(questions);
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return new ArrayList<>(questions);
	}

	public Set<Question> getQuestionsSet() {
		return new HashSet<>(questions);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question q) {
		questions.add(q);
	}

	@Override
	public String toString() {
		return "Test [questions=" + questions + ", getName()=" + getName() + ", getDescription()=" + getDescription()
				+ ", getType()=" + getType() + ", getSubject()=" + getSubject() + ", isSend()=" + isSend() + "]";
	}

}
