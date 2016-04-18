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

	@RequestMapping(value="/favourite/{id}")
	public String profile(Model model, Principal principal, @PathVariable int id) {
		User user = usersService.getUser(principal.getName());
		Recipe recipe = recipeService.getOneRecipe(id);
		Favorite favorite = new Favorite(recipe);
		
		List<Favorite> favouriteList = new ArrayList<Favorite>();
		try {
			favouriteList = user.getUsersFavorites();
		} catch (Exception e) {
		}
		favouriteList.add(favorite);
		user.setUsersFavorites(favouriteList);
		recipeService.saveOrUpdate(user);
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(recipe);

		model.addAttribute("recipe", recipeList);
		return "recipe";

	}
}
