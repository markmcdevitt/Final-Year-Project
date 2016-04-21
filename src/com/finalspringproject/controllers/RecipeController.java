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
import com.finalspringproject.entity.Category;
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

	@RequestMapping(method = RequestMethod.GET, value = "/recipe/{id}")
	public String showSpecificRecipe(@PathVariable int id, Model model) {

		List<Recipe> recipe = getOneRecipe(id);
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
			@RequestParam(value = "type") String category,
			@RequestParam(value = "ingredientName") String ingredientName, BindingResult result, Principal principal,
			@RequestParam(value = "delete", required = false) String delete) {

		List<Ingredient> ingList = ingredients(ingredientAmount, ingredientName);
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

			recipeService.saveOrUpdate(user);

			List<Recipe> recipeList = getOneRecipe(recipe.getId());
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

			recipeList2 = recipeService.getGeneratedRecipe(" " + s);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + " ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + ",");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + "s,");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + "s ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(" " + s + "'s ");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipe(s + "es ");
			r.addAll(recipeList2);

			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s2);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s);
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + ",");
			r.addAll(recipeList2);
			recipeList2 = recipeService.getGeneratedRecipeSingleWord(s + "es");
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
		}else if (amount % 1 == 0) {
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
			level = "Amatuer Cook";
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
			System.out.println("no decimal point " + noDecimalPoint);
			finishedAmount = String.valueOf(noDecimalPoint);
		} else {
			String aString = Double.toString(amount);
			String[] fraction = aString.split("\\.");
			denominator = (int) Math.pow(10, fraction[1].length());

			if (fraction[1].equals("333333333333333") || fraction[1].contains("3333333333333333")) {
				fraction[1] = "33";
				denominator = 99;
			} else if (fraction[1].equals("666666666666667") || fraction[1].equals("666666666666666")) {
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
				System.out.println("numerator " + numerator);
				finishedAmount = String.valueOf(numerator);
			} else {
				finishedAmount = numerator + "/" + denominator;
			}

		}
		if (amount == 0.0) {
			System.out.println("in the zero " + amount);
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
			
			System.out.println(name+ " can be diveded by 3 and has a teaspoon "+ amount);
			
			tablespoon = (int) (amount / 3.0);
			name.replace("teaspoon", "tablespoon");
			ingredient.setIngredientName(name);
			System.out.println(name);
			ingredient.setIngredientAmount(String.valueOf(tablespoon));
			System.out.println(ingredient.getIngredientName());

		} else if (name.contains("teaspoon")) {

			System.out.println(name+ " cannot be diveded by 3 and has a teaspoon "+ amount);
			System.out.println("no here");
			int check = (int) amount;
			do {
				check -= 3;
				amount -= 3;
				tablespoon += 1;
			} while (check >= 3);
			ingredient.setIngredientName(name);

			finishedAmount = tablespoon + " tablespoons and " + amount;
			ingredient.setIngredientAmount(String.valueOf(finishedAmount));
		} else {
			System.out.println(name+ " NO TEASPOON "+ amount);
			System.out.println("HEREEEE " + noDecimalPoint);
			finishedAmount = String.valueOf(noDecimalPoint);
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

		if (fraction[1].equals("333333333333333") || fraction[1].contains("3333333333333333")) {
			fraction[1] = "33";
			denominator = 99;
		} else if (fraction[1].equals("666666666666667") || fraction[1].equals("666666666666666")) {
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
			if (name.contains("teaspoon") && whole >= 3) {
				int tablespoon = 0;
				if (whole % 3 == 0) {
					tablespoon = whole / 3;
					finishedAmount = tablespoon + " tablespoons " + newNum2 + "/" + denominator;
					ingredient.setIngredientAmount(finishedAmount);
					ingredient.setIngredientName(name);
				} else {
					int check = whole;
					do {
						check -= 3;
						whole -= 3;
						tablespoon += 1;
					} while (check >= 3);
					finishedAmount = tablespoon + " tablespoons " + whole + "-" + newNum2 + "/" + denominator;
					ingredient.setIngredientAmount(finishedAmount);
					ingredient.setIngredientName(name);
				}

			} else {

				finishedAmount = whole + "-" + newNum2 + "/" + denominator;
				ingredient.setIngredientAmount(finishedAmount);
				ingredient.setIngredientName(name);
			}
		} else if (numerator > denominator && (denominator == 1) || numerator > denominator && (denominator == 0)) {
			System.out.println("numerator " + numerator);
			finishedAmount = String.valueOf(numerator);
			ingredient.setIngredientAmount(finishedAmount);
			ingredient.setIngredientName(name);
		} else {
			finishedAmount = numerator + "/" + denominator;
			ingredient.setIngredientAmount(finishedAmount);
			ingredient.setIngredientName(name);
		}

		return ingredient;
	}
}
