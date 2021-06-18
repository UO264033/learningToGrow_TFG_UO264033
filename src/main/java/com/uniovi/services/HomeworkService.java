package com.uniovi.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
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
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.Subject;
import com.uniovi.entities.Test;
import com.uniovi.entities.User;
import com.uniovi.repositories.HomeworkRepository;

@Service
public class HomeworkService {

	@Autowired
	private HomeworkRepository homeworkRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private UserService userService;

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
		homework.setSend(true);
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
		for (Homework homework : homeworks.getContent()) {
			Feedback feedback = feedbackService.findByHomework(homework);
			if (feedback == null)
				homework.setSend(false);
			else if (feedback != null && !feedback.isSend())
				homework.setSend(true);
			else if (feedback != null && feedback.isSend())
				homework.setSend(true);
		}
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

	public void deleteByExerciseId(Exercise exercise) {
		Homework homework = homeworkRepository.findByExercise(exercise);
		if (homework != null) {
			if (homework.getAnswers() != null) {
				for (Answer a : homework.getAnswers()) {
					answerService.delete(a);
				}
			}
			feedbackService.deleteByHomework(homework);
			homeworkRepository.deleteByExercise(exercise);
		}

	}

	public List<Exercise> getListOfExercises(String username) {
		User user = userService.getUserByUsername(username);
		List<Subject> subjects = subjectService.getSubjectsByRole(user);
		List<Exercise> homeworks = new ArrayList<>();
		for (Subject s : subjects) {
			if (!exerciseService.getExercisesBySubject(s.getId()).isEmpty())
				;
			for (Exercise exercise : exerciseService.getExercisesBySubject(s.getId())) {
				Homework homework = homeworkRepository.findByExerciseAndUser(exercise, user);
				if (homework != null && homework.isSent()) {
					exercise.setSend(true);
				}
				homeworks.add(exercise);
			}
		}

		return homeworks;
	}

	public Homework saveShortAnswers(String[] answerStrings, Principal principal, String description,
			Exercise realExercise) {
		Homework homework = new Homework(description, true, realExercise);
		String username = principal.getName(); // Username es el name de la autenticación
		User user = userService.getUserByUsername(username);
		homework.setUser(user);
		for (int i = 0; i < answerStrings.length; i++) {
			homework.addAnswer(new Answer(answerStrings[i]));
		}
		return homework;
	}

	public Homework saveAnswersTest(int[] checkAnswers, Principal principal, String description,
			Exercise realExercise) {
		if (checkAnswers != null) {
			Homework homework = new Homework(description, true, realExercise);
			String username = principal.getName(); // Username es el name de la autenticación
			User user = userService.getUserByUsername(username);
			homework.setUser(user);
			for (int i = 0; i < checkAnswers.length; i++) {
				homework.addAnswer(new Answer(answerService.getById(checkAnswers[i]).getText()));
			}
			return homework;
		}
		return null;
	}

	public Homework saveFiles(MultipartFile file, String description, Principal principal, Exercise realExercise) {
		Homework homework = new Homework(description, true, realExercise);
		String username = principal.getName(); // Username es el name de la autenticación
		User user = userService.getUserByUsername(username);
		homework.setUser(user);
		try {
			byte[] bytesImg = file.getBytes();
			File directorioAsignatura = new File("C:\\tfg_ltg//" + realExercise.getSubject().getName());
			directorioAsignatura.mkdir();
			File directorioEjercicio = new File(directorioAsignatura + "//" + realExercise.getName());
			directorioEjercicio.mkdir();
			Path completeRoute = Paths
					.get(directorioEjercicio + "//" + user.getFullName() + "_" + file.getOriginalFilename());
			Files.write(completeRoute, bytesImg);

			homework.setFile(user.getFullName() + "_" + file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return homework;
	}

	public List<Exercise> listOfExercisesBySubject(Long id) {
		List<Exercise> homeworks = new ArrayList<>();
		if (!exerciseService.getExercisesBySubject(id).isEmpty()) {
			for (Exercise e : exerciseService.getExercisesBySubject(id)) {
				homeworks.add(e);
			}
		}
		return homeworks;
	}

	public List<Answer> correctTest(Homework homework) {
		List<Answer> correctAnswers = new ArrayList<Answer>();
		Test exercise = (Test) homework.getExercise();
		List<Answer> answers;
		for (int k = 0; k < exercise.getQuestions().size(); k++) {
			answers = exercise.getQuestions().get(k).getAnswers();
			for (int j = 0; j < answers.size(); j++) {
				if (answers.get(j).isCorrect()) {
					correctAnswers.add(answers.get(j));
				}
			}
		}
		return correctAnswers;
	}

	public List<Answer> correctShortAnswer(Homework homework) {
		List<Answer> correctAnswers = new ArrayList<Answer>();
		ShortAnswer exercise = (ShortAnswer) homework.getExercise();
		for (int i = 0; i < exercise.getQuestions().size(); i++) {
			correctAnswers.addAll(exercise.getQuestions().get(i).getAnswers());
		}
		return correctAnswers;
	}

	public Page<Homework> homeworkList(Pageable pageable, User user, String searchText) {
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		if (searchText != null && !searchText.isEmpty())
			homeworks = getHomeworksToCorrectFiltered(pageable, user, searchText);
		else
			homeworks = getHomeworksToCorrect(pageable, user);
		return homeworks;
	}

	public Page<Homework> getHomeworksToCorrectFiltered(Pageable pageable, User user, String searchText) {
		Page<Homework> homeworks = new PageImpl<Homework>(new LinkedList<Homework>());
		homeworks = homeworkRepository.findByProfessorFiltered(pageable, user, searchText);
		for (Homework homework : homeworks.getContent()) {
			Feedback feedback = feedbackService.findByHomework(homework);
			if (feedback == null)
				homework.setSend(false);
			else if (feedback != null && !feedback.isSend())
				homework.setSend(true);
			else if (feedback != null && feedback.isSend())
				homework.setSend(true);
		}
		return homeworks;
	}

	private int[] idsAnswers;

	public void saveStudentAnswer(int[] checkAnswers) {
		if (checkAnswers != null)
			this.setIdsAnswers(checkAnswers);

		System.out.println("2.-  " + checkAnswers);
	}

	public int[] getIdsAnswers() {
		return idsAnswers;
	}

	public void setIdsAnswers(int[] idsAnswers) {
		this.idsAnswers = idsAnswers;
	}

}
