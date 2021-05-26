package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Homework;
import com.uniovi.entities.User;
import com.uniovi.repositories.HomeworkRepository;

@Service
public class HomeworkService {

	@Autowired
	private HomeworkRepository homeworkRepository;

	@Autowired
	private AnswerService answerService;

	public Page<Homework> getHomeworks(Pageable pageable) {
		Page<Homework> homeworks = homeworkRepository.findAll(pageable);
		return homeworks;
	}

	public Homework getHomework(Long id) {
		return homeworkRepository.findById(id).get();
	}

	public void addHomework(Homework homework) {
		if (!homework.getAnswers().isEmpty()) {
			for (Answer answer : homework.getAnswers())
				answerService.addAnswer(answer);
		}
		homeworkRepository.save(homework);
	}

	public void deleteHomework(Long id) {
		homeworkRepository.deleteById(id);
	}

	public void setHomeworkResend(boolean revised, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Homework homework = homeworkRepository.findById(id).get();
		if (homework.getUser().getUsername().equals(username)) {
			homeworkRepository.updateResend(revised, id);
		}

	}

	public Page<Homework> getHomeworksForUser(Pageable pageable, User user) {
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		if (user.getRole().equals("ROLE_STUDENT")) {
			homeworks = homeworkRepository.findAllByUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			homeworks = getHomeworks(pageable);
		}
		return homeworks;
	}

	public Page<Homework> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchText, User user) {
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		searchText = "%" + searchText + "%";
		if (user.getRole().equals("ROLE_STUDENT")) {
			homeworks = homeworkRepository.findByDescriptionNameAndUser(pageable, searchText, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			homeworks = homeworkRepository.findByDescriptionAndName(pageable, searchText);
		}
		return homeworks;
	}

	public Page<Homework> getHomeworksToCorrect(Pageable pageable, User user) {
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		homeworks = homeworkRepository.findByProfessor(pageable, user);
		return homeworks;
	}

	public String[] differentMarks() {
		String[] s = { "Ejercicio sin nota", "Sin hacer",
				"Necesitas esforzarte un poco más pero, seguro que lo consigues ¡A por ello!",
				"Estás trabajando muy bien pero aún hay que fijarse un poco más", "Vas por el buen camino, ¡sigue así!",
				"Muy bien trabajo, ¡enhorabuena!" };
		return s;
	}

	public List<Answer> correct(List<Answer> correctAnswers, Set<Answer> answers) {
		List<Answer> answersList = new ArrayList<Answer>();
		answersList.addAll(answers);
		for (int i = 0; i < answers.size(); i++) {
			if (answersList.get(i).equals(correctAnswers.get(i)))
				correctAnswers.get(i).setCorrect(true);
			else
				correctAnswers.get(i).setCorrect(false);
		}
		return correctAnswers;
	}

	public void markAsSent(Homework homework) {
		homework.setSend(true);
		homeworkRepository.save(homework);
	}

}
