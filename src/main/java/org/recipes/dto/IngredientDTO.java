package org.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.recipes.model.Ingredient;

import org.recipes.autocomplete.AutoComplete;

public class IngredientDTO implements AutoComplete{

	private Integer id;
	private String description;
	private String title;
	private List<RecipeUsesIngredientDTO> recipeUsesIngredients = new ArrayList<RecipeUsesIngredientDTO>();
	private List<PostDTO> posts = new ArrayList<PostDTO>();
	
	
	public IngredientDTO(){}
	public IngredientDTO(Ingredient entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
		this.title = entity.getTitle();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public String getLabel() {return title;}
	@JsonIgnore
	public String getValue() {return String.valueOf(id);}
	
	public Ingredient constructEntity(){
		Ingredient ingredientEntity = new Ingredient();
		ingredientEntity.setId(id);
		ingredientEntity.setTitle(title);
		ingredientEntity.setDescription(description);
		return ingredientEntity;
	}
	public List<RecipeUsesIngredientDTO> getRecipeUsesIngredients() {
		return recipeUsesIngredients;
	}
	public void setRecipeUsesIngredients(
			List<RecipeUsesIngredientDTO> recipeUsesIngredients) {
		this.recipeUsesIngredients = recipeUsesIngredients;
	}
	public List<PostDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<PostDTO> posts) {
		this.posts = posts;
	}
}
