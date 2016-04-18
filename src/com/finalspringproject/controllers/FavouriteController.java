package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.entity.Favorite;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.AllergyService;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class FavouriteController {

	private UsersService usersService;
	private RecipeService recipeService;
	private AllergyService allergyService;

	@Autowired
	public void setAllergyService(AllergyService allergyService) {
		this.allergyService = allergyService;
	}

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping(value = "/favourite/{id}")
	public String profile(Model model, Principal principal, @PathVariable int id) {
		User user = usersService.getUser(principal.getName());
		Recipe recipe = recipeService.getOneRecipe(id);
		Favorite favorite = new Favorite(recipe);

		user.getUsersFavorites().add(favorite);
		usersService.updateUser(user);

		model.addAttribute("user", user);
		model.addAttribute("recipeList", user.getRecipes());

		return "recipe";

	}

	@RequestMapping(value = "/deleteFavourite/{id}")
	public String delete(Model model, Principal principal, @PathVariable int id) {
		
		User user = usersService.getUser(principal.getName());
		Recipe recipe = recipeService.getOneRecipe(id);

		System.out.println(user.getUsersFavorites().size());
		List<Favorite>favoriteList = user.getUsersFavorites();
		for(int i = 0;i<favoriteList.size();i++){
			if(favoriteList.get(i).getRecipe().getId()==id){
				favoriteList.remove(i);
			}
		}
		System.out.println(user.getUsersFavorites().size());
		usersService.updateUser(user);
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(recipe);

		model.addAttribute("user", user);
		model.addAttribute("recipeList", user.getRecipes());
		return "profile";

	}
}
