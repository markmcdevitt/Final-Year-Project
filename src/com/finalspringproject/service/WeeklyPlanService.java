package com.finalspringproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finalspringproject.dao.User;
import com.finalspringproject.dao.WeeklyPlan;
import com.finalspringproject.dao.WeeklyPlanDao;

@Service("weeklyPlanService")
public class WeeklyPlanService {

	@Autowired
	private WeeklyPlanDao weeklyPlanDao;
	public WeeklyPlanService(){
		
	}
	public void deletePlan(int planId){
		 weeklyPlanDao.deletePlan(planId);
	}
	
	public WeeklyPlan getPlan(int planId){
		return weeklyPlanDao.getPlan(planId);
	}
	public User getUserWeeklyPlan(String username) {
		return weeklyPlanDao.getWeeklyPlan(username);
	}
	public void updateWeeklyPlan(WeeklyPlan weeklyPlan) {
		weeklyPlanDao.updateWeeklyPlan(weeklyPlan);
	}
}
