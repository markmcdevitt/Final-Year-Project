package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.dao.Recipe;
import com.finalspringproject.dao.User;
import com.finalspringproject.service.UsersService;
@Controller
public class UserController {
	
private UsersService usersService;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@RequestMapping("/profile")
	public String profile(Model model, Principal principal) {
		User user=usersService.getUser(principal.getName());
		List<User> userList = new ArrayList<User>();
		List<Recipe> recipeList = user.getRecipes();
		
		model.addAttribute("user",user);
		model.addAttribute("userList",userList);
		model.addAttribute("recipeList",recipeList);
		return "profile";
		
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping(value="/editDetails")
	public String editDetails(Model model,@Validated(FormValidationGroup.class) User user, BindingResult result) {
		if(result.hasErrors()){
			return "profile";
		}else{
			System.out.println("form is correct");
		}
		User oldDetails = usersService.getUser(user.getUsername());
		
		user.setRecipes(oldDetails.getRecipes());
		user.setShoppingList(oldDetails.getShoppingList());
		user.setWeeklyPlan(oldDetails.getWeeklyPlan());
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		usersService.update(user);
		
		return "profile";
	}
	
	@RequestMapping(value="/doRegister")
	public String doRegister(Model model,@Validated(FormValidationGroup.class) User user, BindingResult result) {//binding tells us if the form is validated

		if(result.hasErrors()){
			return "register";
		}else{
			System.out.println("form is correct");
		}
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		try{
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		return "loggedin";
	}

}
