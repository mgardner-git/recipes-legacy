package org.recipes.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.recipes.model.Post;
import org.recipes.model.Thread;
import org.recipes.model.User;

public class PostDTO {

	Integer id;
	String title;
	String content;
	
	UserDTO user;
	PostDTO parent; //the post that this post is a response to	
	ThreadDTO thread;
	@JsonIgnore
	Date postDate;

	public PostDTO() {

	}

	public PostDTO(Post entity) {
		this.id=entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.user = new UserDTO();
		this.user.setId(entity.getUserFk());
		this.postDate = entity.getPostDate();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public ThreadDTO getThread() {
		return thread;
	}

	public void setThread(ThreadDTO thread) {
		this.thread=thread;
	}

	public Post constructPostEntity() {
		Post post = new Post();
		post.setContent(content);
		post.setId(id);
		post.setPostDate(postDate);

		if (this.thread != null) {
			org.recipes.model.Thread threadEntity = new Thread();
			threadEntity.setId(thread.getId());
			post.setThread(threadEntity);
		}
		
		post.setTitle(title);
		if (this.user != null) {
			post.setUserFk(this.user.getId());
		}
		if (this.parent != null) {
			Post parent = new Post();
			parent.setId(this.parent.getId());
		}
		return post;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public PostDTO getParent() {
		return parent;
	}

	public void setParent(PostDTO parent) {
		this.parent = parent;
	}




}
