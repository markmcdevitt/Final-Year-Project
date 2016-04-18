package com.finalspringproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.finalspringproject.dao.RecipeDAO;
import com.finalspringproject.entity.IngredientsOwned;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.Review;
import com.finalspringproject.entity.User;
import com.finalspringproject.entity.WeeklyPlan;

@Service("recipeService")
public class RecipeService {

	private RecipeDAO recipeDao;

	public RecipeService() {
	}

	public List<Recipe> getCurrent() {
		return recipeDao.getRecipe();

	}

	public List<Recipe> getGeneratedRecipe(String ingredient) throws InterruptedException {
		return recipeDao.getGeneratedRecipe(ingredient);
	}

	public Recipe getCurrentRecipe(String name) {
		return recipeDao.getRecipe2(name);
	}

	public List<Recipe> getCurrent(String title) {
		return recipeDao.getRecipe(title);
	}

	@Autowired
	public void setRecipeDao(RecipeDAO recipeDao) {
		this.recipeDao = recipeDao;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public void create(User user) {
		recipeDao.saveOrUpdate(user);
	}

	public boolean hadRecipe(String name) {
		if (name == null) {
			return false;
		}
		List<Recipe> recipes = recipeDao.getRecipe(name);
		if (recipes.size() == 0) {
			return false;
		}
		return true;
	}

	public Recipe getRecipe(String username) {

		if (username == null) {
			return null;
		}

		List<Recipe> recipes = recipeDao.getRecipe(username);

		if (recipes.size() == 0) {
			return null;
		}
		return recipes.get(0);
	}

	public void saveOrUpdate(User user) {

		recipeDao.saveOrUpdate(user);

	}

	public void saveOrUpdate(Recipe recipe) {

		recipeDao.saveOrUpdate(recipe);

	}

	public void update(Recipe recipe) {

		recipeDao.update(recipe);

	}

	public void delete(int id) {

		recipeDao.delete(id);

	}

	public void merge(User user) {
		recipeDao.merge(user);

	}

	public List<Recipe> find(String search) {
		return recipeDao.find(search);
	}

	public List<Recipe> getGeneratedRecipeSingleWord(String ingredient) throws InterruptedException {
		return recipeDao.getGeneratedRecipeSingleWord(ingredient);
	}

	public Recipe getOneRecipe(int id) {
		return recipeDao.getRecipe(id);
	}

	public List<Recipe> getSpecific(String category) {
		return recipeDao.getSpecific(category);
	}
}
