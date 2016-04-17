package com.finalspringproject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.finalspringproject.dao.FormValidationGroup;
import com.finalspringproject.dao.PersistenceValidationGroup;

@Entity
@Table(name = "user")
public class User {

	@Id
	@NotBlank(groups = { FormValidationGroup.class, PersistenceValidationGroup.class })
	@Size(min = 5, max = 20, groups = { FormValidationGroup.class, PersistenceValidationGroup.class })
	private String username;

	@NotBlank(groups = { FormValidationGroup.class, PersistenceValidationGroup.class })
	@Email(message = "This is an invalid email address", groups = { FormValidationGroup.class,
			PersistenceValidationGroup.class })
	private String email;

	@NotBlank(groups = { FormValidationGroup.class, PersistenceValidationGroup.class })
	@Size(min = 3, max = 15, groups = { FormValidationGroup.class })
	private String password;

	private boolean enabled = false;
	private String authority;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<IngredientsOwned> ingredientsOwned;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Recipe> recipes;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<WeeklyPlan> weeklyPlan;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ShoppingList> shoppingList;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Allergy> usersAllergys;

	@Column(nullable = true)
	private String userLevel;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Favorite> usersFavorites;

	public User() {

	}

	public User(String username, String email, String password, boolean enabled, String authority,
			List<IngredientsOwned> ingredientsOwned, List<Recipe> recipes, List<WeeklyPlan> weeklyPlan,
			List<ShoppingList> shoppingList, List<Allergy> usersAllergys, String userLevel,
			List<Favorite> usersFavorites) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.authority = authority;
		this.ingredientsOwned = ingredientsOwned;
		this.recipes = recipes;
		this.weeklyPlan = weeklyPlan;
		this.shoppingList = shoppingList;
		this.usersAllergys = usersAllergys;
		this.userLevel = userLevel;
		this.usersFavorites = usersFavorites;
	}

	public List<IngredientsOwned> getIngredientsOwned() {
		return ingredientsOwned;
	}

	public void setIngredientsOwned(List<IngredientsOwned> ingredientsOwned) {
		this.ingredientsOwned = ingredientsOwned;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<WeeklyPlan> getWeeklyPlan() {
		return weeklyPlan;
	}

	public void setWeeklyPlan(List<WeeklyPlan> weeklyPlan) {
		this.weeklyPlan = weeklyPlan;
	}

	public List<ShoppingList> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(List<ShoppingList> shoppingList) {
		this.shoppingList = shoppingList;
	}

	public List<Allergy> getUsersAllergys() {
		return usersAllergys;
	}

	public void setUsersAllergys(List<Allergy> usersAllergys) {
		this.usersAllergys = usersAllergys;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public List<Favorite> getUsersFavorites() {
		return usersFavorites;
	}

	public void setUsersFavorites(List<Favorite> usersFavorites) {
		this.usersFavorites = usersFavorites;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", password=" + password + ", enabled=" + enabled
				+ ", authority=" + authority + ", ingredientsOwned=" + ingredientsOwned + ", recipes=" + recipes
				+ ", weeklyPlan=" + weeklyPlan + ", shoppingList=" + shoppingList + ", usersAllergys=" + usersAllergys
				+ ", userLevel=" + userLevel + ", usersFavorites=" + usersFavorites + "]";
	}

}
