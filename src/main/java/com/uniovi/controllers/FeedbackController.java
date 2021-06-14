package com.uniovi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.services.FeedbackService;
import com.uniovi.services.HomeworkService;

@Controller
public class FeedbackController {

	@Autowired
	private HomeworkService homeworksService;

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping(value = { "/feedback/list" }, method = RequestMethod.GET)
	public String getFeedback(Model model, Pageable pageable) {
		model.addAttribute("feedbackList", feedbackService.getFeedback(pageable));
		return "feedback/list";
	}

	@RequestMapping("/feedback/list/{id}")
	public String getFeedbackBySubject(Model model, @PathVariable Long id) {
		List<Feedback> feedbacks = feedbackService.getFeedbackBySubjectList(id);
		model.addAttribute("feedbackList", feedbacks);
		return "feedback/list";
	}

	@RequestMapping(value = { "/feedback/answer/{id}" }, method = RequestMethod.POST)
	public String answerFeedback(Model model, @PathVariable Long id, @RequestParam String message, Pageable pageable) {
		Feedback feedback = feedbackService.getFeedback(id);
		if (feedback != null) {
			feedbackService.addMessage(message, feedback);
			model.addAttribute("feedback", feedback);
		}
		model.addAttribute("feedbackList", feedbackService.getFeedback(pageable));
		return "feedback/list";
	}

	@RequestMapping(value = "/homework/correct", method = RequestMethod.POST)
	public String correctExerciseShortAnswer(Model model, @ModelAttribute("feedback") Feedback feedback,
			@RequestParam("idHomework") Long idHomework) {
		Homework homework = homeworksService.getHomework(idHomework);
		feedbackService.createFeedback(feedback, homework);
		model.addAttribute("feedback", feedback);
		return "redirect:/homework/list";
	}

}
