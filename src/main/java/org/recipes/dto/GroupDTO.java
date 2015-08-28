package org.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import org.recipes.model.*;

public class GroupDTO {

	private Integer id;
	private String admin;
	private String description;
	private String title;
	private List<ThreadDTO> threads = new ArrayList<ThreadDTO>();
	private List<MembershipDTO> membership = new ArrayList<MembershipDTO>();
	
	
	
	public GroupDTO(){}
	public GroupDTO(Group entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
		this.title = entity.getTitle();
		this.admin = entity.getAdmin().getId();
		
		for (Membership membershipEntity : entity.getMembers()) {
			MembershipDTO membershipForm = new MembershipDTO(membershipEntity);
			membership.add(membershipForm);
		}
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Group constructEntity(){
		Group groupEntity = new Group();
		groupEntity.setId(id);
		groupEntity.setTitle(title);
		User userEntity = new User();
		userEntity.setId(admin);
		groupEntity.setAdmin(userEntity);
		groupEntity.setDescription(description);
		return groupEntity;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public List<MembershipDTO> getMembership() {
		return membership;
	}
	public void setMembership(List<MembershipDTO> membership) {
		this.membership = membership;
	}
	public List<ThreadDTO> getThreads() {
		return threads;
	}
	public void setThreads(List<ThreadDTO> threads) {
		this.threads = threads;
	}
}
