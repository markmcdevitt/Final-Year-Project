package com.finalspringproject.dao;

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
//This annotation tells hibernate search that this class has to be indexed
@Indexed
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

	 // This annotation tells that this field has to be indexed and also analyzed (break the long sentence and ignore common words), store tells if this field
    // will be part of Index, by Store.Yes it means it will be part of Index, so that query will be faster, downside is that size of Index increases
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
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
