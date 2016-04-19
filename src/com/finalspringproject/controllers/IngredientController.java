package com.finalspringproject.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public String adjustRecipe(Model model, @PathVariable int id, @RequestParam(value = "quantity") String quan) {

		Recipe recipe = recipeService.getOneRecipe(id);
		List<Recipe> recipeList = new ArrayList<Recipe>();
		int quantity = Integer.parseInt(quan);

		List<Ingredient> ingredientList = recipe.getIngredients();// ingredients
		int serves = Integer.parseInt(recipe.getPeopleFed());// how many

		for (Ingredient ingredient : ingredientList) {
			double ingAmount = 0;
			try {
				ingAmount = Double.parseDouble(ingredient.getIngredientAmount());// amount

			} catch (Exception e) {
			}

			if (ingAmount == 1) {
				double oneServing = ingAmount * quantity;
				ingredient.setIngredientAmount(String.valueOf((int)oneServing));
			} else {
				double oneServing = ingAmount / serves;
				double newAmount = oneServing * quantity;
				ingredient.setIngredientAmount(recipeController.ingredientAmount(newAmount));
			}

		}
		double calories = ((Double.parseDouble(recipe.getCalories())/serves)*Double.parseDouble(quan));
		String cal= String.valueOf((int)round(calories,0));
		recipe.setIngredients(ingredientList);
		recipe.setPeopleFed(quan);
		recipe.setCalories(cal);
		recipeList.add(recipe);
		model.addAttribute("recipe", recipeList);
		return "recipe";
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
