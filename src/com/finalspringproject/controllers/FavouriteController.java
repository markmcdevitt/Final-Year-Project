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
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.Review;
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
		
		List<Recipe>recipeList = new ArrayList<>();
		recipeList.add(recipe);
		String level = recipeList.get(0).getLevel();

		int recipeLevel = levelCheck(level);
		int userLevel = levelCheck(user.getUserLevel());

		System.out.println(userLevel + "check " + recipeLevel);

		String answer;
		if (userLevel >= recipeLevel) {
			answer = recipeList.get(0).getLevel();
		} else {
			answer = "unknown";
		}

		model.addAttribute("answer", answer);
		
		user.getUsersFavorites().add(favorite);
		usersService.updateUser(user);
		
		RecipeController recipeController = new RecipeController();
		for(Ingredient r:recipeList.get(0).getIngredients()){
			r.setIngredientAmount(recipeController.ingredientAmount(Double.parseDouble(r.getIngredientAmount())));
		}
		
		String ableToReview = "false";
		try {
			List<Recipe> recipeList2 = user.getRecipes();
			for (Recipe r : recipeList2) {
				System.out.println("here 3");
				if (recipeList.get(0).getTitleParse().equals(r.getTitleParse())) {
					System.out.println("here " + r.toString());
					ableToReview = "true";
				}
			}
		} catch (Exception e) {
			System.out.println("recipe catch");
		}
		try {
			List<Review> reviewList = recipeList.get(0).getReview();
			for (Review review : reviewList) {
				System.out.println("here 4");
				if (review.getUser().getUsername().equals(user.getUsername())) {
					System.out.println("here " + review.getUser().toString());
					ableToReview = "true";
				}
			}
		} catch (Exception e) {
			System.out.println("review catch");
		}

		model.addAttribute("review", ableToReview);

		model.addAttribute("recipe", recipeList);
		
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
	
	public int levelCheck(String level) {

		int check;

		if (level.equals("Master Chef")) {
			check = 8;
		} else if (level.equals("Executive Chef")) {
			check = 7;
		} else if (level.equals("Sous Chef")) {
			check = 5;
		} else if (level.equals("Prep Chef")) {
			check = 5;
		} else if (level.equals("Wise Chef")) {
			check = 4;
		} else if (level.equals("Gifted Chef")) {
			check = 3;
		} else if (level.equals("Amatuer Cook")) {
			check = 2;
		} else {
			check = 1;
		}
		return check;
	}
}
