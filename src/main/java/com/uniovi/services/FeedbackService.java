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
import com.uniovi.entities.User;
import com.uniovi.repositories.FeedbackRepository;

/**
 * Servicio encargado de la gestion de las retroalimentaciones
 * 
 * @author UO264033
 *
 */
@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private HomeworkService homeworkService;

	@Autowired
	private UserService userService;

	@Autowired
	private SubjectService subjectService;

	/**
	 * Añade un objeto feedback al repositorio
	 * 
	 * @param feedback
	 */
	public void addFeedback(Feedback feedback) {
		feedbackRepository.save(feedback);
	}

	/**
	 * devuelve una lista de todas las retroalimentaciones asociadas al usuario en
	 * sesion
	 * 
	 * @param pageable
	 * @return List<Feedback>
	 */
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

	/**
	 * devuelve una lista de todas las retroalimentaciones asociadas a una
	 * asignatura cuyo id se pasa como parametro
	 * 
	 * @param id
	 * @return List<Feedback>
	 */
	public List<Feedback> getFeedbackBySubject(Long id) {
		Subject subject = subjectService.getSubject(id);
		return feedbackRepository.getFeedbackBySubject(subject);
	}

	/**
	 * devuelve una retroalimentacion por su id
	 * 
	 * @param feedbackId
	 * @return Feedback
	 */
	public Feedback getFeedback(Long feedbackId) {
		if (feedbackRepository.findById(feedbackId).isPresent())
			return feedbackRepository.findById(feedbackId).get();
		return null;
	}

	/**
	 * Añade el mensaje de respuesta a la retroalimentación
	 * 
	 * @param message
	 * @param feedback
	 */
	public void addMessage(String message, Feedback feedback) {
		feedback.setAnswer(message);
		feedback.setSend(true);
		feedbackRepository.save(feedback);
	}

	/**
	 * Elimina una retroalimentacion por la asociacion que tiene a un homework
	 * 
	 * @param homework
	 */
	public void deleteByHomework(Homework homework) {
		feedbackRepository.deleteByHomeworkId(homework);
	}

	/**
	 * Devuelve una retroalimentacion por la asociacion que tiene a un homework
	 * 
	 * @param homework
	 * @return
	 */
	public Feedback findByHomework(Homework homework) {
		return feedbackRepository.getFeedbackByHomeworkId(homework);
	}

	/**
	 * Marca una retroalimentación como enviada
	 * 
	 * @param feedback
	 */
	public void markAsSent(Feedback feedback) {
		feedback.setSend(true);
		feedbackRepository.save(feedback);
	}

	/**
	 * Crea un objeto feedback y configura sus asociaciones. Mas tarde llama al
	 * metodo feedback
	 * 
	 * @param feedback
	 * @param homework
	 */
	public void createFeedback(Feedback feedback, Homework homework) {
		feedback.setHomework(homework);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String username = auth.getName();
			User activeUser = userService.getUserByUsername(username);
			feedback.setProfessor(activeUser);
		}
		feedback.setSend(false);
		addFeedback(feedback);
		homeworkService.markAsSent(homework);
	}

	/**
	 * Devuelve una lista de retroalimentaciones en funcion del id de una subject
	 * 
	 * @param id
	 * @return
	 */
	public List<Feedback> getFeedbackBySubjectList(Long id) {
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		if (!getFeedbackBySubject(id).isEmpty()) {
			for (Feedback f : getFeedbackBySubject(id)) {
				feedbacks.add(f);
			}
		}
		return feedbacks;
	}
}
