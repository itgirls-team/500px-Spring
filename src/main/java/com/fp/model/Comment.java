package com.fp.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fp.dbModel.CommentDao;

public class Comment {

	@Autowired
	private CommentDao commentDao;
	private long id;
	private long userId;
	private String userName;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;
	private Post post;
	private int numberOfLikes;
	private int numberOfDislikes;

	public Comment(long userId, String description, Post post) {
		this.userId = userId;
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
	
	public String getUserName() {
		userName = commentDao.getUserName(id, userId);
		return userName;
	}
	
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
