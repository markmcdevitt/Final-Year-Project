package com.finalspringproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.finalspringproject.dao.UsersDao;
import com.finalspringproject.entity.User;


@Service("usersService")
public class UsersService {

	private UsersDao usersDao;

	@Autowired
	public void setRecipeDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public void create(User user) {
		usersDao.create(user);
	}
	public User getUser(String username) {
		return usersDao.getUser(username);
	}

	public boolean exists(String username) {
		return usersDao.exists(username);
	}

	
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public List<User> findChef(String search) {
		return usersDao.findChef(search);
	}

	public void update(User user) {
		usersDao.update(user);
		
	}

	public void deleteUser(User user) {
		usersDao.deleteUser(user);
		
	}
}
