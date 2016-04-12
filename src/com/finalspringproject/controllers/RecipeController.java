package com.finalspringproject.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.taglibs.standard.functions.Functions;
import org.hibernate.sql.ordering.antlr.OrderingSpecification.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Instructions;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@Controller
public class RecipeController {

	private List<Recipe> recipeList;
	private RecipeService recipeService;
	private UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/foodgenerator")
	public String foodgenerator(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "foodgenerator";
	}

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/allrecipes")
	public String showRecipe(Model model) {

		List<Recipe> recipe = recipeService.getCurrent();
		model.addAttribute("recipe", recipe);
		return "allrecipes";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/recipe/{titleParse}")
	public String showSpecificRecipe(@PathVariable String titleParse, Model model) {

		List<Recipe> recipe = getOneRecipe(titleParse);
		model.addAttribute("recipe", recipe);
		return "recipe";
	}

	@RequestMapping("/createrecipe")
	public String createRecipe(Model model, Principal principal) {
		Recipe recipe = null;
		if (principal != null) {
			String username = principal.getName();
			recipe = null;
		}

		if (recipe == null) {
			recipe = new Recipe();
		}
		model.addAttribute("recipe", recipe);
		return "createrecipe";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Model model, @Validated(value = FormValidationGroup.class) Recipe recipe,
			@RequestParam(value = "ingredientQuantity") String ingredientAmount,
			@RequestParam(value = "ingredientName") String ingredientName, BindingResult result, Principal principal,
			@RequestParam(value = "delete", required = false) String delete) {

		List<Ingredient> ingList = ingredients(ingredientAmount, ingredientName);
		recipeList = new ArrayList<Recipe>();
		if (result.hasErrors()) {
			return "createrecipe";
		}

		if (delete == null) {
			User user = usersService.getUser(principal.getName());

			List<Recipe> list = user.getRecipes();
			recipe.setIngredients(ingList);
			list.add(recipe);
			user.setRecipes(list);

			recipeService.saveOrUpdate(user);

			List<Recipe> recipeList = getOneRecipe(recipe.getTitleParse());
			model.addAttribute("recipe", recipeList);
			return "recipe";
		} else {
			recipeService.delete(recipe.getId());
			return "home";
		}

	}

	public List<Ingredient> ingredients(String ingredientAmount, String ingredientName) {
		List<Ingredient> ingList = new ArrayList<Ingredient>();
		List<String> amountList = Arrays.asList(ingredientAmount.split(","));
		List<String> nameList = Arrays.asList(ingredientName.split(","));

		try {
			for (int i = 0; i < nameList.size(); i++) {
				Ingredient ingredient = new Ingredient();
				String x = nameList.get(i);
				String y = amountList.get(i);

				ingredient.setIngredientAmount(y);
				ingredient.setIngredientName(x);
				ingList.add(ingredient);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ingList;
	}

	@RequestMapping("/findrecipe")
	public String findRecipe(Model model, @Validated(value = FormValidationGroup.class) Ingredient ingredient,
			BindingResult result) throws InterruptedException {
		
		List<String> list = Arrays.asList(ingredient.getIngredientName().split(","));
		List<Recipe> recipeList2 = new ArrayList<Recipe>();
		ArrayList<Recipe> r = new ArrayList<Recipe>();

		for (String s : list) {
			String s2 = removeLastChar(s);

			recipeList2 = recipeService.getGeneratedRecipe(" " + s + " ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + "s ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + "'s ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(s + "es ");
			r.addAll(recipeList2);

			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s2);
			System.out.println(s2);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + "s");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + "es");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + "'s");
			r.addAll(recipeList2);

			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s2);
			r.addAll(recipeList2);

		}
		ArrayList<Integer> anotherList = new ArrayList<Integer>();

		for (Recipe idList : r) {
			int id = idList.getId();
			anotherList.add(id);
		}
		ArrayList<Integer> frequencyList = new ArrayList<Integer>();
		for (Integer fList : anotherList) {
			int frequency = Collections.frequency(anotherList, fList);
			frequencyList.add(frequency);
		}
		ArrayList<Integer> tracker = new ArrayList<Integer>();
		HashMap<Recipe, Integer> hmap = new HashMap<Recipe, Integer>();
		for (int i = 0; i < frequencyList.size(); i++) {
			int freq = frequencyList.get(i);
			Recipe recipe = r.get(i);
			if (!tracker.contains(recipe.getId())) {
				tracker.add(recipe.getId());
				System.out.println("HERE  --- " + recipe.getTitleParse() + "/" + freq);
				hmap.put(recipe, freq);
			}
		}
		ArrayList<Recipe> waitingList = new ArrayList<Recipe>();
		List<Map.Entry<Recipe, Integer>> entries = new ArrayList<Map.Entry<Recipe, Integer>>(hmap.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<Recipe, Integer>>() {
			public int compare(Map.Entry<Recipe, Integer> a, Map.Entry<Recipe, Integer> b) {
				return Integer.compare(b.getValue(), a.getValue());
			}
		});

		List<String> matches = new ArrayList<String>();

		for (Map.Entry<Recipe, Integer> e : entries) {
			waitingList.add(e.getKey());
			matches.add(String.valueOf(e.getValue()));
		}

		Set<Recipe> set = new LinkedHashSet<>(waitingList);
		List<Recipe> recipeList = new ArrayList<Recipe>();
		recipeList.addAll(set);
		model.addAttribute("recipe", recipeList);
		model.addAttribute("matches", matches);
		return "result";

	}

	public String ingredientAmount(double amount) {
		int denominator;
		int numerator;
		String finishedAmount;
		System.out.println(amount);
		if (amount % 1 == 0) {
			int noDecimalPoint = (int) amount;
			finishedAmount = String.valueOf(noDecimalPoint);
		} else {
			String aString = Double.toString(amount);
			String[] fraction = aString.split("\\.");
			denominator = (int) Math.pow(10, fraction[1].length());

			if (fraction[1].equals("333333333333333")) {
				fraction[1] = "33";
				denominator = 99;
			}
			if (fraction[1].equals("666666666666667") || fraction[1].equals("666666666666666")) {
				fraction[1] = "66";
				denominator = 99;
			}
			numerator = Integer.parseInt(fraction[0] + "" + fraction[1]);
			for (int i2 = 2; i2 <= 33; i2++) {
				if (numerator % i2 == 0 && denominator % i2 == 0) {
					numerator = numerator / i2;
					denominator = denominator / i2;
					i2 = 2;
				}
			}
			finishedAmount = numerator + "/" + denominator;
		}
		if (amount == 0.0) {
			System.out.println("in the zero " + amount);
			finishedAmount = "-";
		}
		return finishedAmount;
	}

	public List<Recipe> getOneRecipe(String title) {
		List<Ingredient> ingredient = new ArrayList<Ingredient>();
		List<Recipe> recipe = recipeService.getCurrent(title);
		System.out.println(recipe.size());
		try {
			for (Ingredient i : recipe.get(0).getIngredients()) {
				Ingredient ing = new Ingredient();
				String createAmount = i.getIngredientAmount();
				double amount = Double.parseDouble(createAmount);

				String finishedAmount = ingredientAmount(amount);
				ing.setIngredientAmount(finishedAmount);
				ing.setIngredientName(i.getIngredientName());
				ingredient.add(ing);

			}
			recipe.get(0).setIngredients(ingredient);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return recipe;
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}
}
