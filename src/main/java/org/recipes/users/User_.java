package org.recipes.users;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.recipes.recipes.Recipe;

@Generated(value="Dali", date="2015-12-04T21:43:32.175-0800")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> id;
	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Recipe> recipes;
}
