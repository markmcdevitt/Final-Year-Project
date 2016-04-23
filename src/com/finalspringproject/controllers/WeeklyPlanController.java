package com.finalspringproject.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.IngredientsOwned;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.ShoppingList;
import com.finalspringproject.entity.User;
import com.finalspringproject.entity.WeeklyPlan;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.WeeklyPlanService;

/**
 * DELETE FROM table WHERE date < '2011-09-21 08:21:22';
 * 
 * @author Mark
 *
 */
@Controller
public class WeeklyPlanController {

	private WeeklyPlanController weeklyPlanController;
	private String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	private WeeklyPlan weeklyPlan;
	private List<Recipe> recipeList;
	private List<WeeklyPlan> plan;
	private List<ShoppingList> completeList;
	private List<String> shoppingListIngredient;
	private List<String> shoppingListQuantity;
	private List<ShoppingList> shoppingList;
	private RecipeController recipeController;
	private IngredientController ingredientController;
	private Recipe recipe;

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private WeeklyPlanService weeklyPlanService;

	@RequestMapping(value = "/deleteRecipeFromPlan/{weeklyPlanId}/{titleParse}")
	public String deleteRecipeFromPlan(Model model, Principal principal, @PathVariable int weeklyPlanId,
			@PathVariable String titleParse) {
		String username = principal.getName();// gets the users name
		User user = weeklyPlanService.getUserWeeklyPlan(username);

		WeeklyPlan w = weeklyPlanService.getPlan(weeklyPlanId);
		List<Recipe> listOfReipes = w.getRecipe();

		for (int i = 0; i < listOfReipes.size(); i++) {
			Recipe recipe = listOfReipes.get(i);
			if (recipe.getTitleParse().equals(titleParse)) {
				listOfReipes.remove(i);
			}
		}

		w.setRecipe(listOfReipes);
		weeklyPlanService.updateWeeklyPlan(w);

		if (listOfReipes.size() == 0) {
			plan = user.getWeeklyPlan();
			for (WeeklyPlan weeklyPlan : plan) {
				if (weeklyPlan.getId() == weeklyPlanId) {
					plan.remove(weeklyPlan);
					user.setUsername(username);
					user.setWeeklyPlan(plan);
					recipeService.saveOrUpdate(user);
					weeklyPlanService.deletePlan(weeklyPlanId);
					break;
				}
			}
		}
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		model.addAttribute("userList", userList);
		return "allweeklyplans";
	}

	@RequestMapping(value = "/deleteUsersDay/{weeklyPlanId}")
	public String deleteUsersDay(Model model, @PathVariable int weeklyPlanId, Principal principal) {

		List<User> userList = new ArrayList<User>();
		boolean delete = false;

		String username = principal.getName();// gets the users name
		User user = weeklyPlanService.getUserWeeklyPlan(username);
		List<ShoppingList> shoppingList = user.getShoppingList();
		WeeklyPlan w = weeklyPlanService.getPlan(weeklyPlanId);
		List<Recipe> listOfReipes = w.getRecipe();

		for (int i = 0; i < shoppingList.size(); i++) {
			if (listOfReipes.get(0).equals(shoppingList.get(i).getIngredient())) {
				shoppingList.remove(i);
			}
		}

		user.setShoppingList(shoppingList);
		listOfReipes.clear();
		w.setRecipe(listOfReipes);

		weeklyPlanService.updateWeeklyPlan(w);
		plan = user.getWeeklyPlan();

		for (WeeklyPlan weeklyPlan : plan) {
			if (weeklyPlan.getId() == weeklyPlanId) {
				plan.remove(weeklyPlan);
				user.setUsername(username);
				user.setWeeklyPlan(plan);
				recipeService.saveOrUpdate(user);
				weeklyPlanService.deletePlan(weeklyPlanId);
				delete = true;
				break;
			}
		}

		if (delete) {
			userList.add(user);
			model.addAttribute("userList", userList);
			return "allweeklyplans";
		} else {
			System.out.println("There has been an error");
			return "home";
		}
	}

