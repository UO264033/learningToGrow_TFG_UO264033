package com.uniovi.tests.unit.user;

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

import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	@Autowired
    private UserService userService;
	
	@Test
	public void pr01AddUserValid() {
		User user1 = new User("alumnoEjemplo", "alumno", "ejemplo", "alumno@gmail.com");
		user1.setPassword("Pollito88");
		user1.setRole("ROLE_STUDENT");
		userService.addUser(user1);
		assertNotNull(userService.getUserByUsername("alumnoEjemplo"));
	}
	
	@Test
	public void pr02AddUserInvalid() {
		User user1 = new User("alumnoEjemplo", "alumno", "ejemplo", "alumno@gmail.com");
		user1.setPassword("Pollito88");
		user1.setRole("ROLE_STUDENT");
		userService.addUser(user1);
		
		assertNull(userService.addUser(user1));
	}
	
	@Test
	public void pr03AddUserInvalid() {
		User user1 = new User("jose", "J", "P", "jose.com");
		user1.setPassword("pollito");
		user1.setRole("ROLE_STUDENT");
		userService.addUser(user1);
		
		assertNull(userService.getUserByUsername("jose"));
	}
	
	@Test
	public void pr04EditUserValid() {
		User user = userService.getUserByUsername("alumnoEjemplo");
		User u = new User("EjemploAlumno", "Alumno", "Ejemplo", "ejemplo@alumno.com");
		u.setPassword("Pepito99");
		userService.editUser(u, user.getId());
		
		assertNotNull(userService.getUserByUsername("EjemploAlumno"));
		assertEquals("Alumno", userService.getUserByUsername("EjemploAlumno").getName());
		assertEquals("Ejemplo", userService.getUserByUsername("EjemploAlumno").getLastName());
		assertEquals("ejemplo@alumno.com", userService.getUserByUsername("EjemploAlumno").getEmail());
	}
	
	@Test
	public void pr05DeleteUserValid() {
		userService.deleteUser(userService.getUserByUsername("EjemploAlumno").getId());
		
		assertNull(userService.getUserByUsername("EjemploAlumno"));
	}

}
