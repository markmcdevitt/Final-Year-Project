package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.Review;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class ReviewController {

	private RecipeService recipeService;
	private UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/createreview/{titleParse}")
	public String createreview(Model model, @RequestParam(value = "message") String message,
			@RequestParam(value = "rating-input-1") String rating, @PathVariable String titleParse,
			Principal principal) {
		
		List<Recipe> recipe = recipeService.getCurrent(titleParse);
		User user = usersService.getUser(principal.getName());

		Recipe currentRecipe = recipe.get(0);
		Review review = new Review(rating, message, user);

		List<Review> reviewList = currentRecipe.getReview();
		reviewList.add(review);
		int userRating = 0;
		for (Review r : reviewList) {
			userRating += Integer.parseInt(r.getRating());
		}

		int numberOfRatings = reviewList.size();
		double averageRating = userRating / numberOfRatings;
		String totalRating = String.valueOf(averageRating);

		currentRecipe.setTotalRating(totalRating);
		currentRecipe.setReview(reviewList);

		recipeService.saveOrUpdate(currentRecipe);
		recipe.clear();
		recipe.add(currentRecipe);
		System.out.println(recipe.toString());
		
		RecipeController recipeController = new RecipeController();
		for(Ingredient r:recipe.get(0).getIngredients()){
			try {
				r.setIngredientAmount(recipeController.ingredientAmount(Double.parseDouble(r.getIngredientAmount())));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		

		model.addAttribute("recipe", recipe);
		
		return "recipe";
	}

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
}