	@RequestMapping(value = "/viewyourweeklyplan")
	public String usersPlan(Model model, Principal principal) {
		List<User> userList = new ArrayList<User>();
		String username = principal.getName();
		User user = weeklyPlanService.getUserWeeklyPlan(username);
		userList.add(user);
		model.addAttribute("userList", userList);
		return "allweeklyplans";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/addToWeeklyPlan/{weeklyPlanRecipe}/{serves}")
	public String addToWeeklyPlan(Model model, Principal principal, @PathVariable int weeklyPlanRecipe,
			@PathVariable int serves) {
		recipe = recipeService.getOneRecipe(weeklyPlanRecipe);
		weeklyPlanController = new WeeklyPlanController();
		if (!(Integer.parseInt(recipe.getPeopleFed()) == serves)) {
			List<Ingredient> ingredientList = recipe.getIngredients();
			for (Ingredient ingredient : recipe.getIngredients()) {
				double ingAmount = 0;
				try {
					ingAmount = Double.parseDouble(ingredient.getIngredientAmount());// amount

				} catch (Exception e) {
				}

				if (ingAmount == 1) {
					Ingredient ing = new Ingredient();
					ing = ingredientController.wholeNumber(ingAmount, Integer.parseInt(recipe.getPeopleFed()), serves,
							ingredient);

					ingredient.setIngredientAmount(ing.getIngredientAmount());
					ingredient.setIngredientName(ing.getIngredientName());
				} else {

					double oneServing = ingAmount / Integer.parseInt(recipe.getPeopleFed());
					double newAmount = oneServing * serves;

					Ingredient ing = new Ingredient();
					ing = recipeController.ingredientAmount(newAmount, ingredient.getIngredientName());
					ingredient.setIngredientAmount(ing.getIngredientAmount());
					ingredient.setIngredientName(ing.getIngredientName());
				}

			}
			String cal = null;
			try {
				double calories = ((Double.parseDouble(recipe.getCalories()) / Integer.parseInt(recipe.getPeopleFed()))
						* serves);
				cal = String.valueOf((int) round(calories, 0));
			} catch (Exception e) {
				cal = "Unknown";
			}

			recipe.setIngredients(ingredientList);
			recipe.setPeopleFed(String.valueOf(serves));
			recipe.setCalories(cal);
			weeklyPlanController.setRecipe(recipe);

		} else {
			weeklyPlanController.setRecipe(recipe);
		}

		weeklyPlan = new WeeklyPlan(timeStamp, null);

		model.addAttribute("weeklyPlan", weeklyPlan);
		model.addAttribute("recipe", recipe);

		return "weeklyplan";
	}

	@RequestMapping(value = "/saveToWeeklyPlan", method = RequestMethod.POST)
	public String saveToWeeklyPlan(Model model, WeeklyPlan weeklyPlan, BindingResult result, Principal principal)
			throws ParseException {

		boolean newDate = true;
		ShoppingList sl = new ShoppingList();
		completeList = new ArrayList<ShoppingList>();
		shoppingListIngredient = new ArrayList<String>();
		shoppingListQuantity = new ArrayList<String>();
		shoppingList = new ArrayList<ShoppingList>();
		List<Recipe> updatedList;

		recipeList = new ArrayList<Recipe>();
		try {
			recipe = weeklyPlanController.getRecipe();
		} catch (Exception e) {
			System.out.println(" the page timed out: " + e);
			return "home";
		}

		recipeList.add(recipe);

		String newDateFormat = date(weeklyPlan.getDate());
		weeklyPlan.setDate(newDateFormat);
		weeklyPlan.setRecipe(recipeList);// puts the recipe in the weeklyPlan

		String username = principal.getName();
		User user = weeklyPlanService.getUserWeeklyPlan(username);
		try {
			shoppingList = user.getShoppingList();
			for (ShoppingList slist : shoppingList) {
				shoppingListIngredient.add(slist.getIngredient());
				shoppingListQuantity.add(slist.getQuantity());
			}
		} catch (Exception e) {
			shoppingListIngredient = new ArrayList<String>();
			shoppingListQuantity = new ArrayList<String>();
		}

		completeList.addAll(shoppingList);

		List<IngredientsOwned> ingredientsOwned = user.getIngredientsOwned();
		List<Ingredient> ingList = recipe.getIngredients();

		for (IngredientsOwned s : ingredientsOwned) {
			for (Ingredient ing : ingList) {
				if (ing.getIngredientName().contains(" " + s.getIngredientOwned() + " ")
						|| ing.getIngredientName().contains(" " + s.getIngredientOwned() + "s ")
						|| ing.getIngredientName().contains(" " + s.getIngredientOwned() + "'s ")) {
					ingList.remove(ing);
					break;
				}
			}
		}

		List<Ingredient> ingredientList = new ArrayList<Ingredient>(ingList);

		for (Ingredient i : ingredientList) {
			try {
				double number = Double.parseDouble(i.getIngredientAmount());
				int rounded = (int) Math.ceil(number);
				i.setIngredientAmount(String.valueOf(rounded));

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//List<Ingredient> ingredientList = new ArrayList<Ingredient>();
		for(Ingredient i:ingredientList){
			System.out.println("old list "+ i.toString());
		}
		System.out.println("old list size "+ingredientList.size());

     	ingredientList = ingredients(ingredientList);//HERE
     	
     	System.out.println("new list size "+ingredientList.size());

     	for(Ingredient i:ingredientList){
			System.out.println("new list "+ i.toString());
		}

		if (shoppingListIngredient.isEmpty()) {
			for (Ingredient ingredient : ingredientList) {
				ShoppingList shoppingList = new ShoppingList(ingredient.getIngredientAmount(),
						ingredient.getIngredientName());
				shoppingList = CheckforConversion(shoppingList);
				sl = new ShoppingList(shoppingList.getQuantity(), shoppingList.getIngredient());
				completeList.add(sl);
			}
		} else {
			for (Ingredient ingredient : ingredientList) {
				ShoppingList shoppingList = new ShoppingList(ingredient.getIngredientAmount(),
						ingredient.getIngredientName());
				shoppingList = CheckforConversion(shoppingList);
				sl = new ShoppingList(shoppingList.getQuantity(), shoppingList.getIngredient());
				ingredient.setIngredientAmount(sl.getQuantity());
				ingredient.setIngredientName(sl.getIngredient());
			}
			List<ShoppingList> forList = completeList;
			for (int i = 0; i < forList.size() + ingredientList.size(); i++) {
				try {
					if (shoppingListIngredient.contains(ingredientList.get(i).getIngredientName())) {
						for (int i2 = 0; i2 < completeList.size(); i2++) {

							if (completeList.get(i2).getIngredient()
									.equals(ingredientList.get(i).getIngredientName())) {

								sl = new ShoppingList(shoppingList.get(i2).getQuantity(),
										shoppingList.get(i2).getIngredient());
								Ingredient ingredient2 = ingredientList.get(i);

								String amount = sl.getQuantity();
								double realAmount = Double.parseDouble(amount);

								String amount2 = ingredient2.getIngredientAmount();
								double realAmount2 = Double.parseDouble(amount2);

								double newAmount = realAmount + realAmount2;
								int rounded = (int) Math.ceil(newAmount);
								String finalAmount = String.valueOf(rounded);
								completeList.get(i2).setQuantity(finalAmount);
								sl.setQuantity(finalAmount);
							}
						}

					} else {
						double number = Double.parseDouble(ingredientList.get(i).getIngredientAmount());
						int rounded = (int) Math.ceil(number);
						sl = new ShoppingList(String.valueOf(rounded), ingredientList.get(i).getIngredientName());
						completeList.add(sl);
					}
				} catch (Exception e) {
					System.out.println(e);
					break;
				}
			}
		}

		plan = user.getWeeklyPlan();
		for (WeeklyPlan wp : plan) {
			if (wp.getDate().equals(weeklyPlan.getDate())) {
				newDate = false;
				updatedList = wp.getRecipe();
				updatedList.add(recipe);
				wp.setRecipe(updatedList);
			}
		}

		if (newDate) {
			plan.add(weeklyPlan);
		}

		user.setUsername(username);
		user.setWeeklyPlan(plan);
		user.setShoppingList(completeList);
		recipeService.saveOrUpdate(user);
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		model.addAttribute("userList", userList);
		return "allweeklyplans";

	}

	private ShoppingList CheckforConversion(ShoppingList shoppingList2) {
		ShoppingList shoppingList = new ShoppingList();
		String tea = shoppingList2.getIngredient();

		if (tea.indexOf("teaspoon") != -1) {

			String ing = shoppingList2.getIngredient().replace("teaspoon", "gram");
			shoppingList.setIngredient(ing);
			double amountIng = Double.parseDouble(shoppingList2.getQuantity()) * 5;
			int rounded = (int) Math.ceil(amountIng);
			String amount = String.valueOf((int) rounded);
			shoppingList.setQuantity(amount);

		} else if (tea.indexOf("tablespoon") != -1) {
			String ing = shoppingList2.getIngredient().replace("tablespoon", "gram");
			shoppingList.setIngredient(ing);
			double amountIng = Double.parseDouble(shoppingList2.getQuantity()) * 14.787;
			int rounded = (int) Math.ceil(amountIng);
			String amount = String.valueOf(rounded);
			shoppingList.setQuantity(amount);

		} else if (tea.indexOf("cup") != -1) {
			String ing = shoppingList2.getIngredient().replace("cup", "gram");
			shoppingList.setIngredient(ing);
			double amountIng = Double.parseDouble(shoppingList2.getQuantity()) * 236.588;
			int rounded = (int) Math.ceil(amountIng);
			String amount = String.valueOf((int) rounded);
			shoppingList.setQuantity(amount);
		} else {
			shoppingList.setIngredient(shoppingList2.getIngredient());
			shoppingList.setQuantity(shoppingList2.getQuantity());
		}

		return shoppingList;
	}

	public String date(String olddate) throws ParseException {
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd/MM/yyyy";

		String oldDateString = olddate;
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		sdf.applyPattern(NEW_FORMAT);
		return newDateString = sdf.format(d);
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public List<Ingredient> ingredients(List<Ingredient> ingList) {

		ingList = new ArrayList<Ingredient>();

		for (Ingredient i : ingList) {
			if (i.getIngredientAmount().contains("/")) {
				System.out.println(i.getIngredientAmount());
				System.out.println("fraction");
				List<String> list = Arrays.asList(i.getIngredientAmount().split("/"));
				String whole = "0";
				int n = 0;
				if (list.get(0).contains(" ")) {
					String str = list.get(0);
					System.out.println(" has a space " + str);
					String[] splited = str.split("\\s+");
					System.out.println("splited:" + splited[0]);
					whole = splited[0] + " ";
					System.out.println("should be 1 " + splited[splited.length - 1]);
					n = Integer.parseInt(splited[splited.length - 1]);
				} else if (list.get(0).contains("-")) {
					String str = list.get(0);
					String[] splited = str.split("\\-");
					whole = splited[0] + " ";
					n = Integer.parseInt(splited[splited.length - 1]);
				} else {
					n = Integer.parseInt(list.get(0));
				}
				System.out.println(list.get(0) + "something " + list.get(1));
				int d = Integer.parseInt(list.get(1));
				list = null;
				double fraction = (double) n / (double) d;
				System.out.println(fraction + " = " + (double) n + "/" + (double) d);
				double complete = Double.parseDouble(whole) + fraction;
				System.out.println(complete + " = " + whole + "+" + fraction);

				String string = (String.valueOf(complete));
				i.setIngredientName(i.getIngredientName());
				i.setIngredientAmount(string);
			}

		}
		for (Ingredient i : ingList) {
			System.out.println("new list diff method "+i.toString());
		}
		return ingList;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public WeeklyPlan getWeeklyPlan() {
		return weeklyPlan;
	}

	public void setRecipe(WeeklyPlan weeklyPlan) {
		this.weeklyPlan = weeklyPlan;
	}

	@Autowired
	public void setIngredientController(IngredientController ingredientController) {
		this.ingredientController = ingredientController;
	}

	@Autowired
	public void setRecipeController(RecipeController recipeController) {
		this.recipeController = recipeController;
	}
}