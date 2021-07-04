package com.uniovi.tests.unit.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.entities.Answer;
import com.uniovi.entities.Feedback;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Question;
import com.uniovi.entities.ShortAnswer;
import com.uniovi.entities.TestType;
import com.uniovi.entities.UploadFile;
import com.uniovi.entities.User;
import com.uniovi.services.ExerciseFileUploadService;
import com.uniovi.services.ExerciseService;
import com.uniovi.services.ExerciseShortAnswerService;
import com.uniovi.services.ExerciseTestService;
import com.uniovi.services.FeedbackService;
import com.uniovi.services.HomeworkService;
import com.uniovi.services.UserService;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkManagementTest {

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private ExerciseTestService testService;

	@Autowired
	private ExerciseShortAnswerService shortAnswerService;

	@Autowired
	private ExerciseFileUploadService uploadFileService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private HomeworkService homeworkService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Test
	public void pr01AddTestExerciseValid() {
		Question q = new Question("P1");
		q.addAnswer(new Answer("A1", true));
		q.addAnswer(new Answer("A2", false));
		q.addAnswer(new Answer("A3", false));
		TestType test1 = new TestType("Ejercicio de tipo test ejemplo", "Este es un ejemplo de ejercicio de tipo test");
		test1.addQuestion(q);

		testService.addExercise(test1);

		assertNotNull(exerciseService.getExerciseByName("Ejercicio de tipo test ejemplo"));
	}

	@Test
	public void pr02AddUploadFileExerciseValid() {
		UploadFile uploadFile1 = new UploadFile("Ejercicio de subida de fichero ejemplo",
				"Este es un ejemplo de ejercicio de subida de fichero");

		uploadFileService.addExercise(uploadFile1);

		assertNotNull(exerciseService.getExerciseByName("Ejercicio de subida de fichero ejemplo"));
	}

	@Test
	public void pr03AddShortAnswerExerciseValid() {
		Question q = new Question("Pregunta");
		q.addAnswer(new Answer("Respuesta"));
		ShortAnswer shortAnswer1 = new ShortAnswer("Ejercicio de respuesta corta ejemplo",
				"Este es un ejemplo de ejercicio de respuesta cortao");
		shortAnswer1.addQuestion(q);
		shortAnswerService.addExercise(shortAnswer1);

		assertNotNull(exerciseService.getExerciseByName("Ejercicio de respuesta corta ejemplo"));
	}

	@Test
	public void pr04AddExerciseDuplicatedValid() {
		UploadFile uploadFile1 = new UploadFile("Ejercicio de subida de fichero ejemplo",
				"Este es un ejemplo de ejercicio de subida de fichero");


		assertNull(uploadFileService.addExercise(uploadFile1));
	}

	@Test
	public void pr05deleteExercise() {
		exerciseService.deleteExercise(exerciseService.getExerciseByName("Ejercicio de subida de fichero ejemplo").getId());

		assertNull(exerciseService.getExerciseByName("Ejercicio de subida de fichero ejemplo"));
	}
	
	@Test
	public void pr06DoShortAnswerExercise() {
		ShortAnswer realExercise = (ShortAnswer) exerciseService.getExerciseByName("Ejercicio de respuesta corta ejemplo");
		User pedro = userService.getUserByUsername("alumno");
		Homework homework = new Homework("Comentario del alumno", true, realExercise);
		homework.setUser(pedro);
		homework.addAnswer(new Answer("Respuesta"));
		
		homeworkService.addHomework(homework);

		org.springframework.data.domain.Pageable pageable = null;
		assertNotNull(homeworkService.getHomeworksForUser(pageable, pedro).getContent());
		assertEquals(homework, homeworkService.getHomeworksForUser(pageable, pedro).getContent().get(0));
	}
	
	@Test
	public void pr07DoTestExercise() {
		TestType realExercise = (TestType) exerciseService.getExerciseByName("Ejercicio de tipo test ejemplo");
		User pedro = userService.getUserByUsername("alumno");
		Homework homework = new Homework("Comentario del alumno", true, realExercise);
		homework.setUser(pedro);
		homework.addAnswer(new Answer("A2"));
		
		homeworkService.addHomework(homework);

		org.springframework.data.domain.Pageable pageable = null;
		assertNotNull(homeworkService.getHomeworksForUser(pageable, pedro).getContent());
		assertEquals(homework, homeworkService.getHomeworksForUser(pageable, pedro).getContent().get(1));
	}
	
	@Test
	public void pr08CorrectExercise() {
		ShortAnswer realExercise = (ShortAnswer) exerciseService.getExerciseByName("Ejercicio de respuesta corta ejemplo");
		User pedro = userService.getUserByUsername("alumno");
		User professor = userService.getUserByUsername("profesor");
		Homework homework = homeworkService.getHomeworkByExerciseAndUser(realExercise, pedro);
		Feedback feedback = new Feedback("Ejemplo de retroalimentación", "Sin hacer");
		feedback.setProfessor(professor);
		feedbackService.createFeedback(feedback, homework);
		

		assertNotNull(feedbackService.findByHomework(homework));
		assertEquals(feedback, feedbackService.findByHomework(homework));
	}
	
	@Test
	public void pr09AnswerFeedback() {
		ShortAnswer realExercise = (ShortAnswer) exerciseService.getExerciseByName("Ejercicio de respuesta corta ejemplo");
		User pedro = userService.getUserByUsername("alumno");
		Homework homework = homeworkService.getHomeworkByExerciseAndUser(realExercise, pedro);
		Feedback feedback = feedbackService.findByHomework(homework);
		feedbackService.addMessage("Ejemplo de mensaje", feedback);

		assertEquals("Ejemplo de mensaje", feedbackService.findByHomework(homework).getAnswer());
	}

	
	
}
