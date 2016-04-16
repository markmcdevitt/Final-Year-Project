package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.entity.Allergy;
import com.finalspringproject.entity.IngredientsOwned;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.AllergyService;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class UserController {

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

	@RequestMapping("/profile")
	public String profile(Model model, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<Recipe> recipeList = user.getRecipes();

		model.addAttribute("user", user);
		model.addAttribute("recipeList", recipeList);
		return "profile";

	}

	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/editDetails")
	public String editDetails(Model model, @Validated(FormValidationGroup.class) User user, BindingResult result) {
		if (result.hasErrors()) {
			return "profile";
		} else {
			System.out.println("form is correct");
		}
		User oldDetails = usersService.getUser(user.getUsername());

		user.setRecipes(oldDetails.getRecipes());
		user.setShoppingList(oldDetails.getShoppingList());
		user.setWeeklyPlan(oldDetails.getWeeklyPlan());
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		usersService.update(user);

		return "profile";
	}

	@RequestMapping(value = "/doRegister")
	public String doRegister(Model model, @Validated(FormValidationGroup.class) User user, BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		} else {
			System.out.println("form is correct");
		}
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);

		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		return "loggedin";
	}
	@RequestMapping("/createIngredientsOwnedEdit")
	public String createIngredientsOwned2(Model model, Principal principal,
			@RequestParam(value = "ingredientName") String ingredientName) {

		User user = usersService.getUser(principal.getName());
		usersIngredients(user,ingredientName);
		
		model.addAttribute("user", user);
		return "profile";
	}

	@RequestMapping("/createingredientsowned")
	public String createIngredientsOwned(Model model, Principal principal,
			@RequestParam(value = "ingredientName") String ingredientName) {

		User user = usersService.getUser(principal.getName());
		usersIngredients(user,ingredientName);

		model.addAttribute("user", user);
		return "allergy";
	}
	@RequestMapping("/editAllergy")
	public String editAllergy(){
		return "allergy";
	}

	@RequestMapping("/yourallergy")
	public String allergy(Model model, Principal principal, @RequestParam(value = "nuts") String nuts,
			@RequestParam(value = "milk") String milk, @RequestParam(value = "peanuts") String peanuts,
			@RequestParam(value = "eggs") String eggs, @RequestParam(value = "fish") String fish,
			@RequestParam(value = "shellfish") String shellfish, @RequestParam(value = "wheat") String wheat,
			@RequestParam(value = "soy") String soy) {
		
		List<Allergy> allergyList = new ArrayList<Allergy>();
		Allergy soyAllergy = new Allergy();
		Allergy milkAllergy = new Allergy();
		Allergy nutsAllergy = new Allergy();
		Allergy fishAllergy = new Allergy();
		Allergy peanutsAllergy = new Allergy();
		Allergy shellfishAllergy = new Allergy();
		Allergy wheatAllergy = new Allergy();
		Allergy eggsAllergy = new Allergy();
		
		if(soy.contains("on")){
			soyAllergy =allergyService.getAllergy("soy");
			allergyList.add(soyAllergy);
		}
		
		if(milk.contains("on")){
			milkAllergy = allergyService.getAllergy("milk");
			allergyList.add(milkAllergy);
		}
		
		if(nuts.contains("on")){
			nutsAllergy = allergyService.getAllergy("nuts");
			allergyList.add(nutsAllergy);
		}
		
		if(fish.contains("on")){
			fishAllergy = allergyService.getAllergy("fish");
			allergyList.add(fishAllergy);
		}
		
		if(peanuts.contains("on")){
			peanutsAllergy = allergyService.getAllergy("peanuts");
			allergyList.add(peanutsAllergy);
		}
		
		if(shellfish.contains("on")){
			shellfishAllergy = allergyService.getAllergy("shellfish");
			allergyList.add(shellfishAllergy);
		}
		if(eggs.contains("on")){
			eggsAllergy = allergyService.getAllergy("egg");
			allergyList.add(eggsAllergy);
		}
		if(wheat.contains("on")){
			wheatAllergy = allergyService.getAllergy("wheat");
			allergyList.add(wheatAllergy);
		}
		User user = usersService.getUser(principal.getName());
		System.out.println(allergyList.size());

		user.setUsersAllergys(allergyList);
		System.out.println(user.toString());
		recipeService.saveOrUpdate(user);

		model.addAttribute("user", user);
		model.addAttribute("recipeList", user.getRecipes());

		return "profile";
	}
	
	public void usersIngredients(User user, String ingredientName){
		List<String> nameList = Arrays.asList(ingredientName.split(","));
		List<IngredientsOwned> ingredients = new ArrayList<IngredientsOwned>();

		for (String singleIng : nameList) {
			IngredientsOwned ingredientsOwned = new IngredientsOwned(singleIng);
			ingredients.add(ingredientsOwned);
		}
		Set<IngredientsOwned> hs = new HashSet<>();
		hs.addAll(ingredients);
		ingredients.clear();
		ingredients.addAll(hs);

		user.setIngredientsOwned(ingredients);
		recipeService.saveOrUpdate(user);

	}

}
