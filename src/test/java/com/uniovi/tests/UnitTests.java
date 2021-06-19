package com.uniovi.tests;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.entities.Exercise;
import com.uniovi.repositories.ExerciseRepository;
import com.uniovi.services.ExerciseService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootApplication
class UnitTests {
	
	@Before
	public void setUp() throws Exception {
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
//		 assertEquals(exercise.getName(), exerciseService.getExerciseByName("E1").getName());
	}
	
	@Test
	void test2() {
//		assertEquals("E1", exerciseService.getExerciseByName("E1"));
	}

}
