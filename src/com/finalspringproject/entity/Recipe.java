package com.finalspringproject.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.dao.PersistenceValidationGroup;

@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Size(min = 5, max = 100)
	private String titleParse;
	@Size(min = 5, max = 200)
	private String descriptionParse;
	@NotBlank
	private String imageURLParse;
	private String totalRating;
	@NumberFormat(style = Style.NUMBER)
	private String peopleFed;
	private String calories;
	private String level;
	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;

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
			String level, Category category, List<Instructions> instructions, List<Ingredient> ingredients) {
		super();
		this.titleParse = titleParse;
		this.descriptionParse = descriptionParse;
		this.imageURLParse = imageURLParse;
		this.peopleFed = peopleFed;
		this.calories = calories;
		this.instructions = instructions;
		this.ingredients = ingredients;
		this.level = level;
		this.category = category;
	}

	public Recipe(String titleParse, String level, String descriptionParse, String imageURLParse, String peopleFed,
			String calories, Category category, List<Instructions> instructions, List<Ingredient> ingredients,
			String totalRating) {
		super();
		this.level = level;
		this.category = category;
		this.titleParse = titleParse;
		this.descriptionParse = descriptionParse;
		this.imageURLParse = imageURLParse;
		this.peopleFed = peopleFed;
		this.calories = calories;
		this.instructions = instructions;
		this.ingredients = ingredients;
		this.totalRating = totalRating;
	}

	public Recipe(String titleParse, String descriptionParse, String imageURLParse, String totalRating,
			String peopleFed, String calories, String level, Category category, List<Instructions> instructions,
			List<Ingredient> ingredients, List<Review> review) {
		super();
		this.titleParse = titleParse;
		this.descriptionParse = descriptionParse;
		this.imageURLParse = imageURLParse;
		this.totalRating = totalRating;
		this.peopleFed = peopleFed;
		this.calories = calories;
		this.level = level;
		this.category = category;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", titleParse=" + titleParse + ", descriptionParse=" + descriptionParse
				+ ", imageURLParse=" + imageURLParse + ", totalRating=" + totalRating + ", peopleFed=" + peopleFed
				+ ", calories=" + calories + ", level=" + level + ", category=" + category + ", instructions="
				+ instructions + ", ingredients=" + ingredients + ", review=" + review + "]";
	}

}
