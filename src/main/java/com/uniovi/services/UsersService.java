package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SubjectService subjectService;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = usersRepository.findAll(pageable);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
	
	public Page<User> searchUsersByUsernameAndNameAndLastname(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.findByUsernameAndLastNameAndName(pageable, searchText);
		return users;
	}

	public void editUser(User user) {
		
		Optional<User> userOp = usersRepository.findById(user.getId());
		if(userOp.isPresent()){
			User userToUpdate = userOp.get();
			userToUpdate.setUsername(user.getUsername());
			userToUpdate.setName(user.getName());
			userToUpdate.setLastName(user.getLastName());
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepository.save(userToUpdate);
		}
		else
			System.out.println("No se pudo actualizar");		
	}
	
	public Page<User> getUsersByRole(Pageable pageable, String role){
		return usersRepository.findUsersByRole(pageable, role);
	}
	
	public List<User> getStudentsByRole(String role){
		return usersRepository.findStudentsByRole(role);
	}

	public Page<User> searchStudentsByNameAndLastname(Pageable pageable, String searchText, String role) {
		searchText = "%" + searchText + "%";
		return usersRepository.findStudentsByNameAndLastname(pageable, searchText, role);
	}

	public List<User> getUsersBySubject(Subject subject) {
		List<User> students = new ArrayList<User>();
		for (User u : subject.getStudents()) {
			students.add(u);
		}
		return students;
	}

	public void setStudent(Long id, String name) {
		Subject subject = subjectService.getSubjectByName(name);
		User student = usersRepository.findById(id).get();
		student.addSubject(subject);
		usersRepository.save(student);
	}

}