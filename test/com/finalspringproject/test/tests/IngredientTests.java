package com.finalspringproject.test.tests;


	import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finalspringproject.controllers.IngredientController;
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
	public class IngredientTests {
		
		private RecipeController recipeController = new RecipeController();
		private IngredientController ingredientController = new IngredientController();
		
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
			double amount =8;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.noFraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","2 tablespoons and 2");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		}
		
		@Test
		public void testNoFractionNoTablespoon() {
			double amount =2;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.noFraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","2");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void fraction() {
			double amount =0.1666;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.fraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","1/6");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void wholeFraction() {
			double amount =10.5;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.fraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","3 tablespoons and 1-1/2");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void wholeFraction2() {
			double amount =9.5;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.fraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","3 tablespoons and 1/2");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void removeFraction() {
			double amount =2;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.fraction(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","2");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void checkForZero() {
			double amount =0;
			String name = "teaspoon of salt";
			
			Ingredient ingredient = recipeController.ingredientAmount(amount, name);
			Ingredient correctIngredient = new Ingredient("teaspoon of salt","-");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void wholeNumber() {
			double amount =12;
			String name = "teaspoons of salt";
			
			Ingredient ingredient2 = new Ingredient(name,"12");
			
			Ingredient ingredient = ingredientController.wholeNumber(amount,2,4, ingredient2);
			Ingredient correctIngredient = new Ingredient("tablespoons of salt","8");
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void wholeNumber2() {
			double amount =12;
			String name = "teaspoons of salt";
			
			Ingredient ingredient2 = new Ingredient(name,"12");
			
			Ingredient ingredient = ingredientController.wholeNumber(amount,3,4, ingredient2);
			Ingredient correctIngredient = new Ingredient("teaspoons of salt","5 tablespoons and 1");;
			
			assertEquals(correctIngredient.getIngredientAmount(), ingredient.getIngredientAmount());
			assertEquals(correctIngredient.getIngredientName(), ingredient.getIngredientName());
		
		}
		
		@Test
		public void roundUp() {
			double answer = 3.0;
			double roundedNumber= ingredientController.round(2.5,0);
			boolean match = false;
			System.out.println(roundedNumber); 
			
			if(answer == roundedNumber){
				match =true;
			}
		
			assertEquals(true, match);
		}
}
