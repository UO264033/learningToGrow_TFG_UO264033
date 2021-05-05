package com.uniovi.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.uniovi.util.ArgumentChecks;

@Entity
public class Answer extends BaseEntity {

	@Basic(optional = false)
	private String text;

	private boolean correct;

	@ManyToOne
	private Question question;

	public Answer() {
	}

	public Answer(String text) {
		ArgumentChecks.isNotEmpty(text);
		this.text = text;
	}
	
	public Answer(String text, boolean right) {
		this(text);
		this.correct = right;
	}

	public Answer(String text, boolean right, Question question) {
		ArgumentChecks.isNotEmpty(text);
		this.text = text;
		this.correct = right;
		ArgumentChecks.isNotNull(question);
		this.question = question;
	}
	
	public Answer(String text, Question question) {
		ArgumentChecks.isNotEmpty(text);
		this.text = text;
		ArgumentChecks.isNotNull(question);
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean right) {
		this.correct = right;
	}

	public Question getQuestion() {
		return question;
	}

	public void _setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Answer other = (Answer) obj;
		if (correct != other.correct)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [text=" + text + ", correct=" + correct + "]";
	}
}
