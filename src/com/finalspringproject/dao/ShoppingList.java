package com.finalspringproject.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String quantity;
	private String ingredient;

	public ShoppingList(String quantity, String ingredient) {
		super();
		this.quantity = quantity;
		this.ingredient = ingredient;
	}

	public ShoppingList() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public String toString() {
		return "ShoppingList [id=" + id + ", quantity=" + quantity + ", ingredient=" + ingredient + "]";
	}

}