
package com.uniovi.entities;

public class Feedback extends BaseEntity{

	private Double score;
	private String comment;
	
	private Homework homework;
	
	public Feedback() {
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
