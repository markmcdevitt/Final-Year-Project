package com.finalspringproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Recipe;
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
	public String adjustRecipe(Model model, @PathVariable int id,
			@RequestParam(value = "quantity") String quan) {

		Recipe recipe = recipeService.getOneRecipe(id);
		int quantity = Integer.parseInt(quan);

		List<Ingredient> ingredientList = recipe.getIngredients();// ingredients
		int serves = Integer.parseInt(recipe.getPeopleFed());// how many

		for (Ingredient ingredient : ingredientList) {
			double ingAmount = Double.parseDouble(ingredient.getIngredientAmount());// amount
			double oneServing = ingAmount / serves;
			double newAmount = oneServing * quantity;
			
			ingredient.setIngredientAmount(recipeController.ingredientAmount(newAmount));
		}
		recipe.setIngredients(ingredientList);
		model.addAttribute("recipe", recipe);
		return "recipe";
	}
}
