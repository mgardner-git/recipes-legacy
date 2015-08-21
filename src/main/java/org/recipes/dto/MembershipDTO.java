package org.recipes.dto;

import java.util.Date;

import org.recipes.model.Membership;
import org.recipes.model.User;

public class MembershipDTO {

	private Integer id;
	private Date joinDate;
	private UserDTO user;
	private GroupDTO group;
	
	public MembershipDTO() {}
	public MembershipDTO(Membership entity){
		this.id = entity.getId();
		this.user = new UserDTO(entity.getUser());		
		this.joinDate = entity.getJoinDate();

	}

	public Membership constructEntity() {
		Membership entity = new Membership();
		entity.setId(id);
		entity.setJoinDate(joinDate);
		User userEntity = user.constructUserEntity();
		entity.setUser(userEntity);
		entity.setGroup(group.constructEntity());
		return entity;
		
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public GroupDTO getGroup() {
		return group;
	}

	public void setGroup(GroupDTO group) {
		this.group = group;
	}
}
