package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UserService;
import com.uniovi.validators.EditUserValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {

	@Autowired
	private UserService usersService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private EditUserValidator editUserValidator;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = usersService.getList(pageable, searchText);
		if (users.getContent().isEmpty()) {
			model.addAttribute("mensaje", "No se han encontrado resultados.");
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}

	@RequestMapping("/user/student/list")
	public String getStudentList(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = usersService.getStudentList(pageable, searchText);
		if (users.getContent().isEmpty()) {
			model.addAttribute("mensaje", "No se han encontrado resultados.");
		}
		model.addAttribute("studentList", users);
		model.addAttribute("page", users);
		return "user/student/list";
	}

	@RequestMapping(value = "/user/perfil")
	public String perfil(Model model) {
		model.addAttribute("user", usersService.activeUser());
		return "user/perfil";
	}

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/student/search")
	public String getStudentsFiltered(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/student/search";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("rolesList", rolesService.getRoles());
			return "user/add";
		}
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		User user = usersService.getUser(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, 
			@ModelAttribute User user, BindingResult result) {
		editUserValidator.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/edit";
		}
		usersService.editUser(user, id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("rolesList", rolesService.getPrincipalRoles());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("rolesList", rolesService.getPrincipalRoles());
			return "signup";
		}
		usersService.addUser(user);
		securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Usuario y/o contraseña incorrectos.");
		if (logout != null)
			model.addAttribute("message", "Sesión cerrada con éxito.");
		return "login";

	}

}
