package com.finalspringproject.test.tests;


	import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finalspringproject.controllers.RecipeController;
import com.finalspringproject.entity.Ingredient;
import com.finalspringproject.service.RecipeService;
import com.finalspringproject.service.UsersService;

	@ActiveProfiles("dev")
	@ContextConfiguration(locations = { "classpath:com/finalspringproject/config/dao-context.xml",
			"classpath:com/finalspringproject/config/security-context.xml",
			"classpath:com/finalspringproject/test/config/datasource.xml",
			"classpath:com/finalspringproject/config/service-context.xml" })
	@RunWith(SpringJUnit4ClassRunner.class)
	public class RecipeTests {
		@Autowired
		private UsersService usersService;
		@Autowired
		private RecipeService recipeService;
		
		private RecipeController recipeController = new RecipeController();
		

		@Test
		public void testRecipe() {
			double amount = 0.25;
			String fraction = recipeController.ingredientAmount(amount);
			String correctFraction = "1/4";
		
			assertEquals(correctFraction, fraction);
		}
		
		
		@Test
		public void testNoFractionTeaspoon() {
			double amount =3;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.noFraction(amount, name);
			Ingredient correctIngredient = new Ingredient("tablespoon of salt","1");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		}
		
		@Test
		public void testNoFractionManyTablespoon() {
			
		
		}
}
