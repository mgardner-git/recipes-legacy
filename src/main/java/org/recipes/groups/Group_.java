package org.recipes.groups;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.recipes.membership.Membership;

@Generated(value="Dali", date="2016-03-12T10:26:52.972-0800")
@StaticMetamodel(Group.class)
public class Group_ {
	public static volatile SingularAttribute<Group, Integer> id;
	public static volatile SingularAttribute<Group, String> description;
	public static volatile SingularAttribute<Group, String> title;
	public static volatile ListAttribute<Group, Membership> memberships;
}
