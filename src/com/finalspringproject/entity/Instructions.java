package com.finalspringproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Instructions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String steps;

	public Instructions() {

	}

	public Instructions(String steps) {
		super();
		this.steps = steps;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "Instructions [steps=" + steps + "]";
	}

	

}
