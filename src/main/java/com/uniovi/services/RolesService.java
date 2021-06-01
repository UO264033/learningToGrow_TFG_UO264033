package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService { //lo ideal sería incluir una entidad Role en bases de datos y relacionarla con la entidad user.

	String[] roles = { "ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN" };

	public String[] getRoles() {
		return roles;
	}
	
	public String[] getPrincipalRoles() {
		return new String[] {roles[0], roles[1]};
	}

	
	
	

}
