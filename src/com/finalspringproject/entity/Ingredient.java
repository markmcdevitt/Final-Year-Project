package com.finalspringproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
public class Ingredient {

	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String ingredientName;

	private String ingredientAmount;

	public Ingredient() {
		
	}

	public Ingredient(String ingredientName, String ingredientAmount) {
		super();
		this.ingredientName = ingredientName;
		this.ingredientAmount = ingredientAmount;
	}

   public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getIngredientAmount() {
		return ingredientAmount;
	}

	public void setIngredientAmount(String ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", ingredientName=" + ingredientName + ", ingredientAmount=" + ingredientAmount
				+ "]";
	}
	
	

}
