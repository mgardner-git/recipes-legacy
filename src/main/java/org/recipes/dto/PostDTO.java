package org.recipes.dto;

import java.util.Date;

import org.recipes.model.Post;
import org.recipes.model.Recipe;
import org.recipes.model.Thread;

public class PostDTO {

	int id;
	String title;
	String content;
	Integer recipe_fk;
	String user_fk;
	Integer parentPost_fk;
	ThreadDTO thread;
	IngredientDTO ingredient;
	
	Date postDate;

	public PostDTO() {

	}

	public PostDTO(Post entity) {
		this.id=entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		if (entity.getRecipe() != null) {
			this.recipe_fk = entity.getRecipeFk();
		}
		if (entity.getIngredient() != null) {
			this.ingredient = new IngredientDTO(entity.getIngredient());
		}
		if (entity.getThread() != null) {
			this.thread = new ThreadDTO(entity.getThread());
		}
		this.user_fk = entity.getUserFk();
		this.postDate = entity.getPostDate();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(String user_fk) {
		this.user_fk = user_fk;
	}

	public Integer getRecipe_fk() {
		return recipe_fk;
	}

	public void setRecipe_fk(Integer recipe_fk) {
		this.recipe_fk = recipe_fk;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getParentPost_fk() {
		return parentPost_fk;
	}

	public void setParentPost_fk(Integer parentPost_fk) {
		this.parentPost_fk = parentPost_fk;
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
		if (this.recipe_fk != null) {
			Recipe recipeEntity = new Recipe();
			recipeEntity.setId(this.recipe_fk);
			post.setRecipe(recipeEntity);
		}
		if (this.thread != null) {
			org.recipes.model.Thread threadEntity = new Thread();
			threadEntity.setId(thread.getId());
			post.setThread(threadEntity);
		}
		post.setTitle(title);
		post.setUserFk(user_fk);
		return post;
	}


	public IngredientDTO getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientDTO ingredient) {
		this.ingredient = ingredient;
	}

}
