package org.recipes.dto;
/**
 * 
 * Contains information about each group, whether the current user is a member of that group, and whether the selected ingredient 
 * has a thread linked to it in that group.
 *
 */
public class GroupsAndIngredientsDTO {

	GroupDTO group;
	boolean isMember;
	boolean hasThread;
	
	public GroupsAndIngredientsDTO() {
		
	}

	public GroupDTO getGroup() {
		return group;
	}

	public void setGroup(GroupDTO group) {
		this.group = group;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public boolean isHasThread() {
		return hasThread;
	}

	public void setHasThread(boolean hasThread) {
		this.hasThread = hasThread;
	}
	
}
