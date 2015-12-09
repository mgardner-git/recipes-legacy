package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.recipes.ingredients.Ingredient;
import org.recipes.measurementTypes.MeasurementType;
import org.recipes.recipes.Recipe;

@Generated(value="Dali", date="2015-12-08T19:08:48.431-0800")
@StaticMetamodel(RecipeUsesIngredient.class)
public class RecipeUsesIngredient_ {
	public static volatile SingularAttribute<RecipeUsesIngredient, Integer> id;
	public static volatile SingularAttribute<RecipeUsesIngredient, Integer> quantity;
	public static volatile SingularAttribute<RecipeUsesIngredient, Ingredient> ingredient;
	public static volatile SingularAttribute<RecipeUsesIngredient, MeasurementType> measurementType;
	public static volatile SingularAttribute<RecipeUsesIngredient, Recipe> recipe;
}
