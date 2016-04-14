package com.finalspringproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalspringproject.dao.AllergyDAO;
import com.finalspringproject.entity.Allergy;

@Service("allergyService")
public class AllergyService {

	private AllergyDAO allergyDao;
	
	@Autowired
	public void setAllergyDao(AllergyDAO allergyDao) {
		this.allergyDao = allergyDao;
	}

	public AllergyService() {
	}
	
	public Allergy getAllergy(String allergy){
		return allergyDao.getAllergy(allergy);
	}
}
