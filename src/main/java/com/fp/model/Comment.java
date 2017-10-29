package com.fp.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fp.dbModel.CommentDao;

public class Comment {

	@Autowired
	private CommentDao commentDao; // TODO remove ASAP!
	private Long id;
	private Long userId;
	private Long postId;
	private String userName;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;
	private Post post;
	private int numberOfLikes;
	private int numberOfDislikes;

	public Comment(Long userId, String description, Post post) {
		this.userId = userId;
		this.description = description;
		this.post = post;
	}

	public Comment(Long userId, String description, Long postId) {
		this.userId = userId;
		this.description = description;
		this.postId = postId;
	}

	public Comment(Long userId, String description, Long postId, LocalDateTime dateAndTimeOfUpload) {
		this(userId, description, postId);
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public Comment(Long id, Long user, String description, Post post) {
		this(user, description, post);
		this.id = id;
	}

	public Comment(Long commentId, Long userId, String description, LocalDateTime dateAndTimeOfUpload,
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

	public LocalDateTime getDateAndTimeOfUpload() {
		return dateAndTimeOfUpload;
	}

	public void setDateAndTimeOfUpload(LocalDateTime dateAndTimeOfUpload) {
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public void setNumberOfDislikes(int numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", description=" + description + ", dateAndTimeOfUpload="
				+ dateAndTimeOfUpload + ", post=" + post + ", numberOfLikes=" + numberOfLikes + ", numberOfDislikes="
				+ numberOfDislikes + ", usersLikedTheComment=" + "]";
	}
}
