package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniovi.entities.EmailConfig;

@RestController
public class IndexEmailController {
	
	@Autowired
	private EmailConfig config;

	@GetMapping
	public EmailConfig index() {
		return config;
	}
}
