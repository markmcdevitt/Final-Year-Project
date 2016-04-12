package com.finalspringproject.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@org.hibernate.annotations.Entity(
		dynamicUpdate = true
)
public class WeeklyPlan  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String date;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Recipe> recipe;

	public WeeklyPlan(String date, List<Recipe> recipe) {
		this.date = date;
		this.recipe = recipe;
	}

	public WeeklyPlan() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Recipe> getRecipe() {
		return recipe;
	}

	public void setRecipe(List<Recipe> recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "WeeklyPlan [id=" + id + ", date=" + date + ", recipe=" + recipe + "]";
	}

}
