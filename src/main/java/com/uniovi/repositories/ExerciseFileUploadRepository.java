package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.UploadFile;

public interface ExerciseFileUploadRepository extends CrudRepository<UploadFile, Long>  {

}
