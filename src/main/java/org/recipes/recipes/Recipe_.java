package org.recipes.recipes;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RecipeUsesIngredient;
import org.recipes.users.User;

@Generated(value="Dali", date="2015-12-04T21:43:31.956-0800")
@StaticMetamodel(Recipe.class)
public class Recipe_ {
	public static volatile SingularAttribute<Recipe, Integer> id;
	public static volatile SingularAttribute<Recipe, String> instructions;
	public static volatile SingularAttribute<Recipe, String> title;
	public static volatile ListAttribute<Recipe, RecipeUsesIngredient> recipeUsesIngredients;
	public static volatile SingularAttribute<Recipe, User> user;
}
