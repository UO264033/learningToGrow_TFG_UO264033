package com.uniovi.controllers;

import java.util.ArrayList;
import java.util.List;

import com.uniovi.entities.User;

public class StudentsCreationSubject {
	
	private List<User> students = new ArrayList<User>();
	
	public StudentsCreationSubject() {
	}

	public void addStudent(User student) {
        this.students.add(student);
    }

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}
	
	
}
