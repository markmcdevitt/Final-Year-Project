package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.entity.Allergy;
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
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
	public String completeorder(Model model, Principal principal, @RequestParam(value = "search") String search) {

		List<Recipe> allergicRecipes = new ArrayList<Recipe>();
		List<Recipe> nonAllergicRecipes = new ArrayList<Recipe>();

		List<Recipe> recipeList = new ArrayList<Recipe>();
		User user = userService.getUser(principal.getName());
		boolean containsAllergy = false;

		recipeList = recipeService.find(search);
		if(!user.getUsersAllergys().isEmpty()){
		for (Allergy allergy : user.getUsersAllergys()) {
			for (Recipe recipe : recipeList) {
				for (Ingredient ing : recipe.getIngredients()) {
					System.out.println("ingredient--> "+ing.toString());
					System.out.println("allergy-->"+ allergy.toString());
					if (ing.getIngredientName().contains(allergy.getAllergy())) {
						allergicRecipes.add(recipe);
						containsAllergy = true;
						System.out.println("allergys");
					}
				}
				if (!containsAllergy) {
					System.out.println(recipe.toString()+" didnt contain an allergy");
					nonAllergicRecipes.add(recipe);
				}
			}
		}
		}

		List<User> chefList = new ArrayList<User>();

		chefList = userService.findChef(search);
		
		System.out.println("nonAllergicRecipes "+nonAllergicRecipes.size());
		System.out.println("recipeList "+recipeList.size());
		System.out.println("allergicRecipes "+allergicRecipes.size());

		if(!user.getUsersAllergys().isEmpty()){
		model.addAttribute("recipe", nonAllergicRecipes);
		}else{
			model.addAttribute("recipe", recipeList);
		}

		model.addAttribute("allergicrecipe", allergicRecipes);
		model.addAttribute("search", search);
		model.addAttribute("chefList", chefList);
		return "search";
	}
}
