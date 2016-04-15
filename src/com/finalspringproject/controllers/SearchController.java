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
import com.finalspringproject.service.AllergyService;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class SearchController {

	private RecipeService recipeService;
	private UsersService userService;
	private AllergyService allergyService;

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@Autowired
	public void setAllergyService(AllergyService allergyService) {
		this.allergyService = allergyService;
	}

	@Autowired
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/search")
	public String completeorder(Model model, Principal principal, @RequestParam(value = "search") String search,
			@RequestParam(value = "nuts") String nuts, @RequestParam(value = "milk") String milk,
			@RequestParam(value = "peanuts") String peanuts, @RequestParam(value = "eggs") String eggs,
			@RequestParam(value = "fish") String fish, @RequestParam(value = "shellfish") String shellfish,
			@RequestParam(value = "wheat") String wheat, @RequestParam(value = "soy") String soy,
			@RequestParam(value = "exclude") String exclude) {

		System.out.println(wheat + "/" + soy + "/" + eggs + "/" + fish + "/" + shellfish + "/" + milk + "/" + nuts + "/"
				+ peanuts);

		List<Allergy> allergyList = allergyList(wheat, soy, eggs, fish, shellfish, milk, nuts, peanuts);

		for (Allergy a : allergyList) {
			System.out.println(a.toString());
		}

		List<Recipe> allergicRecipes = new ArrayList<Recipe>();
		List<Recipe> nonAllergicRecipes = new ArrayList<Recipe>();

		List<Recipe> recipeList = new ArrayList<Recipe>();
		User user = userService.getUser(principal.getName());
		boolean containsAllergy = false;

		recipeList = recipeService.find(search);
		if (!exclude.contains("on") && allergyList.isEmpty()) {

			if (!user.getUsersAllergys().isEmpty()) {
				for (Allergy allergy : user.getUsersAllergys()) {
					for (Recipe recipe : recipeList) {
						for (Ingredient ing : recipe.getIngredients()) {
							if (allergy.getAllergy().equals("shellfish")) {
								if (ing.getIngredientName().contains("shrimp")
										|| ing.getIngredientName().contains("lobster")
										|| ing.getIngredientName().contains("crab")
										|| ing.getIngredientName().contains("clams")
										|| ing.getIngredientName().contains("mussels")
										|| ing.getIngredientName().contains("oysters")
										|| ing.getIngredientName().contains("scallops")) {
									allergicRecipes.add(recipe);
									containsAllergy = true;
								}
							} else if (allergy.getAllergy().equals("milk")) {
								if (ing.getIngredientName().contains(" milk ")) {
									allergicRecipes.add(recipe);
									containsAllergy = true;
								}
							} else if (ing.getIngredientName().contains(allergy.getAllergy())) {
								System.out.println("4--> " + allergy.toString());
								allergicRecipes.add(recipe);
								containsAllergy = true;
							}
						}
						if (!containsAllergy) {
							nonAllergicRecipes.add(recipe);
						}
					}
				}
			}
			model.addAttribute("recipe", nonAllergicRecipes);
		} else if (!exclude.contains("on") && !allergyList.isEmpty()) {
			for (Allergy allergy : allergyList) {
				for (Recipe recipe : recipeList) {
					for (Ingredient ing : recipe.getIngredients()) {
						if (allergy.getAllergy().equals("shellfish")) {
							if (ing.getIngredientName().contains("shrimp")
									|| ing.getIngredientName().contains("lobster")
									|| ing.getIngredientName().contains("crab")
									|| ing.getIngredientName().contains("clams")
									|| ing.getIngredientName().contains("mussels")
									|| ing.getIngredientName().contains("oysters")
									|| ing.getIngredientName().contains("scallops")) {
								allergicRecipes.add(recipe);
								containsAllergy = true;
							}
						} else if (allergy.getAllergy().equals("milk")) {
							if (ing.getIngredientName().contains(" milk ")) {
								allergicRecipes.add(recipe);
								containsAllergy = true;
							}
						} else if (ing.getIngredientName().contains(allergy.getAllergy())) {
							System.out.println("4--> " + allergy.toString());
							allergicRecipes.add(recipe);
							containsAllergy = true;
						}
					}
					if (!containsAllergy) {
						nonAllergicRecipes.add(recipe);
					}
				}
			}
			model.addAttribute("recipe", nonAllergicRecipes);
		} else {
			System.out.println("9-- no allergys");
			model.addAttribute("recipe", recipeList);
		}

		List<User> chefList = new ArrayList<User>();

		chefList = userService.findChef(search);

		// if (!user.getUsersAllergys().isEmpty()) {
		// model.addAttribute("recipe", nonAllergicRecipes);
		// } else {
		// model.addAttribute("recipe", recipeList);
		// }

		model.addAttribute("allergicrecipe", allergicRecipes);
		model.addAttribute("search", search);
		model.addAttribute("chefList", chefList);
		return "search";
	}

	public List<Allergy> allergyList(String wheat, String soy, String eggs, String fish, String shellfish, String milk,
			String nuts, String peanuts) {// WRONG ORDER
		List<Allergy> allergyList = new ArrayList<Allergy>();

		System.out.println("10");
		if (soy.contains("on")) {
			Allergy soyAllergy = allergyService.getAllergy("soy");
			allergyList.add(soyAllergy);
		}

		if (milk.contains("on")) {
			Allergy milkAllergy = allergyService.getAllergy("milk");
			allergyList.add(milkAllergy);
		}

		if (nuts.contains("on")) {
			Allergy nutsAllergy = allergyService.getAllergy("nuts");
			allergyList.add(nutsAllergy);
		}

		if (fish.contains("on")) {
			Allergy fishAllergy = allergyService.getAllergy("fish");
			allergyList.add(fishAllergy);
		}

		if (peanuts.contains("on")) {
			Allergy peanutsAllergy = allergyService.getAllergy("peanuts");
			allergyList.add(peanutsAllergy);
		}

		if (shellfish.contains("on")) {
			Allergy shellfishAllergy = allergyService.getAllergy("shellfish");
			allergyList.add(shellfishAllergy);
		}
		if (eggs.contains("on")) {
			Allergy eggsAllergy = allergyService.getAllergy("eggs");
			allergyList.add(eggsAllergy);
		}
		if (wheat.contains("on")) {
			Allergy wheatAllergy = allergyService.getAllergy("wheat");
			allergyList.add(wheatAllergy);
		}
		System.out.println("11 " + allergyList.size());
		return allergyList;
	}
}
