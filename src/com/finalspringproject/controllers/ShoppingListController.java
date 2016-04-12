package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finalspringproject.dao.Ingredient;
import com.finalspringproject.dao.ShoppingList;
import com.finalspringproject.dao.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.ShoppingListService;

@Controller
public class ShoppingListController {

	@Autowired
	private RecipeController recipeController;
	@Autowired
	private ShoppingListService shoppingListService;
	@Autowired
	private RecipeService recipeService;

	@RequestMapping(value = "/viewshoppinglist")
	public String viewshoppinglist(Model model, Principal principal) {
		List<User> userList = new ArrayList<User>();
		String username = principal.getName();
		User user = shoppingListService.getUserShoppingList(username);
		List<ShoppingList> shoppingList = user.getShoppingList();
		
		try {
			for (ShoppingList ingredient : shoppingList) {
				String quantity = ingredient.getQuantity();
				double amount = Double.parseDouble(quantity);
				String finalAmount = recipeController.ingredientAmount(amount);
				ingredient.setQuantity(finalAmount);
				System.out.println(finalAmount);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		userList.add(user);
		model.addAttribute("userList", userList);
		return "allshoppinglists";

	}

	
	@RequestMapping(value = "/deleteEntireShoppingList")
	public String deleteEntireShoppingList(Model model, Principal principal) {
		List<User> userList = new ArrayList<User>();
		String username = principal.getName();
		User user = shoppingListService.getUserShoppingList(username);
		List<ShoppingList> shoppingLists = user.getShoppingList();
		shoppingLists.clear();
		user.setShoppingList(shoppingLists);
		recipeService.saveOrUpdate(user);
		userList.add(user);
		model.addAttribute("userList", userList);
		return "allshoppinglists";

	}

	@RequestMapping(value = "/deleteShoppingList/{shoppingListId}")
	public String deleteShoppingList(Model model, Principal principal, @PathVariable int shoppingListId) {
		List<User> userList = new ArrayList<User>();
		String username = principal.getName();
		User user = shoppingListService.getUserShoppingList(username);
		
		List<ShoppingList> shoppingList = user.getShoppingList();
		for (ShoppingList ingredient : shoppingList) {
			String quantity = ingredient.getQuantity();
			double amount = Double.parseDouble(quantity);
			String finalAmount = recipeController.ingredientAmount(amount);
			ingredient.setQuantity(finalAmount);
		}
		
		for (ShoppingList sl : shoppingList) {
			if (sl.getId() == shoppingListId) {
				shoppingList.remove(sl);
				break;
			}
		}
		user.setShoppingList(shoppingList);
		recipeService.saveOrUpdate(user);

		userList.add(user);
		model.addAttribute("userList", userList);

		return "allshoppinglists";

	}
}
