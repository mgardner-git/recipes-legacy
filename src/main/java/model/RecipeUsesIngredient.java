package model;

import java.io.Serializable;
import javax.persistence.*;

import org.recipes.ingredients.Ingredient;
import org.recipes.measurementTypes.MeasurementType;
import org.recipes.recipes.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the recipe_uses_ingredient database table.
 * 
 */
@Entity
@Table(name="recipe_uses_ingredient")
@NamedQuery(name="RecipeUsesIngredient.findAll", query="SELECT r FROM RecipeUsesIngredient r")
public class RecipeUsesIngredient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int quantity;

	//bi-directional many-to-one association to Ingredient
	@ManyToOne
	@JoinColumn(name="ingredient_fk")
	private Ingredient ingredient;

	//bi-directional many-to-one association to Measurementtype
	@ManyToOne
	@JoinColumn(name="measurementType_fk")
	private MeasurementType measurementType;

	//bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name="recipe_fk")
	@JsonIgnore
	private Recipe recipe;

	public RecipeUsesIngredient() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public MeasurementType getMeasurementtype() {
		return this.measurementType;
	}

	public void setMeasurementtype(MeasurementType measurementtype) {
		this.measurementType = measurementtype;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}