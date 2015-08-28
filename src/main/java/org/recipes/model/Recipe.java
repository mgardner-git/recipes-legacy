package org.recipes.model;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.PrivateOwned;

import java.util.List;


/**
 * The persistent class for the recipes database table.
 * 
 */
@Entity
@Table(name="recipes")
@NamedQueries(
		{@NamedQuery(name="Recipe.findAll", query="SELECT r FROM Recipe r"),
		@NamedQuery(name="Recipe.myRecipes", query="SELECT r FROM Recipe r WHERE r.user=:owner"),
		@NamedQuery(name="Recipe.findById", query="SELECT r FROM Recipe r WHERE r.id=:id")})
public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String instructions;

	private String title;

	//This annotation ensures that when a recipe is saved, any rui's that belong to that recipe but are not present are deleted.
	@PrivateOwned
	//bi-directional many-to-one association to RecipeUsesIngredient
	@OneToMany(mappedBy="recipe", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)	
	private List<RecipeUsesIngredient> recipeUsesIngredients;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner_fk")
	private User user;

	public Recipe() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<RecipeUsesIngredient> getRecipeUsesIngredients() {
		return this.recipeUsesIngredients;
	}

	public void setRecipeUsesIngredients(List<RecipeUsesIngredient> recipeUsesIngredients) {
		this.recipeUsesIngredients = recipeUsesIngredients;
	}

	public RecipeUsesIngredient addRecipeUsesIngredient(RecipeUsesIngredient recipeUsesIngredient) {
		getRecipeUsesIngredients().add(recipeUsesIngredient);
		recipeUsesIngredient.setRecipe(this);

		return recipeUsesIngredient;
	}

	public RecipeUsesIngredient removeRecipeUsesIngredient(RecipeUsesIngredient recipeUsesIngredient) {
		getRecipeUsesIngredients().remove(recipeUsesIngredient);
		recipeUsesIngredient.setRecipe(null);

		return recipeUsesIngredient;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}