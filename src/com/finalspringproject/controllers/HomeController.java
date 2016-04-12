package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.entity.Recipe;
import com.finalspringproject.service.RecipeService;



@Controller
public class HomeController {

	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping("/")
	public String showRecipe(Model model,Principal principal) {

		//List<Recipe> recipe = recipeService.getCurrent();

//		model.addAttribute("recipe", recipe);
//		System.out.println("here " + recipe.toString());

//		boolean hasRecipe = false;
//		
//		if(principal !=null){
//			hasRecipe=recipeService.hadRecipe(principal.getName());
//		}
//		
//		model.addAttribute("hasRecipe",hasRecipe);
//		System.out.println("here2 " + hasRecipe);
		return "home";
	}
	

	

}
