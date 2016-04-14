package com.finalspringproject.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalspringproject.entity.Allergy;

@Transactional
@Repository
@Component("allergyDao")
public class AllergyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public Allergy getAllergy(String allergy) {
		Criteria crit = session().createCriteria(Allergy.class);
		crit.add(Restrictions.eq("allergy", allergy));
		return (Allergy) crit.uniqueResult();
		
	}

}
