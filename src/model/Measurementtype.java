package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the measurementtypes database table.
 * 
 */
@Entity
@Table(name="measurementtypes")
@NamedQuery(name="Measurementtype.findAll", query="SELECT m FROM Measurementtype m")
public class Measurementtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to RecipeUsesIngredient
	@OneToMany(mappedBy="measurementtype")
	private List<RecipeUsesIngredient> recipeUsesIngredients;

	public Measurementtype() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
		recipeUsesIngredient.setMeasurementtype(this);

		return recipeUsesIngredient;
	}

	public RecipeUsesIngredient removeRecipeUsesIngredient(RecipeUsesIngredient recipeUsesIngredient) {
		getRecipeUsesIngredients().remove(recipeUsesIngredient);
		recipeUsesIngredient.setMeasurementtype(null);

		return recipeUsesIngredient;
	}

}