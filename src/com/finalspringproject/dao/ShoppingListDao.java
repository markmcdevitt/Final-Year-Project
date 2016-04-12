package com.finalspringproject.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalspringproject.entity.User;
@Transactional
@Repository
@Component("shoppingListDao")
public class ShoppingListDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public User getShoppingList(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (User) crit.uniqueResult();

	}
}
