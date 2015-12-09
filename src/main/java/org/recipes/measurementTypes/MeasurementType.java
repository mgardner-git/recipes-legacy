package org.recipes.measurementTypes;

import java.io.Serializable;
import javax.persistence.*;

import org.recipes.util.AutoComplete;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.RecipeUsesIngredient;

import java.util.List;


/**
 * The persistent class for the measurementtypes database table.
 * 
 */
@Entity
@Table(name="measurementtypes")
@NamedQuery(name="MeasurementType.findAll", query="SELECT m FROM MeasurementType m")
public class MeasurementType implements Serializable,AutoComplete {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to RecipeUsesIngredient
	@OneToMany(mappedBy="measurementType")
	@JsonIgnore
	private List<RecipeUsesIngredient> recipeUsesIngredients;

	public MeasurementType() {
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
	
	@JsonIgnore
	public String getLabel(){
		return this.title;
	}

}