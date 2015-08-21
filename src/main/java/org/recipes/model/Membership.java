package org.recipes.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_member_of_group database table.
 * 
 */
@Entity
@Table(name="membership")
@NamedQueries({
	@NamedQuery(name="Membership.findAll", query="SELECT m FROM Membership m"),
	@NamedQuery(name="Membership.verifyMembership", query ="SELECT m FROM Membership m WHERE m.userId=:userId AND m.groupId=:groupId")
})
public class Membership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="join_date")
	private Date joinDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_fk")
	private Group group;
	
	@Column(name="group_fk", insertable=false, updatable=false)
	private Integer groupId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_fk")
	private User user;

	@Column(name="user_fk", insertable=false, updatable=false)
	private String userId;
	

	public Membership() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}


}