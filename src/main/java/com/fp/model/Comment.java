package com.fp.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fp.dbModel.CommentDao;

public class Comment {

	@Autowired
	private CommentDao commentDao;
	private long id;
	private long userId;
	// private User user;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;
	private Post post;
	private int numberOfLikes;
	private int numberOfDislikes;
	//private User user = commentDao.getUser(id, userId);
	// private HashSet<User> usersLikedTheComment = new HashSet<User>();

	public Comment(long user, String description, Post post) {
		// this.user = user;
		this.userId = user;
		this.description = description;
		this.post = post;
	}

	public Comment(long id, long user, String description, Post post) {
		this(user, description, post);
		this.id = id;
	}

	public Comment(long commentId, long userId, String description, LocalDateTime dateAndTimeOfUpload,
			int numberOfLikes, int numberOfDislikes) {
		this.userId = userId;
		// this.user.setId(userId);
		this.id = commentId;
		this.description = description;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}
	/*public User getUser() {
		return user;
	}
	*/
	public String getDescription() {
		return description;
	}

	public LocalDateTime getdateAndTimeOfUpload() {
		return dateAndTimeOfUpload;
	}

	public Post getPost() {
		return post;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}
	
	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setdateAndTimeOfUpload(LocalDateTime dateAndTimeOfUpload) {
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", description=" + description + ", dateAndTimeOfUpload="
				+ dateAndTimeOfUpload + ", post=" + post + ", numberOfLikes=" + numberOfLikes + ", numberOfDislikes="
				+ numberOfDislikes + ", usersLikedTheComment=" + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
