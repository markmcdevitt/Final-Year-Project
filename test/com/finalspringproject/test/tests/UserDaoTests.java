package com.finalspringproject.test.tests;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finalspringproject.dao.RecipeDAO;
import com.finalspringproject.dao.UsersDao;
import com.finalspringproject.entity.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/finalspringproject/config/dao-context.xml",
		"classpath:com/finalspringproject/config/security-context.xml",
		"classpath:com/finalspringproject/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

	private UsersDao usersDao;
	
	
	private RecipeDAO recipeDao;

	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Autowired
	public void setRecipeDao(RecipeDAO recipeDao) {
		this.recipeDao = recipeDao;
	}



	@Test
	public void testCreateUser() {
		User user =  new User("Terry","O2005@hotmail.com","letmein",true,"ROLE_USER",null,null,null,null,null,"Newbie",null);
	
		
		//assertTrue("User creation should return true", usersDao.createTest(user));
		recipeDao.saveOrUpdate(user);
		//assertEquals("The users ",6,userList.size());
		

		
//		List<Recipe> userRecipes = recipeDao.getRecipe(user.getUsername());
//		
//		assertEquals("UserRecipes should be six",6, userRecipes.size());
//		List<User> userList= usersDao.getAllUsers();
//		
//		assertEquals("Number of users should be 6 ",6,userList.size());
//		
//		assertTrue("User should exist ",usersDao.exists("mark"));
//		
//		assertEquals("Created user should be equal to the retrieved user ", user, userList.get(0));
	}
	
}
