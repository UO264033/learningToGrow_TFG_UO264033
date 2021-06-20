package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.TestType;

public interface ExerciseTestRepository extends CrudRepository<TestType, Long> {

}
