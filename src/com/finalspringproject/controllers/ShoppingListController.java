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
import com.finalspringproject.entity.ShoppingList;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.ShoppingListService;

@Controller
public class ShoppingListController {

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

	@RequestMapping(value = "/addToShoppingList")
	public String addToShoppingList(Model model, Principal principal,
			@RequestParam(value = "ingredientQuantity") String ingredientAmount,
			@RequestParam(value = "ingredientName") String ingredientName) {

		List<User> userList = new ArrayList<User>();
		User user = shoppingListService.getUserShoppingList(principal.getName());
		ShoppingList shoppingList = new ShoppingList(ingredientAmount, ingredientName);
		System.out.println("??"+ingredientName);
		boolean sameIng = false;
		
		for(ShoppingList sl:user.getShoppingList()){
			System.out.println(sl.getIngredient()+" / "+ingredientName);
			if(sl.getIngredient().equals(ingredientName)){
				double quantity = Double.parseDouble(sl.getQuantity())+Double.parseDouble(ingredientAmount);
				String quan = String.valueOf((int) round(quantity, 0));
				sl.setQuantity(quan);
				sameIng=true;
			}
		}
		if(!sameIng){
		double quantity = Double.parseDouble(shoppingList.getQuantity());	
		String quan = String.valueOf((int) round(quantity, 0));	
		shoppingList.setQuantity(quan);
		user.getShoppingList().add(shoppingList);
		}
		recipeService.saveOrUpdate(user);

		userList.add(user);
		model.addAttribute("userList", userList);

		return "allshoppinglists";
	}

	@RequestMapping(value = "/deleteShoppingList/{shoppingListId}")
	public String deleteShoppingList(Model model, Principal principal, @PathVariable int shoppingListId) {
		List<User> userList = new ArrayList<User>();
		User user = shoppingListService.getUserShoppingList(principal.getName());

		List<ShoppingList> shoppingList = user.getShoppingList();

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
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
