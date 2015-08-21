package org.recipes.dto;

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonSetter;

/**
 * 
 * Contains information about each group, whether the current user is a member of that group, and, if there 
 * is a thread linked to the selected recipe, the Id of that thread.
 *
 */
public class GroupsAndRecipesDTO {

	GroupDTO group;
	boolean isMember;
	Integer threadId; 
	
	public GroupsAndRecipesDTO() {
		group = new GroupDTO();
	}

	public GroupDTO getGroup() {
		return group;
	}

	public void setGroup(GroupDTO group) {
		this.group = group;
	}

	@JsonGetter(value="isMember")
	public boolean isMember() {
		return isMember;
	}

	@JsonSetter(value="isMember")
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public Integer getThreadId() {
		return threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}


	

	
}
