package com.finalspringproject.test.tests;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finalspringproject.entity.Allergy;
import com.finalspringproject.entity.Category;
import com.finalspringproject.entity.Favorite;
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.entity.IngredientsOwned;
import com.finalspringproject.entity.Instructions;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.ShoppingList;
import com.finalspringproject.entity.User;
import com.finalspringproject.entity.WeeklyPlan;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/finalspringproject/config/dao-context.xml",
		"classpath:com/finalspringproject/config/security-context.xml",
		"classpath:com/finalspringproject/test/config/datasource.xml",
		"classpath:com/finalspringproject/config/service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTests {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private RecipeService recipeService;
	private String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());



	@Test
	public void testCreateUser() {

		User user = new User("Terry", "O2005@hotmail.com", "letmein", true, "ROLE_USER", null, null, null, null, null,
				"Newbie", null);

		User retrieveUser = usersService.getUser(user.getUsername());
		assertEquals(user.getUsername(), retrieveUser.getUsername());

		// assertTrue("User should exist ",usersDao.exists("mark"));
	
	}

	@Test
	public void testCreateRecipe() {
		Category category = new Category("Main");

		Instructions instruction = new Instructions("make the recipe");
		Instructions instruction2 = new Instructions("eat the meal");
		List<Instructions> instructionsList = new ArrayList<Instructions>();
		instructionsList.add(instruction);
		instructionsList.add(instruction2);

		Ingredient ingredient = new Ingredient("egg", "2");
		Ingredient ingredient2 = new Ingredient("teaspoons of flour", "2");
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		ingredientsList.add(ingredient);
		ingredientsList.add(ingredient2);

		Recipe recipe = new Recipe("French Toast", "Master Level", "This is a very tasty dish", "image", "4", "400",
				category, instructionsList, ingredientsList, "4.5");

		User user = usersService.getUser("Terry");

		List<Recipe> recipeList = new ArrayList<Recipe>();
		System.out.println(recipe.toString());
		recipeList.add(recipe);
		user.setRecipes(recipeList);

		// usersDao.update(user);

		User secondRetrievedUser = usersService.getUser(user.getUsername());

		assertEquals(recipeList.size(), secondRetrievedUser.getRecipes().size());
	}

	@Test
	public void testFavouriteRecipe() {

		Recipe recipe = recipeService.getRecipe(1);
		User user = usersService.getUser("Terry");

		Favorite favorite = new Favorite(recipe);
		List<Favorite> favoriteList = new ArrayList<>();
		favoriteList.add(favorite);
		user.setUsersFavorites(favoriteList);

		// usersDao.update(user);

		User thirdRetrievedUser = usersService.getUser(user.getUsername());

		assertEquals(favoriteList.size(), thirdRetrievedUser.getUsersFavorites().size());
	}

	@Test
	public void testWeeklyPlan() {

		Recipe recipe = recipeService.getRecipe(1);
		User user = usersService.getUser("Terry");

		List<Recipe> recipeList = new ArrayList<Recipe>();
		recipeList.add(recipe);

		WeeklyPlan weeklyplan = new WeeklyPlan(timeStamp, recipeList);
		List<WeeklyPlan> weeklyPlanList = new ArrayList<>();
		weeklyPlanList.add(weeklyplan);

		user.setWeeklyPlan(weeklyPlanList);
		// usersDao.update(user);

		User fourthRetrievedUser = usersService.getUser(user.getUsername());
		assertEquals(weeklyPlanList.size(), fourthRetrievedUser.getWeeklyPlan().size());
	}

	@Test
	public void testShoppingList() {

		ShoppingList shoppingList = new ShoppingList("eggs", "2");
		ShoppingList shoppingList2 = new ShoppingList("teaspoons of flour", "4");
		ShoppingList shoppingList3 = new ShoppingList("loafs of bread", "3");
		ShoppingList shoppingList4 = new ShoppingList("pound of butter", "1");

		List<ShoppingList> listOfShoppingList = new ArrayList<ShoppingList>();
		listOfShoppingList.add(shoppingList);
		listOfShoppingList.add(shoppingList2);
		listOfShoppingList.add(shoppingList3);
		listOfShoppingList.add(shoppingList4);

		User user = usersService.getUser("Terry");
		user.setShoppingList(listOfShoppingList);

		// usersDao.update(user);

		User fifthRetrievedUser = usersService.getUser(user.getUsername());
		assertEquals(listOfShoppingList.size(), fifthRetrievedUser.getShoppingList().size());

	}

	@Test
	public void testIngredientsOwned() {

		IngredientsOwned ingredientOwned = new IngredientsOwned("milk");
		IngredientsOwned ingredientOwned2 = new IngredientsOwned("bread");
		IngredientsOwned ingredientOwned3 = new IngredientsOwned("ham");
		IngredientsOwned ingredientOwned4 = new IngredientsOwned("salt");

		List<IngredientsOwned> ingredientsOwnedlist = new ArrayList<>();
		ingredientsOwnedlist.add(ingredientOwned);
		ingredientsOwnedlist.add(ingredientOwned2);
		ingredientsOwnedlist.add(ingredientOwned3);
		ingredientsOwnedlist.add(ingredientOwned4);

		User user = usersService.getUser("Terry");
		user.setIngredientsOwned(ingredientsOwnedlist);

		// usersDao.update(user);

		User sixthRetrievedUser = usersService.getUser(user.getUsername());
		assertEquals(ingredientsOwnedlist.size(), sixthRetrievedUser.getIngredientsOwned().size());
	}

	@Test
	public void testAllergy() {
		List<Allergy> allergyList = new ArrayList<Allergy>();
		Allergy soyAllergy = new Allergy("soy");
		allergyList.add(soyAllergy);

		User user = usersService.getUser("Terry");
		user.setUsersAllergys(allergyList);

		//usersDao.update(user);

		User seventhRetrievedUser = usersService.getUser(user.getUsername());
		assertEquals(allergyList.size(), seventhRetrievedUser.getUsersAllergys().size());
	}
	
	

}
