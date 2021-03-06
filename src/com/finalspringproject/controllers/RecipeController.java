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

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.collections.CollectionUtils;
import org.apache.taglibs.standard.functions.Functions;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.hibernate.sql.ordering.antlr.OrderingSpecification.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.entity.Category;
import com.finalspringproject.entity.Favorite;
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.Instructions;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.Review;
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

	@RequestMapping(method = RequestMethod.GET, value = "/recipe/{id}")
	public String showSpecificRecipe(@PathVariable int id, Model model, Principal principal) {

		User user;
		List<Recipe> recipe = getOneRecipe(id);
		try {
			user = usersService.getUser(principal.getName());
		} catch (Exception e) {
			user = new User();
			user.setUserLevel("Newbie");
		}
		String level = recipe.get(0).getLevel();

		int recipeLevel = levelCheck(level);
		int userLevel = levelCheck(user.getUserLevel());

		String answer;
		if (userLevel >= recipeLevel) {
			answer = recipe.get(0).getLevel();
		} else {
			answer = "unknown";
		}
		String ableToReview = "false";
		ableToReview = ableToReview(ableToReview, user, recipe);
		String fav = "false";
		fav = FavoriteCheck(user, fav, recipe);

		model.addAttribute("fav", fav);
		model.addAttribute("review", ableToReview);
		model.addAttribute("answer", answer);
		model.addAttribute("recipe", recipe);
		return "recipe";
	}

	@RequestMapping("/createrecipe")
	public String createRecipe() {
		return "createrecipe";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Model model, Recipe recipe, BindingResult result,
			@RequestParam(value = "ingredientQuantity") String ingredientAmount,
			@RequestParam(value = "type") String category,
			@RequestParam(value = "ingredientName") String ingredientName, Principal principal,
			@RequestParam(value = "delete", required = false) String delete) {

		if (recipe.getCalories().equals(",no")) {
			recipe.setCalories("Unknown");
		} else {
			String calories = recipe.getCalories().replace(",no", "");
			recipe.setCalories(calories);
		}
		List<String> amountList = Arrays.asList(ingredientAmount.split(","));

		for (String i : amountList) {
			String regex = "\\d{1,5}([.]\\d{1,3}|(\\s\\d{1,5})?[/]\\d{1,3})?";
			if (!i.matches(regex)) {
				return "createreciperegex";
			}
		}

		List<Ingredient> ingList = ingredients(ingredientAmount, ingredientName, recipe);

		recipeList = new ArrayList<Recipe>();
		if (result.hasErrors()) {
			return "createrecipe";
		}

		if (delete == null) {
			User user = usersService.getUser(principal.getName());
			Category categoryObj = new Category(category);
			List<Recipe> list = user.getRecipes();

			int score = recipe.getInstructions().size() + ingList.size() + Integer.parseInt(recipe.getPeopleFed());

			String level = recipeScore(score, recipe.getInstructions());

			recipe.setLevel(level);
			recipe.setIngredients(ingList);
			recipe.setCategory(categoryObj);

			list.add(recipe);
			user.setRecipes(list);

			try {
				Recipe recipe2 = recipeService.getCurrentRecipe(recipe.getTitleParse());
				recipeService.saveOrUpdate(user);
			} catch (Exception e) {
				result.rejectValue("titleParse", "DuplicateName.recipe.titleparse");
				return "createRecipeDuplicate";
			}

			List<Recipe> recipeList = getOneRecipe(recipe.getId());
			String ableToReview = "true";
			
			
			int recipeLevel = levelCheck(level);
			int userLevel = levelCheck(user.getUserLevel());

			String answer;
			if (userLevel >= recipeLevel) {
				answer = level;
			} else {
				answer = "unknown";
			}

			model.addAttribute("answer", answer);
			model.addAttribute("review", ableToReview);
			model.addAttribute("recipe", recipeList);

	
			return "recipe";
		} else {
			recipeService.delete(recipe.getId());
			return "home";
		}

	}

	public List<Ingredient> ingredients(String ingredientAmount, String ingredientName, Recipe recipe) {

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

		}
		for (Ingredient i : ingList) {
			if (i.getIngredientAmount().contains("/")) {
				List<String> list = Arrays.asList(i.getIngredientAmount().split("/"));
				for (String l : list) {
					l.trim();
				}
				String whole = "0";
				int n = 0;
				if (list.get(0).contains(" ")) {
					String str = list.get(0);
					String[] splited = str.split("\\s+");
					whole = splited[0] + " ";
					n = Integer.parseInt(splited[splited.length - 1]);
				} else if (list.get(0).contains("-")) {
					String str = list.get(0);
					String[] splited = str.split("\\-");
					whole = splited[0] + " ";
					n = Integer.parseInt(splited[splited.length - 1]);
				} else {
					n = Integer.parseInt(list.get(0));
				}
				int d = Integer.parseInt(list.get(1));
				list = null;

				double fraction = (double) n / (double) d;
				double complete = Double.parseDouble(whole) + fraction;

				String string = (String.valueOf(complete));
				i.setIngredientName(i.getIngredientName());
				i.setIngredientAmount(string);
			}

		}
		return ingList;
	}

	public Ingredient wholeNumber(double ingAmount, int serves, int quantity, Ingredient ingredient) {

		double oneServing = ingAmount / serves;
		double newAmount = oneServing * quantity;

		if (newAmount % 1 == 0) {
			int tablespoon = 0;

			if (newAmount % 3 == 0 && ingredient.getIngredientName().contains("teaspoon")) {

				tablespoon = (int) (newAmount / 3.0);
				String name = ingredient.getIngredientName().replace("teaspoon", "tablespoon");
				ingredient.setIngredientName(name);
				ingredient.setIngredientAmount(String.valueOf(tablespoon));

			} else if (ingredient.getIngredientName().contains("teaspoon") && newAmount >= 3) {

				int check = (int) newAmount;
				do {
					check -= 3;
					newAmount -= 3;
					tablespoon += 1;
				} while (check >= 3);

				String finishedAmount = tablespoon + " tablespoons and " + newAmount;
				ingredient.setIngredientAmount(String.valueOf(finishedAmount));
			} else {

				ingredient.setIngredientAmount(String.valueOf((int) newAmount));
			}

		} else {
			ingredient = ingredientAmount(newAmount, ingredient.getIngredientName());
		}

		return ingredient;
	}

	@RequestMapping("/findrecipe")
	public String findRecipe(Model model, @Validated(value = FormValidationGroup.class) Ingredient ingredient,
			BindingResult result) throws InterruptedException {

		List<String> list = Arrays.asList(ingredient.getIngredientName().split(","));
		List<Recipe> recipeList2 = new ArrayList<Recipe>();
		ArrayList<Recipe> r = new ArrayList<Recipe>();

		for (String s : list) {
			String s2 = removeLastChar(s);

			recipeList2 = recipeService.getGeneratedRecipe(" " + s);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + " ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + ",");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(s + "es ");
			r.addAll(recipeList2);

			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s2);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + ",");
			r.addAll(recipeList2);

		}
		ArrayList<Integer> anotherList = new ArrayList<Integer>();
		ArrayList<Recipe> anotherList2 = new ArrayList<Recipe>();

		for (Recipe recipe : r) {
			if ((recipe.getIngredients().size() > 4)) {
				anotherList2.add(recipe);
			}
		}
		r.clear();
		r = new ArrayList<Recipe>(anotherList2);

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
				int score = freq - (recipe.getIngredients().size() - freq);
				System.out.println(freq + " ---> " + score);
				hmap.put(recipe, score);
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
		List<Recipe> shortenedRecipeList = new ArrayList<>();
		try {
			for (int i = 0; i <= 29; i++) {
				System.out.println(recipeList.get(i).getTitleParse());
				shortenedRecipeList.add(recipeList.get(i));
			}
			model.addAttribute("recipe", shortenedRecipeList);
		} catch (Exception e) {
			model.addAttribute("recipe", recipeList);
		}

		model.addAttribute("matches", matches);
		return "result";

	}

	public Ingredient ingredientAmount(double amount, String name) {
		Ingredient ingredient = new Ingredient();
		ingredient.setIngredientName(name);
		String finishedAmount;

		if (amount == 0.0) {
			finishedAmount = "-";
			ingredient.setIngredientAmount(finishedAmount);
		} else if (amount % 1 == 0) {
			ingredient = noFraction(amount, name);
		} else {
			ingredient = fraction(amount, name);
		}
		return ingredient;
	}

	public List<Recipe> getOneRecipe(int id) {
		List<Ingredient> ingredient = new ArrayList<Ingredient>();
		Recipe oneRecipe = recipeService.getOneRecipe(id);

		for (Ingredient i : oneRecipe.getIngredients()) {
			Ingredient ing = new Ingredient();
			String createAmount = i.getIngredientAmount();
			double amount = 0;
			try {
				amount = Double.parseDouble(createAmount);

			} catch (Exception e) {
				// TODO: handle exception
			}

			String finishedAmount = ingredientAmount(amount);
			ing.setIngredientAmount(finishedAmount);
			ing.setIngredientName(i.getIngredientName());
			ingredient.add(ing);
		}

		oneRecipe.setIngredients(ingredient);

		List<Recipe> recipe = new ArrayList<Recipe>();
		recipe.add(oneRecipe);
		return recipe;
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	@RequestMapping("/title/alphabetical")
	public String alphabeticalAuthor(Model model) {
		List<Recipe> recipes = recipeService.getCurrent();

		Collections.sort(recipes, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipes", recipes);
		return "allrecipes";
	}

	@RequestMapping("/rating/alphabetical")
	public String alphabeticalRating(Model model) {
		List<Recipe> recipes = recipeService.getCurrent();

		Collections.sort(recipes, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return Double.compare(Double.parseDouble(object2.getTotalRating()),
						Double.parseDouble(object1.getTotalRating()));
			}
		});
		model.addAttribute("allrecipes", recipes);
		return "allbooks";
	}

	private String recipeScore(int score, List<Instructions> instructionList) {

		ArrayList<String> complicatedWords = new ArrayList<String>();

		complicatedWords.add("chopped");
		complicatedWords.add("diced");
		complicatedWords.add("saute");
		complicatedWords.add("grated");
		complicatedWords.add("dash");
		complicatedWords.add("smidge");
		complicatedWords.add("minced");
		complicatedWords.add("sliced");
		complicatedWords.add("pan fry");
		complicatedWords.add("liquid measuring cup");
		complicatedWords.add("broiling");
		complicatedWords.add("simmer");
		complicatedWords.add("shredded");

		for (int i = 0; i < complicatedWords.size(); i++) {
			for (Instructions instruction : instructionList) {
				if (instruction.getSteps().contains(complicatedWords.get(i))) {
					score += 5;
				}
			}

		}
		String level;

		if (score > 45) {
			level = "Master Chef";
		} else if (score > 40) {
			level = "Executive Chef";
		} else if (score > 35) {
			level = "Sous Chef";
		} else if (score > 30) {
			level = "Prep Chef";
		} else if (score > 25) {
			level = "Wise Chef";
		} else if (score > 20) {
			level = "Gifted Chef";
		} else if (score > 16) {
			level = "Amateur Cook";
		} else {
			level = "Newbie";
		}
		return level;
	}

	public String ingredientAmount(double amount) {
		int denominator;
		int numerator;
		String finishedAmount;
		if (amount % 1 == 0) {
			int noDecimalPoint = (int) amount;
			finishedAmount = String.valueOf(noDecimalPoint);
		} else {
			String aString = Double.toString(amount);
			String[] fraction = aString.split("\\.");
			denominator = (int) Math.pow(10, fraction[1].length());

			if (fraction[1].equals("333333333333333") || fraction[1].contains("3333333333333333")) {
				fraction[1] = "33";
				denominator = 99;
			} else if (fraction[1].equals("666666666666667") || fraction[1].equals("666666666666666")
					|| fraction[1].equals("06666666666666666") || fraction[1].equals("6666666666666666")) {
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
			if (numerator > denominator && !(denominator == 1)) {
				int whole = (int) Math.floor(numerator / denominator);
				int newNum2 = numerator - (whole * denominator);

				finishedAmount = whole + "-" + newNum2 + "/" + denominator;
			} else if (numerator > denominator && (denominator == 1) || numerator > denominator && (denominator == 0)) {
				finishedAmount = String.valueOf(numerator);
			} else {
				finishedAmount = numerator + "/" + denominator;
			}

		}
		if (amount == 0.0) {
			finishedAmount = "-";
		}
		return finishedAmount;
	}

	public Ingredient noFraction(double amount, String name) {
		Ingredient ingredient = new Ingredient();
		ingredient.setIngredientName(name);
		int noDecimalPoint = (int) amount;
		String finishedAmount = null;
		int tablespoon = 0;

		if (amount % 3 == 0 && name.contains("teaspoon")) {

			tablespoon = (int) (amount / 3.0);
			String newName = name.replace("teaspoon", "tablespoon");
			ingredient.setIngredientName(newName);
			ingredient.setIngredientAmount(String.valueOf(tablespoon));

		} else if (ingredient.getIngredientName().contains("teaspoon") && amount >= 3) {

			int check = (int) amount;
			do {
				check -= 3;
				amount -= 3;
				tablespoon += 1;
			} while (check >= 3);

			ingredient.setIngredientName(name);

			finishedAmount = tablespoon + " tablespoon and " + (int) amount;

			ingredient.setIngredientAmount(String.valueOf(finishedAmount));
		} else {
			finishedAmount = String.valueOf((int) noDecimalPoint);
			ingredient.setIngredientAmount(String.valueOf(finishedAmount));
		}
		return ingredient;

	}

	public Ingredient fraction(double amount, String name) {
		Ingredient ingredient = new Ingredient();
		int denominator;
		int numerator;
		String finishedAmount = null;
		String aString = Double.toString(amount);
		String[] fraction = aString.split("\\.");
		denominator = (int) Math.pow(10, fraction[1].length());
		if (fraction[1].equals("333333333333333") || fraction[1].contains("3333333333333333")
				|| fraction[1].contains("3333333333333333") && denominator == 2147483647) {
			fraction[1] = "33";
			denominator = 99;
		} else if (fraction[1].contains("1666") || fraction[1].contains("166666")) {
			fraction[1] = "1";
			denominator = 6;
		} else if (fraction[1].equals("666666666666667") || fraction[1].equals("666666666666666")
				|| fraction[1].equals("6666666666666666") || fraction[1].equals("06666666666666666")
				|| fraction[1].equals("6666666666666665") || fraction[1].contains("6666666666666666")) {
			fraction[1] = "66";
			denominator = 99;
		} else if (fraction[1].contains("0833333")) {
			fraction[1] = "1";
			denominator = 12;
		} else if (fraction[1].contains("8333333333333333")) {
			fraction[1] = "5";
			denominator = 6;
		} else if (fraction[1].contains("8999999999999999")) {
			fraction[1] = "9";
			denominator = 10;
		}

		numerator = Integer.parseInt(fraction[0] + "" + fraction[1]);

		for (int i2 = 2; i2 <= 33; i2++) {
			if (numerator % i2 == 0 && denominator % i2 == 0) {
				numerator = numerator / i2;
				denominator = denominator / i2;
				i2 = 2;
			}
		}

		if (numerator > denominator && !(denominator == 1)) {
			int whole = (int) Math.floor(numerator / denominator);
			int newNum2;
			if (!(numerator == 233) && !(numerator == 133) && !(numerator == 266) && !(numerator == 166)
					&& !(numerator == 333) && !(numerator == 366) && !(numerator == 433) && !(numerator == 466)
					&& !(numerator == 533) && !(numerator == 566) && !(numerator == 666) && !(numerator == 633)) {
				newNum2 = numerator - (whole * denominator);
			} else if ((numerator == 166) || (numerator == 266) || (numerator == 366) || (numerator == 466)
					|| (numerator == 566)) {
				newNum2 = 2;
				denominator = 3;
			} else {
				newNum2 = 1;
				denominator = 3;
			}

			if (name.contains("teaspoon") && whole >= 3) {
				int tablespoon = 0;
				if (whole % 3 == 0) {
					tablespoon = whole / 3;
					finishedAmount = tablespoon + " tablespoons and " + newNum2 + "/" + denominator;
					ingredient.setIngredientAmount(finishedAmount);
					ingredient.setIngredientName(name);
				} else {
					int check = whole;
					do {
						check -= 3;
						whole -= 3;
						tablespoon += 1;
					} while (check >= 3);
					finishedAmount = tablespoon + " tablespoons and " + whole + "-" + newNum2 + "/" + denominator;
					ingredient.setIngredientAmount(finishedAmount);
					ingredient.setIngredientName(name);
				}

			} else {

				finishedAmount = whole + "-" + newNum2 + "/" + denominator;
				System.out.println("in here 3 " + finishedAmount);
				ingredient.setIngredientAmount(finishedAmount);
				ingredient.setIngredientName(name);
			}
		} else if (numerator > denominator && (denominator == 1) || numerator > denominator && (denominator == 0)) {

			finishedAmount = String.valueOf(numerator);
			System.out.println("in here2 " + finishedAmount);
			ingredient.setIngredientAmount(finishedAmount);
			ingredient.setIngredientName(name);
		} else {

			finishedAmount = numerator + "/" + denominator;
			System.out.println("in here " + finishedAmount);
			ingredient.setIngredientAmount(finishedAmount);
			ingredient.setIngredientName(name);
		}

		return ingredient;
	}

	public int levelCheck(String level) {

		int check;

		if (level.equals("Master Chef")) {
			check = 8;
		} else if (level.equals("Executive Chef")) {
			check = 7;
		} else if (level.equals("Sous Chef")) {
			check = 5;
		} else if (level.equals("Prep Chef")) {
			check = 5;
		} else if (level.equals("Wise Chef")) {
			check = 4;
		} else if (level.equals("Gifted Chef")) {
			check = 3;
		} else if (level.equals("Amatuer Cook")) {
			check = 2;
		} else {
			check = 1;
		}
		return check;
	}

	public String ableToReview(String ableToReview, User user, List<Recipe> recipe) {
		try {
			List<Recipe> recipeList = user.getRecipes();
			for (Recipe r : recipeList) {
				if (recipe.get(0).getTitleParse().equals(r.getTitleParse())) {
					ableToReview = "true";
				}
			}
		} catch (Exception e) {
			System.out.println("recipe catch");
		}
		try {
			List<Review> reviewList = recipe.get(0).getReview();
			for (Review review : reviewList) {
				if (review.getUser().getUsername().equals(user.getUsername())) {
					ableToReview = "true";
				}
			}
		} catch (Exception e) {
		}
		return ableToReview;
	}

	public String FavoriteCheck(User user, String fav, List<Recipe> recipe) {
		try {
			List<Favorite> favorites = user.getUsersFavorites();
			for (Favorite favorite : favorites) {
				if (favorite.getRecipe().getTitleParse().equals(recipe.get(0).getTitleParse())) {
					fav = "true";
				}
			}
		} catch (Exception e) {

		}
		return fav;
	}

	@RequestMapping("/getMainDishes")
	public String getMainDishes(Model model) {
		List<Recipe> categoryRecipe = recipeService.getSpecific("Main Dish");

		Collections.sort(categoryRecipe, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipe", categoryRecipe);
		return "allrecipes";

	}

	@RequestMapping("/getVegetarian")
	public String getVegetarian(Model model) {
		List<Recipe> categoryRecipe = recipeService.getSpecific("Vegetarian");

		Collections.sort(categoryRecipe, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipe", categoryRecipe);
		return "allrecipes";

	}

	@RequestMapping("/getAppetisers")
	public String getAppetisers(Model model) {
		List<Recipe> categoryRecipe = recipeService.getSpecific("Appetizers");

		Collections.sort(categoryRecipe, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipe", categoryRecipe);
		return "allrecipes";

	}

	@RequestMapping("/getDessert")
	public String getDessert(Model model) {
		List<Recipe> categoryRecipe = recipeService.getSpecific("Dessert");

		Collections.sort(categoryRecipe, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipe", categoryRecipe);
		return "allrecipes";

	}

	@RequestMapping("/allrecipes")
	public String showRecipe(Model model) {
		List<Recipe> recipes = recipeService.getCurrent();

		Collections.sort(recipes, new Comparator<Recipe>() {
			@Override
			public int compare(final Recipe object1, final Recipe object2) {
				return object1.getTitleParse().compareTo(object2.getTitleParse());
			}
		});
		model.addAttribute("recipe", recipes);
		return "allrecipes";
	}
}
