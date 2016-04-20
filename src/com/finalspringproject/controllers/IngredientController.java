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

		List<Ingredient> ingredientList = recipe.getIngredients();
		int serves = Integer.parseInt(recipe.getPeopleFed());

		for (Ingredient ingredient : ingredientList) {
			double ingAmount = 0;
			try {
				ingAmount = Double.parseDouble(ingredient.getIngredientAmount());// amount

			} catch (Exception e) {
			}

			if (ingAmount == 1) {
				System.out.println(ingredient.getIngredientName() + " is a one " + ingredient.getIngredientAmount());

				Ingredient ing = new Ingredient();
				ing = wholeNumber(ingAmount, serves, quantity, ingredient);
				
				ingredient.setIngredientAmount(ing.getIngredientAmount());
				ingredient.setIngredientName(ing.getIngredientName());
				System.out.println("/////////////////////////////////////////////////////////////////////////");
				System.out.println("THE SAME INGREDIENT: " + ingredient.toString());
			} else {

				double oneServing = ingAmount / serves;
				double newAmount = oneServing * quantity;
				System.out.println(ingredient.getIngredientName() + " is not a one " + ingredient.getIngredientAmount());

				Ingredient ing = new Ingredient();
				ing = recipeController.ingredientAmount(newAmount, ingredient.getIngredientName());
				ingredient.setIngredientAmount(ing.getIngredientAmount());
				ingredient.setIngredientName(ing.getIngredientName());
				System.out.println("/////////////////////////////////////////////////////////////////////////");
				System.out.println("THE SAME INGREDIENT: " + ingredient.toString());
			}

		}
		double calories = ((Double.parseDouble(recipe.getCalories()) / serves) * Double.parseDouble(quan));
		String cal = String.valueOf((int) round(calories, 0));
		recipe.setIngredients(ingredientList);
		System.out.println("/////////////////////////////////////////////////////////////////////////");
		for (Ingredient ingredient : ingredientList) {
			System.out.println(ingredient.toString());
		}
		recipe.setPeopleFed(quan);
		recipe.setCalories(cal);
		recipeList.add(recipe);
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
			System.out.println(ingredient.getIngredientName() + " is a one " + ingredient.getIngredientAmount());

			int tablespoon = 0;

			if (newAmount % 3 == 0 && ingredient.getIngredientName().contains("teaspoon")) {
				System.out.println(ingredient.getIngredientName() + " contains a teaspoon and is dividable by 3 "
						+ ingredient.getIngredientAmount());

				tablespoon = (int) (newAmount / 3.0);
				String name = ingredient.getIngredientName().replace("teaspoon", "tablespoon");
				ingredient.setIngredientName(name);
				ingredient.setIngredientAmount(String.valueOf(tablespoon));

			} else if (ingredient.getIngredientName().contains("teaspoon")&& newAmount>=3) {
				System.out.println(ingredient.getIngredientName() + " is a teaspoon " + ingredient.getIngredientAmount());

				int check = (int) newAmount;
				do {
					check -= 3;
					newAmount -= 3;
					tablespoon += 1;
				} while (check >= 3);

				String finishedAmount = tablespoon + " tablespoons and " + newAmount;
				ingredient.setIngredientAmount(String.valueOf(finishedAmount));
			} else {
				System.out.println(ingredient.getIngredientName() + " else " + newAmount);

				ingredient.setIngredientAmount(String.valueOf((int) newAmount));
			}

		} else {
			System.out.println("is a fraction??????");
			ingredient = recipeController.ingredientAmount(newAmount, ingredient.getIngredientName());
		}

		return ingredient;
	}

}
