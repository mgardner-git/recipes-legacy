package org.recipes.ingredients;

import java.io.Serializable;
import javax.persistence.*;

import org.recipes.recipeUsesIngredient.RecipeUsesIngredient;
import org.recipes.util.AutoComplete;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the ingredients database table.
 * 
 */
@Entity
@Table(name="ingredients")
@NamedQueries({
	@NamedQuery(name="Ingredient.search", query="SELECT i FROM Ingredient i WHERE i.title like :term"),
	@NamedQuery(name="Ingredient.searchExact", query="SELECT i FROM Ingredient i WHERE TRIM(UPPER(i.title))=TRIM(UPPER(:term))"),
	@NamedQuery(name="Ingredient.myIngredients", query="SELECT distinct i from Recipe r inner join r.recipeUsesIngredients rui inner join rui.ingredient i where r.user = :user")
})
public class Ingredient implements Serializable, AutoComplete {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to RecipeUsesIngredient
	@OneToMany(mappedBy="ingredient")
	@JsonIgnore
	private List<RecipeUsesIngredient> recipeUsesIngredients;

	public Ingredient() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		recipeUsesIngredient.setIngredient(this);

		return recipeUsesIngredient;
	}

	public RecipeUsesIngredient removeRecipeUsesIngredient(RecipeUsesIngredient recipeUsesIngredient) {
		getRecipeUsesIngredients().remove(recipeUsesIngredient);
		recipeUsesIngredient.setIngredient(null);

		return recipeUsesIngredient;
	}
	
	@JsonIgnore
	public String getLabel(){
		return this.title;
	}

}