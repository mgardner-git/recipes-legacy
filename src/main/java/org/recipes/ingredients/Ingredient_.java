package org.recipes.ingredients;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RecipeUsesIngredient;

@Generated(value="Dali", date="2015-12-06T13:51:35.451-0800")
@StaticMetamodel(Ingredient.class)
public class Ingredient_ {
	public static volatile SingularAttribute<Ingredient, Integer> id;
	public static volatile SingularAttribute<Ingredient, String> description;
	public static volatile SingularAttribute<Ingredient, String> title;
	public static volatile ListAttribute<Ingredient, RecipeUsesIngredient> recipeUsesIngredients;
}
