package com.finalspringproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finalspringproject.dao.ShoppingListDao;
import com.finalspringproject.entity.User;

@Service("shoppingListService")
public class ShoppingListService {

	@Autowired
	private ShoppingListDao shoppingListDao;
	public ShoppingListService(){
		
	}
	public User getUserShoppingList(String username) {
		return shoppingListDao.getShoppingList(username);
	}
}
