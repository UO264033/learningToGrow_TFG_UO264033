package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Subject;
import com.uniovi.repositories.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private HomeworkService homeworkService;

	@Autowired
	private UsersService userService;
	
	@Autowired
	private SubjectService subjectService;

	public void addFeedback(Feedback feedback) {
		feedbackRepository.save(feedback);
	}

	public List<Feedback> getFeedback(Pageable pageable) {
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		if (userService.getUserByUsername(username) != null) {
			Page<Homework> homeworks = homeworkService.getHomeworksForUser(pageable,
					userService.getUserByUsername(username));
			for (Homework h : homeworks.getContent()) {
				feedbackList.add(feedbackRepository.getFeedbackByHomeworkId(h));
			}
		}

		return feedbackList;
	}

	public List<Feedback> getFeedbackBySubject(Long id) {
		Subject subject = subjectService.getSubject(id);
		return feedbackRepository.getFeedbackBySubject(subject);
	}

	public Feedback getFeedback(Long feedbackId) {
		if(feedbackRepository.findById(feedbackId).isPresent())
			return feedbackRepository.findById(feedbackId).get();
		return null;
	}

	public void addMessage(String message, Feedback feedback) {
		feedback.setAnswer(message);
		feedback.setSend(true);
		feedbackRepository.save(feedback);
	}

	public void deleteByHomework(Homework homework) {
		feedbackRepository.deleteByHomeworkId(homework);
	}

	public Feedback findByHomework(Homework homework) {
		return feedbackRepository.getFeedbackByHomeworkId(homework);
	}

}
