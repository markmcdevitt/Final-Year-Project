package com.finalspringproject.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.service.RecipeService;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String showRecipe(Model model, Principal principal) {
		return "home";
	}
}
