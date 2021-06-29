package com.uniovi.services;

import org.springframework.stereotype.Service;

/**
 * Servicio encargado de los roles de usuario
 * 
 * @author maari
 *
 */
@Service
public class RolesService {

	private String[] roles = { "ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN" };

	/**
	 * Devuelve todos los roles del usuario
	 * 
	 * @return
	 */
	public String[] getRoles() {
		return roles;
	}

	/**
	 * Devuelve un array con el rol de estudiante y el rol de profesor
	 * 
	 * @return String[] roles
	 */
	public String[] getPrincipalRoles() {
		return new String[] { roles[0], roles[1] };
	}

}
