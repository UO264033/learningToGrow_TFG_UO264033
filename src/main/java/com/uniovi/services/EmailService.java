package com.uniovi.services;

import java.io.IOException;
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
import com.uniovi.util.MessageParser;

@Service
public class EmailService {
	@Autowired
	private EmailConfig emailConfig;

	Folder emailFolder;
	Store store;
	Properties properties = new Properties();

	@PostConstruct
	void setup() {
		String server = emailConfig.getHost();
		properties.put("mail.pop3.host", server);
		properties.put("mail.pop3.port", emailConfig.getPort());
		//properties.put("mail.pop3.starttls.enable", "true");
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
			
			//Imprimimos el texto del mensaje como prueba.
			System.out.println(MessageParser.getMessageBody(message));
			
			
			//Pendiente: El remitente debería ser un email válido de la aplicación.
			System.out.println(((InternetAddress) message.getFrom()[0]).getAddress());
			
			
			//Pendiente: El asunto debería ser el código de una tarea asignada a ese email/usuario.
			System.out.println(message.getSubject());
			
			//Tiene que tener adjunto
			if (MessageParser.isMultiPart(message))
			{
				Multipart multiPart = (Multipart) message.getContent();
				for (int j = 0; j < multiPart.getCount(); j++) {
				    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
				    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				    	//Aquí haríamos lo necesario para gestionar los ficheros.
				    	//En el ejemplo se guarda en d:\\.
				    	part.saveFile("C:\\tfg_ltg//" + part.getFileName());
				    	System.out.println("Fichero "+ part.getFileName() + " almacenado.");
				    	
				    }
				}				
			}
			
		}
		emailFolder.close();
	}
}
