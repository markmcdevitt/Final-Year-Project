package com.finalspringproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Allergy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String allergy;

	public Allergy() {
	}

	public Allergy(String allergy) {
		this.allergy = allergy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

	@Override
	public String toString() {
		return "Allergy [id=" + id + ", allergy=" + allergy + "]";
	}

}
