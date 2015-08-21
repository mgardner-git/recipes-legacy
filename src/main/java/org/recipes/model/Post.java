package org.recipes.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the posts database table.
 * 
 */
@Entity
@Table(name="posts")
@NamedQueries({
	@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p"),
	@NamedQuery(name="Post.findAllByThread", query="SELECT p FROM Post p WHERE p.thread.id=:threadId ORDER BY p.postDate ASC")
})
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String content;

	@Temporal(TemporalType.DATE)
	@Column(name="post_date")
	private Date postDate;


	
	
	private String title;

	@Column(name="user_fk")
	private String userFk;

	
	//bi-directional many-to-one association to Thread
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "thread_fk", insertable = true, updatable = true)
	private Thread thread;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="post_fk")
	private Post responseTo;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="responseTo", fetch=FetchType.EAGER)
	private List<Post> posts;

	public Post() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserFk() {
		return this.userFk;
	}

	public void setUserFk(String userFk) {
		this.userFk = userFk;
	}

	public Thread getThread() {
		return this.thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Post getResponses() {
		return this.responseTo;
	}

	public void setResponses(Post responses) {
		this.responseTo = responses;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setResponses(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setResponses(null);

		return post;
	}


}