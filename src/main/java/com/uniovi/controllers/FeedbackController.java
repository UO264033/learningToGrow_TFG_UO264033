package com.uniovi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.entities.User;
import com.uniovi.services.FeedbackService;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.UsersService;

@Controller
public class FeedbackController {
	
	@Autowired
	private HomeworkService homeworksService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value = { "/feedback/list" }, method = RequestMethod.GET)
	public String getFeedback(Model model, Pageable pageable) {
		model.addAttribute("feedbackList", feedbackService.getFeedback(pageable));
		return "feedback/list";
	}
	
	@RequestMapping(value = { "/feedback/answer" }, method = RequestMethod.POST)
	public String answerFeedback(Model model, @RequestParam(value="answer") String answer) {
		System.out.println(answer);
		return "feedback/listS";
	}
	
	@RequestMapping("/feedback/list/{id}")
	public String getFeedbackBySubject(Model model, @PathVariable Long id) {
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		if (!feedbackService.getFeedbackBySubject(id).isEmpty()) {
			for (Feedback f : feedbackService.getFeedbackBySubject(id)) {
				feedbacks.add(f);
			}
		}
		model.addAttribute("feedbackList", feedbacks);

		return "feedback/list";
	}
	
	@RequestMapping(value = "/homework/correct/shortAnswer", method = RequestMethod.POST)
	public String correctExercise(Model model, @ModelAttribute("feedback") Feedback feedback,
			@RequestParam("idHomework") Long idHomework) {

		Homework homework = homeworksService.getHomework(idHomework);
		feedback.setHomework(homework);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = usersService.getUserByUsername(username);
		feedback.setProfessor(activeUser);

		model.addAttribute("feedback", feedback);
		feedbackService.addFeedback(feedback);
		homeworksService.markAsSent(homework);
		return "homework/list";
	}
	
	

}
