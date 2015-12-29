package org.recipes.measurementTypes;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.recipes.recipeUsesIngredient.RecipeUsesIngredient;

@Generated(value="Dali", date="2015-12-15T19:37:36.503-0800")
@StaticMetamodel(MeasurementType.class)
public class MeasurementType_ {
	public static volatile SingularAttribute<MeasurementType, Integer> id;
	public static volatile SingularAttribute<MeasurementType, String> description;
	public static volatile SingularAttribute<MeasurementType, String> title;
	public static volatile ListAttribute<MeasurementType, RecipeUsesIngredient> recipeUsesIngredients;
}
