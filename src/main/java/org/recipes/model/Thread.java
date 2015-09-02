package org.recipes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * The persistent class for the threads database table.
 * Represents a discussion thread on a particular topic within a group.
 */
@Entity
@Table(name="threads")
@NamedQueries({
	@NamedQuery(name="Thread.findAll", query="SELECT t FROM Thread t"),
	@NamedQuery(name="Thread.findByGroupAndRecipe", query="SELECT T FROM Thread T WHERE T.group.id=:groupId AND T.recipe.id=:recipeId"),
	@NamedQuery(name="Thread.findByGroupAndIngredient", query="SELECT T FROM Thread T WHERE T.group.id=:groupId AND T.ingredient.id=:ingredientId"),
	@NamedQuery(name="Thread.findByRecipe", query="SELECT T FROM Thread T WHERE T.recipe.id = :recipeId"),
	@NamedQuery(name="Thread.findByIngredient", query="SELECT T FROM Thread T WHERE T.ingredient.id = :ingredientId")
})
public class Thread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="group_fk",insertable=false, updatable=false)
	private int groupFk;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_fk")
	private Group group;
	
	private String title;

	//bi-directional many-to-one association to Post
	@OneToMany (cascade=CascadeType.ALL, targetEntity=Post.class, mappedBy="thread", fetch=FetchType.EAGER)
	@OrderBy("postDate DESC")
	private List<Post> posts;

	@Column(name="recipe_fk",insertable=false, updatable=false)
	private Integer recipeFk;
	
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="recipe_fk", unique=true, nullable=true, updatable=false)
	private Recipe recipe;
	
	@Column(name="ingredient_fk", insertable=false, updatable=false)
	private Integer ingredientId;
	
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ingredient_fk", unique=true, nullable=true, updatable=false)
	private Ingredient ingredient;
	
	public Thread() {
		posts = new ArrayList<Post>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupFk() {
		return this.groupFk;
	}

	public void setGroupFk(int groupFk) {
		this.groupFk = groupFk;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setThread(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setThread(null);

		return post;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Integer getRecipeFk() {
		return recipeFk;
	}

	public void setRecipeFk(Integer recipeFk) {
		this.recipeFk = recipeFk;
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Integer ingredientFk) {
		this.ingredientId = ingredientFk;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}


}