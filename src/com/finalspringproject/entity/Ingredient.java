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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ingredientAmount == null) ? 0 : ingredientAmount.hashCode());
		result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (id != other.id)
			return false;
		if (ingredientAmount == null) {
			if (other.ingredientAmount != null)
				return false;
		} else if (!ingredientAmount.equals(other.ingredientAmount))
			return false;
		if (ingredientName == null) {
			if (other.ingredientName != null)
				return false;
		} else if (!ingredientName.equals(other.ingredientName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", ingredientName=" + ingredientName + ", ingredientAmount=" + ingredientAmount
				+ "]";
	}
	
	

}
