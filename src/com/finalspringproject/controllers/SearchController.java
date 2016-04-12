package com.finalspringproject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.dao.Recipe;
import com.finalspringproject.dao.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class SearchController {

	private RecipeService recipeService;
	private UsersService userService;

	@Autowired
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	@Autowired
	public void RecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping(value = "/search")
	public String completeorder(Model model, @RequestParam(value = "search") String search) {

		List<Recipe> recipeList = new ArrayList<Recipe>();
		List<User> chefList = new ArrayList<User>();

		recipeList=recipeService.find(search);
		chefList=userService.findChef(search);
		for(Recipe r:recipeList){
			System.out.println(r);
		}
		
		model.addAttribute("search",search);
		model.addAttribute("recipe", recipeList);
		model.addAttribute("chefList", chefList);
		return "search";
	}
}

