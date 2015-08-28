package org.recipes.dto;

import java.util.ArrayList;
import java.util.List;

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
	private GroupDTO group;
	private List<PostDTO> posts;

	public ThreadDTO(){}
	public ThreadDTO(Thread entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		if (entity.getGroup() != null) {
			this.group = new GroupDTO(entity.getGroup());
		}
		posts = new ArrayList<PostDTO>();
		for (Post postEntity : entity.getPosts()) {
			posts.add(new PostDTO(postEntity));
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

	public GroupDTO getGroup() {
		return group;
	}
	public void setGroup(GroupDTO group) {
		this.group = group;
	}
	public List<PostDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<PostDTO> posts) {
		this.posts = posts;
	}
	
}
