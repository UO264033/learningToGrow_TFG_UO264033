package com.uniovi.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uniovi.entities.Exercise;
import com.uniovi.repositories.ExerciseRepository;
import com.uniovi.services.ExerciseService;

import unitTest.ConfigurationSpring;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigurationSpring.class })
class LearningToGrowApplicationUnitTests {
	
	@Before
	public void setUp() throws Exception {
		exerciseService = new ExerciseService();
	}

	@Autowired
    private ExerciseService exerciseService;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
 
	@Test
	void test() {
//		ServicioA servicioA=mock(ServicioA.class);
//		when(servicioA.sumar(2,3)).thenReturn(5);
		
		Exercise exercise = new Exercise();
		exercise.setName("E1");
		exerciseService.addExercise(exercise);
//		when(this.exerciseRepository.findByName("E1")).thenReturn(exercise);
		 assertEquals(exercise.getName(), exerciseService.getExerciseByName("E1").getName());
	}
	
	@Test
	void test2() {
		assertEquals("E1", exerciseService.getExerciseByName("E1"));
	}

}
