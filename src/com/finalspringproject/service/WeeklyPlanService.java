package com.finalspringproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalspringproject.dao.WeeklyPlanDao;
import com.finalspringproject.entity.User;
import com.finalspringproject.entity.WeeklyPlan;

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
