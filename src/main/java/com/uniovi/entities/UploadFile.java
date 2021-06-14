package com.uniovi.entities;

import javax.persistence.Entity;

@Entity
public class UploadFile extends Exercise {

	public UploadFile() {
	}

	public UploadFile(String name, String description) {
		super(name, description, ExerciseType.U);
	}

	@Override
	public String toString() {
		return "UploadFile [getName()=" + getName() + ", getDescription()=" + getDescription() + ", getType()="
				+ getType() + ", getSubject()=" + getSubject() + ", isSend()=" + isSend() + "]";
	}

}
