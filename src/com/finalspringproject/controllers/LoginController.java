package com.finalspringproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.dao.User;
import com.finalspringproject.service.UsersService;

@Controller
public class LoginController {//here

	private UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}

	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "home";
	}

	@RequestMapping("/newaccount")
	public String newAccount(Model model) {
		model.addAttribute("user", new User());
		return "newaccount";
	}



	@RequestMapping(value = "/createaccount")
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result) {

		if (result.hasErrors()) {
			return "newaccount";
		}
		user.setAuthority("ROLE_USER");// same as below
		user.setEnabled(true);// not set in the form

		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateName.user.username");
			return "newaccount";
		}

		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DublicateName.user.username");
			return "newaccount";
		}
		return "home";
	}
}
