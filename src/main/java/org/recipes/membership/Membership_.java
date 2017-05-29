package org.recipes.membership;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.recipes.groups.Group;
import org.recipes.users.User;

@Generated(value="Dali", date="2016-03-18T10:34:42.173-0700")
@StaticMetamodel(Membership.class)
public class Membership_ {
	public static volatile SingularAttribute<Membership, Integer> id;
	public static volatile SingularAttribute<Membership, User> user;
	public static volatile SingularAttribute<Membership, Group> group;
}
