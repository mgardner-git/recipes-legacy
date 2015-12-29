package org.recipes.measurementTypes;

import java.io.Serializable;
import javax.persistence.*;

import org.recipes.recipeUsesIngredient.RecipeUsesIngredient;
import org.recipes.util.AutoComplete;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the measurementtypes database table.
 * 
 */
@Entity
@Table(name="measurement_types")
@NamedQueries({
	@NamedQuery(name="MeasurementType.findAll", query="SELECT m FROM MeasurementType m"),
	@NamedQuery(name="MeasurementType.searchExact", query="SELECT m FROM MeasurementType m WHERE TRIM(UPPER(m.title))=TRIM(UPPER(:term))")
})
public class MeasurementType implements Serializable,AutoComplete {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to RecipeUsesIngredient
	@OneToMany(mappedBy="measurementType")
	@JsonIgnore
	private List<RecipeUsesIngredient> recipeUsesIngredients;

	public MeasurementType() {
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
		recipeUsesIngredient.setMeasurementType(this);

		return recipeUsesIngredient;
	}

	public RecipeUsesIngredient removeRecipeUsesIngredient(RecipeUsesIngredient recipeUsesIngredient) {
		getRecipeUsesIngredients().remove(recipeUsesIngredient);
		recipeUsesIngredient.setMeasurementType(null);

		return recipeUsesIngredient;
	}
	
	@JsonIgnore
	public String getLabel(){
		return this.title;
	}

}