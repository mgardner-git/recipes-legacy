package org.recipes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name="groups")
@NamedQueries({
	@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g"),
	@NamedQuery(name="Group.findById", query="SELECT g FROM Group g WHERE g.id=:id")
})

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "admin", insertable = true, updatable = false)
	private User admin;

	private String description;

	private String title;
	
	@OneToMany(mappedBy="group")
	private List<Membership> members;
	
	@OneToMany(mappedBy="group")
	private List<Thread> threads;
	

	public Group() {
		members = new ArrayList<Membership>();
		threads = new ArrayList<Thread>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAdmin() {
		return this.admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
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

	public List<Membership> getMembers() {
		return members;
	}

	public void setMembers(List<Membership> members) {
		this.members = members;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

}