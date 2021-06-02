package com.uniovi.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.uniovi.entities.EmailConfig;
import com.uniovi.entities.Exercise;
import com.uniovi.entities.Homework;
import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.util.MessageParser;

@Service
public class EmailService {

	@Autowired
	private EmailConfig emailConfig;

	@Autowired
	private UsersService userService;

	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private HomeworkService homeworkService;

	Folder emailFolder;
	Store store;
	Properties properties = new Properties();

	@PostConstruct
	void setup() {
		String server = emailConfig.getHost();
		properties.put("mail.pop3.host", server);
		properties.put("mail.pop3.port", emailConfig.getPort());
		// properties.put("mail.pop3.starttls.enable", "true");
		Session emailSession = Session.getDefaultInstance(properties);
		Store store = null;
		try {
			store = emailSession.getStore("pop3s");
			store.connect(server, emailConfig.getUsername(), emailConfig.getPassword());
			emailFolder = store.getFolder("INBOX");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 5000)
	synchronized void read() throws MessagingException, IOException {
		emailFolder.open(Folder.READ_ONLY);
		Message[] messages = emailFolder.getMessages();
		for (int i = 0; i < messages.length; i++) {
			Message message = messages[i];

			// El remitente debería ser un email de un user válido de la aplicación.
			String email = ((InternetAddress) message.getFrom()[0]).getAddress();
			User user = userService.getByEmail(email);
			if (user != null && user.getRole().equals("ROLE_STUDENT")) {
				System.out.println(user);
				List<Subject> subjects = subjectService.getSubjectsByRole(user);

				if (!subjects.isEmpty()) {
					// El asunto debería ser el código de una asignatura asignada a ese usuario.
					String s = message.getSubject();
					Subject subject = subjectService.getSubjectByName(s);
					if (subject != null && subjects.contains(subject)) {
						// El cuerpo del body tiene que ser una tarea asignada al alumno de esa
						// asignatura
						String exercise = MessageParser.getMessageBody(message);
						exercise = exercise.replaceAll("(\n|\r|\t)", "");
						for (Exercise e : subject.getExercises()) {
							if (exercise.equals(e.getName())) {
								// Tiene que tener adjunto
								if (MessageParser.isMultiPart(message)) {
									Multipart multiPart = (Multipart) message.getContent();
									for (int j = 0; j < multiPart.getCount(); j++) {
										MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
										if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
											// Aquí haríamos lo necesario para gestionar los ficheros.
											// En el ejemplo se guarda en C:\\ltgtfg.
											// + subject.getName() + "\\"
											File directorioAsignatura = new File("C:\\tfg_ltg//" + s);
											directorioAsignatura.mkdir();
											File directorioEjercicio = new File(directorioAsignatura + "//" + exercise);
											directorioEjercicio.mkdir();
											part.saveFile(directorioEjercicio + "//" + user.getFullName() + "_"
													+ part.getFileName());
											System.out.println("Fichero " + part.getFileName() + " almacenado.");
											
											//Creamos el homework en la app
											Homework homework = new Homework("", true, e);
											homework.setUser(user);
											homework.setFile(user.getFullName() + "_" + part.getFileName());
											homeworkService.addHomework(homework);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		emailFolder.close();
	}
}
