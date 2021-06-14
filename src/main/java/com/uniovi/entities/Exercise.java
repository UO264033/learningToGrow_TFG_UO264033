package com.uniovi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.uniovi.util.ArgumentChecks;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Exercise extends BaseEntity {

	@Column(unique = true)
	private String name;
	private String description;

	@ManyToOne
	@JoinColumn(name = "profesor_id")
	private User professor; // PROFESOR

	@ManyToOne
	private Subject subject;

	private ExerciseType type;

	private boolean send;
	private boolean done;

	public Exercise() {
	}

	public Exercise(String name, String description, ExerciseType type) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		ArgumentChecks.isNotNull(description);
		this.description = description;
		ArgumentChecks.isNotNull(type);
		this.type = type;
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

	public ExerciseType getType() {
		return type;
	}

	public void setType(ExerciseType u) {
		this.type = u;
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

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
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
		return "Exercise [name=" + name + ", description=" + description + ", type= " + type + "]";
	}

}
