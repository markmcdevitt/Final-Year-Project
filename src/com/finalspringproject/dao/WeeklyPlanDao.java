package com.finalspringproject.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalspringproject.entity.User;
import com.finalspringproject.entity.WeeklyPlan;

@Transactional
@Repository
@Component("weeklyPlanDao")
public class WeeklyPlanDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public void deletePlan(int id) {
		Criteria crit = session().createCriteria(WeeklyPlan.class);
		crit.add(Restrictions.eq("id", id));
		WeeklyPlan wp=(WeeklyPlan) crit.uniqueResult();
		session().delete(wp);
	}
	public WeeklyPlan getPlan(int id) {
		Criteria crit = session().createCriteria(WeeklyPlan.class);
		crit.add(Restrictions.eq("id", id));
		return (WeeklyPlan) crit.uniqueResult();
	}

	public User getWeeklyPlan(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (User) crit.uniqueResult();
	}

	public void updateWeeklyPlan(WeeklyPlan weeklyPlan) {
		session().saveOrUpdate(weeklyPlan);
	}
}
