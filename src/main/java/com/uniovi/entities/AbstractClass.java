package com.uniovi.entities;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

//@Entity
//@Table
//@Inheritance( strategy = InheritanceType.JOINED)
public abstract class AbstractClass extends BaseEntity {
	
	@Column(unique = true)
	private String name;
	
	@ManyToOne
	private Exercise exercise;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		AbstractClass other = (AbstractClass) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractClass [name=" + name + "]";
	}
	

	 public abstract boolean canPay(double amount);
}
