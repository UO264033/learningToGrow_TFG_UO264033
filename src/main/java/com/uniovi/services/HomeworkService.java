package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Homework;
import com.uniovi.entities.User;
import com.uniovi.repositories.HomeworkRepository;

@Service
public class HomeworkService {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private HomeworkRepository homeworkRepository;

	public Page<Homework> getHomeworks(Pageable pageable) {
		Page<Homework> homeworks = homeworkRepository.findAll(pageable);
		return homeworks;
	}

	public Homework getHomework(Long id) {
		return homeworkRepository.findById(id).get();
	}

	public void addHomework(Homework homework) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		homeworkRepository.save(homework);
	}

	public void deleteHomework(Long id) {
		homeworkRepository.deleteById(id);
	}

	public Homework getMark(Long id) {
		Set<Homework> consultedList = (Set<Homework>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Homework>();
		}
		Homework obtainedmark = homeworkRepository.findById(id).get();
		consultedList.add(obtainedmark);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedmark;
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

}
