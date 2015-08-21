package org.recipes.dto;

import org.recipes.model.Ingredient;
import org.recipes.model.Recipe;
import org.recipes.model.RecipeUsesIngredient;

public class RecipeUsesIngredientDTO {

	private int id;
	private int quantity;
	private RecipeDTO recipe;
	private IngredientDTO ingredient;
	private MeasurementtypeDTO measurementtype;
	
	public RecipeUsesIngredientDTO(){}
	public RecipeUsesIngredientDTO(RecipeUsesIngredient entity){ this(entity,false);}
	public RecipeUsesIngredientDTO(RecipeUsesIngredient entity, boolean deep){
		this.id = entity.getId();
		this.quantity = entity.getQuantity();
		if (deep){
			if (entity.getRecipe() != null){
				this.recipe = new RecipeDTO(entity.getRecipe(),deep);
			}
			if (entity.getIngredient() != null){
				this.ingredient = new IngredientDTO(entity.getIngredient());
			}
			if (entity.getMeasurementtype() != null){
				this.measurementtype = new MeasurementtypeDTO(entity.getMeasurementtype());
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the recipe
	 */
	public RecipeDTO getRecipe() {
		return recipe;
	}

	/**
	 * @param recipe the recipe to set
	 */
	public void setRecipe(RecipeDTO recipe) {
		this.recipe = recipe;
	}

	/**
	 * @return the ingredient
	 */
	public IngredientDTO getIngredient() {
		return ingredient;
	}

	/**
	 * @param ingredient the ingredient to set
	 */
	public void setIngredient(IngredientDTO ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * @return the measurementtype
	 */
	public MeasurementtypeDTO getMeasurementtype() {
		return measurementtype;
	}

	/**
	 * @param measurementtype the measurementtype to set
	 */
	public void setMeasurementtype(MeasurementtypeDTO measurementtype) {
		this.measurementtype = measurementtype;
	}
	
	public RecipeUsesIngredient constructEntity(Recipe recipe){
		RecipeUsesIngredient ruiEntity = new RecipeUsesIngredient();
		ruiEntity.setId(id);
		ruiEntity.setQuantity(quantity);
		Ingredient ingredientEntity = ingredient.constructEntity();
		ruiEntity.setIngredient(ingredientEntity);
		ruiEntity.setMeasurementtype(this.measurementtype.constructEntity());
		ruiEntity.setRecipe(recipe);
		
		return ruiEntity;
	}
}
