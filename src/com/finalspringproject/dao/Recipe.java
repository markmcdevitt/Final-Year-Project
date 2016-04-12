package com.finalspringproject.dao;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String titleParse;
	private String descriptionParse;
	private String imageURLParse;
	private String totalRating;
	private String peopleFed;
	private String calories;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Instructions> instructions;// the list of steps

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Ingredient> ingredients;// list of ingredients

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Review> review;

	public Recipe() {

	}

	public Recipe(String titleParse, String descriptionParse, String imageURLParse, String peopleFed, String calories,
			List<Instructions> instructions, List<Ingredient> ingredients) {
		super();
		this.titleParse = titleParse;
		this.descriptionParse = descriptionParse;
		this.imageURLParse = imageURLParse;
		this.peopleFed = peopleFed;
		this.calories = calories;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

	public Recipe(String titleParse, String descriptionParse, String imageURLParse, String totalRating,
			String peopleFed, String calories, List<Instructions> instructions, List<Ingredient> ingredients,
			List<Review> review) {
		super();
		this.titleParse = titleParse;
		this.descriptionParse = descriptionParse;
		this.imageURLParse = imageURLParse;
		this.totalRating = totalRating;
		this.peopleFed = peopleFed;
		this.calories = calories;
		this.instructions = instructions;
		this.ingredients = ingredients;
		this.review = review;
	}

	public String getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(String totalRating) {
		this.totalRating = totalRating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitleParse() {
		return titleParse;
	}

	public void setTitleParse(String titleParse) {
		this.titleParse = titleParse;
	}

	public String getDescriptionParse() {
		return descriptionParse;
	}

	public void setDescriptionParse(String descriptionParse) {
		this.descriptionParse = descriptionParse;
	}

	public String getImageURLParse() {
		return imageURLParse;
	}

	public void setImageURLParse(String imageURLParse) {
		this.imageURLParse = imageURLParse;
	}

	public List<Instructions> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instructions> instructions) {
		this.instructions = instructions;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public String getPeopleFed() {
		return peopleFed;
	}

	public void setPeopleFed(String peopleFed) {
		this.peopleFed = peopleFed;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "Recipe [titleParse=" + titleParse + ", descriptionParse=" + descriptionParse + ", imageURLParse="
				+ imageURLParse + ", totalRating=" + totalRating + ", peopleFed=" + peopleFed + ", calories=" + calories
				+ ", instructions=" + instructions + ", ingredients=" + ingredients + ", review=" + review + "]";
	}

}
