package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Exercise extends BaseEntity{

	@Column(unique = true)
	private String name;
	private String description;
	
	/*
	 * Questions tipo test
	 */
	@OneToMany(mappedBy="exercise")
	private Set<Question> questions = new HashSet<>();
	
	/*
	 * Questions para short Answer
	 */
	@OneToMany(mappedBy="exercise")
	private Set<ShortAnswer> shortAnswers = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "profesor_id")
	private User professor; //PROFESOR
	
	@ManyToOne
	private Subject subject;

	public Exercise() {
	}
	
	public Exercise(String name, String description) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		ArgumentChecks.isNotNull(description);
		this.description = description;
	}
	
	public Exercise(String name, String description, User professor) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		ArgumentChecks.isNotNull(description);
		this.description = description;
		ArgumentChecks.isNotNull(professor);
		this.professor = professor;
	}
	
	public Exercise(String name, String description, User professor, Subject subject) {
		this(name, description, professor);
		ArgumentChecks.isNotNull(subject);
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	Set<Question> _getQuestions() {
		return questions;
	}

	public Set<Question> getQuestions() {
		return new HashSet<>(questions);
	}
	
	Set<ShortAnswer> _getShortAnswers() {
		return shortAnswers;
	}

	public Set<ShortAnswer> getShortAnswers() {
		return new HashSet<>(shortAnswers);
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
		Exercise other = (Exercise) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Exercise [name=" + name + ", description=" + description + "]";
	}
	
}
