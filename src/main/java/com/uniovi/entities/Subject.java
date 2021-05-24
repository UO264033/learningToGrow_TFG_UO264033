package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Subject extends BaseEntity{
	
	@Column(unique = true)
	private String name;

	@ManyToOne
	private User professor;

	@JoinTable(name = "rel_subjects_students",
			joinColumns = @JoinColumn(name = "FK_SUBJECT", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "FK_STUDENT", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<User> students = new HashSet<User>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private Set<Exercise> exercises = new HashSet<Exercise>();

	public Subject() {
	}

	public Subject(String name) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}

	public Subject(String name, User professor) {
		this(name);
		ArgumentChecks.isNotNull(professor);
		this.professor = professor;
	}

	public Subject(String name, User professor, Set<User> students) {
		this(name, professor);
		this.students = students;
	}

	public Subject(String name, User professor, Set<User> students, Set<Exercise> exercises) {
		this(name, professor, students);
		this.exercises = exercises;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public Set<User> getStudents() {
		return students;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	public void addStudent(User student) {
		students.add(student);
	}
	
	public void removeStudent(User student) {
		students.remove(student);
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
		Subject other = (Subject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + "]";
	}

	public void removeStudents() {
		students = new HashSet<User>();
	}

}
