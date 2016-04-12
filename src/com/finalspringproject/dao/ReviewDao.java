package com.finalspringproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalspringproject.entity.Review;
import com.finalspringproject.entity.User;

@Transactional
@Repository
@Component("reviewDao")
public class ReviewDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();

	}

	@SuppressWarnings("unchecked")
	public List<Review> getReview(User user) {
		Criteria crit = session().createCriteria(Review.class);
		crit.add(Restrictions.eq("user", user));
		return crit.list();
	}

	public void deleteReview(Review review) {
		session().delete(review);
	}
}
