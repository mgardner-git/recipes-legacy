package org.recipes.dto;

import java.util.Date;

import org.recipes.model.Post;
import org.recipes.model.Thread;
/**
 * Contains stuff from the Thread object and some summary stuff to post on the threads page.
 * @author monte.gardner
 *
 */
public class ThreadDTO {

	private int id;
	private String title;
	private Date mostRecentPost;
	private Integer numPosts;
	private GroupDTO group;

	public ThreadDTO(){}
	public ThreadDTO(Thread entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		if (entity.getGroup() != null) {
			this.group = new GroupDTO(entity.getGroup());
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	public Date getMostRecentPost() {
		return mostRecentPost;
	}
	public void setMostRecentPost(Date mostRecentPost) {
		this.mostRecentPost = mostRecentPost;
	}
	public Integer getNumPosts() {
		return numPosts;
	}
	public void setNumPosts(Integer numThreads) {
		this.numPosts = numThreads;
	}
	public GroupDTO getGroup() {
		return group;
	}
	public void setGroup(GroupDTO group) {
		this.group = group;
	}
	
}
