
package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Feedback extends BaseEntity {

	private String comment;
	private String score;
	private boolean send;
	private String answer;

	@OneToOne
	@JoinColumn(name = "homework_id")
	private Homework homework;

	@ManyToOne
	private User professor;

	public Feedback() {
	}

	public Feedback(String comment, String score) {
		ArgumentChecks.isNotNull(comment);
		this.comment = comment;
		ArgumentChecks.isNotNull(score);
		this.score = score;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
		Feedback other = (Feedback) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feedback [comment=" + comment + ", score=" + score + ", homework=" + homework + ", professor="
				+ professor + "]";
	}

}
