package com.finalspringproject.dao;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalspringproject.entity.IngredientsOwned;
import com.finalspringproject.entity.Recipe;
import com.finalspringproject.entity.User;

@Transactional
@Repository
@Component("recipeDao")
public class RecipeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();

	}

	@SuppressWarnings("unchecked")
	public List<Recipe> getRecipe() {
		Criteria crit = session().createCriteria(Recipe.class);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	public Recipe getRecipe2(String titleParse) {
		Criteria crit = session().createCriteria(Recipe.class);
		crit.add(Restrictions.eq("titleParse", titleParse));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Recipe) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Recipe> getRecipe(String title) {
		Criteria crit = session().createCriteria(Recipe.class);
		crit.add(Restrictions.eq("titleParse", title));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}
	
	
	public void saveOrUpdate(User user) {
		session().saveOrUpdate(user);
	}

	public void saveOrUpdate(Recipe recipe) {
		
		session().saveOrUpdate(recipe);
		

	}

	public boolean delete(int id) {
		Query query = session().createQuery("delete from Recipe where id=:id");
		query.setLong("id", id);
		return query.executeUpdate() == 1;
	}

	public Recipe getRecipe(int id) {
		Criteria crit = session().createCriteria(Recipe.class);
		crit.add(Restrictions.idEq(id));
		return (Recipe) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Recipe> getGeneratedRecipe(String ingredient) throws InterruptedException {
		Criteria criteria = session().createCriteria(Recipe.class, "recipe");
		criteria.createAlias("recipe.ingredients", "ing");
		criteria.add(Restrictions.like("ing.ingredientName", ingredient, MatchMode.ANYWHERE));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public void update(User user) {
		session().update(user);
	}

	public void merge(User user) {
		session().merge(user);

	}

	public List<Recipe> find(String search) {
		Criteria criteria = session().createCriteria(Recipe.class, "recipe");
		criteria.add(Restrictions.like("recipe.titleParse", search, MatchMode.ANYWHERE));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public void update(Recipe recipe) {
		session().update(recipe);

	}

	public List<Recipe> getGeneratedRecipeSingleWord(String ingredient) {
		Criteria criteria = session().createCriteria(Recipe.class, "recipe");
		criteria.createAlias("recipe.ingredients", "ing");
		criteria.add(Restrictions.like("ing.ingredientName", ingredient, MatchMode.EXACT));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List<Recipe> getSpecific(String category) {
		Criteria criteria = session().createCriteria(Recipe.class, "recipe");
		criteria.createAlias("recipe.category", "cat");
		criteria.add(Restrictions.like("cat.category", category, MatchMode.EXACT));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public void create(Recipe recipe) {
		session().save(recipe);
	}
}
