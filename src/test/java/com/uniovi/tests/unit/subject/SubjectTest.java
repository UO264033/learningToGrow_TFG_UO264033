package com.uniovi.tests.unit.subject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.services.SubjectService;
import com.uniovi.services.UserService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectTest {
	
	@Autowired
    private SubjectService subjectService;
	
	@Autowired
    private UserService userService;
	
	@Test
	public void pr01AddSubjectValid() {
		Subject subject = new Subject("Plástica");
		User pedro = userService.getUserByUsername("alumno");
		User lucas = userService.getUserByUsername("lucasnunez");
		User lucia = userService.getUserByUsername("garcialucia");
		String idStudents = String.valueOf(pedro.getId()) + "," + String.valueOf(lucas.getId()) + "," + String.valueOf(lucia.getId());
		subjectService.addSubject(subject);
		subjectService.addStudentsToASubject(subject, idStudents);
		
		assertNotNull(subjectService.getSubjectByName("Plástica"));
		Subject s = subjectService.getSubjectByName("Plástica");
		System.out.println(subject);
		System.out.println(pedro);
		System.out.println(lucas);
		assertTrue(userService.getUserByUsername("alumno").getSubjects().contains(s));
		assertTrue(userService.getUserByUsername("lucasnunez").getSubjects().contains(s));
		assertTrue(userService.getUserByUsername("garcialucia").getSubjects().contains(s));
	}
	
	@Test
	public void pr02AddSubjectInvalid() {
		Subject subject = new Subject("Plástica");
		assertNull(subjectService.addSubject(subject));
	}
	
	@Test
	public void pr03DeleteSubject() {
		Subject subject = subjectService.getSubjectByName("Plástica");
		subjectService.deleteSubject(subject.getId());
		
		assertNull(subjectService.getSubjectByName("Plástica"));
		
	}

}
