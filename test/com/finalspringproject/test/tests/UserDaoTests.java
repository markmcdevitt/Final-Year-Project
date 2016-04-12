package com.finalspringproject.test.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.finalspringproject.dao.Recipe;
import com.finalspringproject.dao.RecipeDAO;
import com.finalspringproject.dao.User;
import com.finalspringproject.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/finalspringproject/config/dao-context.xml",
		"classpath:com/finalspringproject/config/security-context.xml",
		"classpath:com/finalspringproject/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RecipeDAO recipeDao;

	@Autowired
	private DataSource dataSource;

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		
		//jdbc.execute("delete from users");

	}

	@Test
	public void testCreateUser() {
		User user =  new User("Terry","O2005@hotmail.com","letmein",null,true,"ROLE_USER",null,null);
	
		//assertTrue("User creation should return true", usersDao.create(user));
		
//		s
		
		List<Recipe> userRecipes = recipeDao.getRecipe(user.getUsername());
		
		assertEquals("UserRecipes should be two",6, userRecipes.size());
//		List<User> userList= usersDao.getAllUsers();
//		
//		assertEquals("Number of users should be 6 ",6,userList.size());
//		
//		assertTrue("User should exist ",usersDao.exists("mark"));
		
		//assertEquals("Created user should be equal to the retrieved user ", user, userList.get(0));
	}
	
}
