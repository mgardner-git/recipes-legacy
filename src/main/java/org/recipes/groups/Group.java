package org.recipes.groups;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.recipes.membership.Membership;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name="groups")
@NamedQueries({
	@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g"),
	@NamedQuery(name="Group.myGroups", query="SELECT g from Group g inner join g.memberships m WHERE m.user = :user"),
	@NamedQuery(name="Group.search", query="SELECT g from Group g WHERE g.title like :term")
})
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to Membership
	@OneToMany(mappedBy="group")
	@JsonIgnore
	private List<Membership> memberships;

	public Group() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Membership> getMemberships() {
		return this.memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public Membership addMembership(Membership membership) {
		getMemberships().add(membership);
		membership.setGroup(this);

		return membership;
	}

	public Membership removeMembership(Membership membership) {
		getMemberships().remove(membership);
		membership.setGroup(null);

		return membership;
	}

}