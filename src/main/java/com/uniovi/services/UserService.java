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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Exercise;
import com.uniovi.entities.Subject;
import com.uniovi.entities.User;
import com.uniovi.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ExerciseService exerciseService;

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
//		if (getUser(user.getId()) == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepository.save(user);
//		}
	}

	public User getUserByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	public void deleteUser(Long id) {
		User user = getUser(id);
		if (!user.getSubjects().isEmpty()) {
			for (Subject s : user.getSubjects())
				subjectService.deleteSubject(s.getId());
		}
		if (!user.getExercises().isEmpty()) {
			for (Exercise e : user.getExercises()) {
				exerciseService.deleteExercise(e.getId());
			}
		}
		usersRepository.deleteById(id);
	}

	public Page<User> searchUsersByUsernameAndNameAndLastname(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.findByUsernameAndLastNameAndName(pageable, searchText);
		return users;
	}

	public void editUser(User user, Long id) {

		Optional<User> userOp = usersRepository.findById(id);
		if (userOp.isPresent()) {
			User userToUpdate = userOp.get();
			userToUpdate.setUsername(user.getUsername());
			userToUpdate.setName(user.getName());
			userToUpdate.setLastName(user.getLastName());
			userToUpdate.setEmail(user.getEmail());
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepository.save(userToUpdate);
		} else
			System.out.println("No se pudo actualizar");
	}

	public Page<User> getUsersByRole(Pageable pageable, String role) {
		return usersRepository.findUsersByRole(pageable, role);
	}

	public List<User> getStudentsByRole(String role) {
		return usersRepository.findStudentsByRole(role);
	}

	public Page<User> searchStudentsByNameAndLastname(Pageable pageable, String searchText, String role) {
		searchText = "%" + searchText + "%";
		return usersRepository.findStudentsFiltered(pageable, searchText, role);
	}

	public List<User> getUsersBySubject(Subject subject) {
		List<User> students = new ArrayList<User>();
		for (User u : usersRepository.findStudentsByRole("ROLE_STUDENT")) {
			for (Subject s : u.getSubjects()) {
				if (s.getName().equals(subject.getName()))
					students.add(u);
			}
		}
		return students;
	}

	public void setStudent(Long id, String name) {
		Subject subject = subjectService.getSubjectByName(name);
		User student = usersRepository.findById(id).get();
		student.addSubject(subject);
		subject.addStudent(student);
		subjectService.addSubject(subject);
		usersRepository.save(student);
	}

	public List<User> getStudentsFiltered(String searchText) {
		searchText = "%" + searchText + "%";
		return usersRepository.findStudentsFiltered(searchText, "ROLE_STUDENT");
	}

	public User getByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public Page<User> getList(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty())
			users = searchUsersByUsernameAndNameAndLastname(pageable, searchText);
		else
			users = getUsers(pageable);
		return users;
	}

	public Page<User> getStudentList(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty())
			users = searchStudentsByNameAndLastname(pageable, searchText, "ROLE_STUDENT");
		else
			users = getUsersByRole(pageable, "ROLE_STUDENT");
		return users;
	}

	public User perfil() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = getUserByUsername(username);
		return activeUser;
	}

	public User activeUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User activeUser = getUserByUsername(username);
		return activeUser;
	}
}