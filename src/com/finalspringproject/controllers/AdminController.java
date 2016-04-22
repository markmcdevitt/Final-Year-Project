package com.finalspringproject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.ReviewService;
import com.finalspringproject.service.ScrapeService;
import com.finalspringproject.service.UsersService;

@Controller
public class AdminController {

	private UsersService usersService;
	private ReviewService reviewService;
	private RecipeService recipeService;
	private ScrapeService scrapeService;
	
	@Autowired
	public void setScrapeService(ScrapeService scrapeService) {
		this.scrapeService = scrapeService;
	}

	@Autowired
	public void setRecipeService(RecipeService	 recipeService) {
		this.recipeService = recipeService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Autowired
	public void setReviewService(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);
		return "admin";
	}
	
	@RequestMapping("/admin/alphabetical")
	public String alphabetical(Model model) {
		List<User> users = usersService.getAllUsers();
		
		Collections.sort(users, new Comparator<User>() {
	        @Override
	        public int compare(final User object1, final User object2) {
	            return object1.getUsername().compareTo(object2.getUsername());
	        }
	       } );
		model.addAttribute("users", users);
		return "admin";
	}

	@RequestMapping("/user/{username}")
	public String user(Model model,@PathVariable String username) {
		User user = usersService.getUser(username);
		List<User> userList = new ArrayList<User>();
		List<Recipe> recipeList = user.getRecipes();
		
		model.addAttribute("user",user);
		model.addAttribute("userList",userList);
		model.addAttribute("recipeList",recipeList);
		return"inspection";
	}

	@RequestMapping("/delete/{username}")
	public String delete(Model model, @PathVariable String username) {
		User user = usersService.getUser(username);
		
		user.setEnabled(false);
		usersService.update(user);
		try {
			usersService.deleteUser(user);
		} catch (Exception e) {
			System.out.println("He had reviews");
		}
		
		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);
		return "admin";
	}
	
	@RequestMapping("/scrape")
	public String scrape() {
		return "scraperecipes";
	}
	
	@RequestMapping("/scrapeRecipes")
	public String scrapeRecipes(Model model,@RequestParam(value = "link") String link) throws IOException{
		scrapeService.createEverything(link);
		return "scraperecipes";
	}
	
	@RequestMapping("/scrapeRecipesDefault")
	public String scrapeRecipesDefault(Model model) throws IOException{
		try {
			scrapeService.createEverything("http://allrecipes.com/");
		} catch (Exception e) {
			
		}
		return "scraperecipes";
	}
}
