package com.finalspringproject.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class IngredientController {

	private RecipeService recipeService;
	private UsersService usersService;
	private RecipeController recipeController;

	@Autowired
	public void setRecipeController(RecipeController recipeController) {
		this.recipeController = recipeController;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/adjustRecipe/{id}")
	public String adjustRecipe(Model model, @PathVariable int id, @RequestParam(value = "quantity") String quan, Principal principal) {

		Recipe recipe = recipeService.getOneRecipe(id);
		List<Recipe> recipeList = new ArrayList<Recipe>();
		int quantity = Integer.parseInt(quan);

		List<Ingredient> ingredientList = recipe.getIngredients();
		int serves = Integer.parseInt(recipe.getPeopleFed());

		for (Ingredient ingredient : ingredientList) {
			double ingAmount = 0;
			try {
				ingAmount = Double.parseDouble(ingredient.getIngredientAmount());// amount

			} catch (Exception e) {
			}

			if (ingAmount == 1) {
				Ingredient ing = new Ingredient();
				ing = wholeNumber(ingAmount, serves, quantity, ingredient);
				
				ingredient.setIngredientAmount(ing.getIngredientAmount());
				ingredient.setIngredientName(ing.getIngredientName());
			} else {

				double oneServing = ingAmount / serves;
				double newAmount = oneServing * quantity;

				Ingredient ing = new Ingredient();
				ing = recipeController.ingredientAmount(newAmount, ingredient.getIngredientName());
				ingredient.setIngredientAmount(ing.getIngredientAmount());
				ingredient.setIngredientName(ing.getIngredientName());
			}

		}
		
	
		recipe.setIngredients(ingredientList);
		recipe.setPeopleFed(quan);
		recipeList.add(recipe);
		User user;
		
		String level = recipeList.get(0).getLevel();
		try {
			 user = usersService.getUser(principal.getName());
		} catch (Exception e) {
			 user = new User();
		}
		

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
		model.addAttribute("recipe", recipeList);
		return "recipe";
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public Ingredient wholeNumber(double ingAmount, int serves, int quantity, Ingredient ingredient) {

		double oneServing = ingAmount / serves;
		double newAmount = oneServing * quantity;

		if (newAmount % 1 == 0) {
			int tablespoon = 0;
			
			if (newAmount % 3 == 0 && ingredient.getIngredientName().contains("teaspoon")) {
				tablespoon = (int) (newAmount / 3.0);
				String name = ingredient.getIngredientName().replace("teaspoon", "tablespoon");
				ingredient.setIngredientName(name);
				ingredient.setIngredientAmount(String.valueOf(tablespoon));

			} else if (ingredient.getIngredientName().contains("teaspoon")&& newAmount>=3) {
				int check = (int) newAmount;
				do {
					check -= 3;
					newAmount -= 3;
					tablespoon += 1;
				} while (check >= 3);
				String finishedAmount;
				
					finishedAmount = tablespoon + " tablespoon and " + (int)newAmount;
				 
				ingredient.setIngredientAmount(String.valueOf(finishedAmount));
			} else {
				ingredient.setIngredientAmount(String.valueOf((int) newAmount));
			}

		} else {
			double teaspoon=0;
			if (newAmount < 1 && ingredient.getIngredientName().contains("tablespoon")){
				teaspoon =  newAmount *3;
				String name = ingredient.getIngredientName().replace("tablespoon", "teaspoon");
				ingredient.setIngredientName(name);
				ingredient.setIngredientAmount(String.valueOf(teaspoon));
				newAmount = teaspoon;
			}
			ingredient = recipeController.ingredientAmount(newAmount, ingredient.getIngredientName());
		}

		return ingredient;
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
