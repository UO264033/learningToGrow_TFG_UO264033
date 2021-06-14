package com.uniovi.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.services.ExerciseService;

class LearningToGrowApplicationUnitTests {
	
	@Before
	public void setUp() throws Exception {
	}

	@Autowired
    ExerciseService exerciseService = new ExerciseService();
 
	@Test
	void test() {
//		ServicioA servicioA=mock(ServicioA.class);
//		when(servicioA.sumar(2,3)).thenReturn(5);
		
		 assertEquals(3, exerciseService.getExercises().size());
	}

}
