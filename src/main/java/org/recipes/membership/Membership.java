package org.recipes.membership;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.recipes.groups.Group;
import org.recipes.users.User;


/**
 * The persistent class for the membership database table.
 * 
 */
@Entity
@Table(name="membership")
@NamedQuery(name="Membership.findAll", query="SELECT m FROM Membership m")
public class Membership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_fk")
	private Group group;

	public Membership() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}