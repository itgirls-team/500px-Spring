package com.fp.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Comment {

	private Long id;
	private Long userId;
	private Long postId;
	private String userName;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;
	private Post post;
	private int numberOfLikes;
	private int numberOfDislikes;
	private Set<User> usersWhoLikeComment;
	private Set<User> usersWhoDislikeComment;

	public Comment(Long userId, String description, Post post) {
		this.userId = userId;
		this.description = description;
		this.post = post;
	}

	public Comment(Long userId, String description, Long postId) {
		this.userId = userId;
		this.description = description;
		this.postId = postId;
		this.usersWhoLikeComment = new HashSet<>();
		this.usersWhoDislikeComment = new HashSet<>();
	}

	public Comment(Long userId, String description, Long postId, LocalDateTime dateAndTimeOfUpload) {
		this(userId, description, postId);
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
		this.usersWhoLikeComment = new HashSet<>();
		this.usersWhoDislikeComment = new HashSet<>();
	}

	public Comment(Long id, Long user, String description, Post post) {
		this(user, description, post);
		this.id = id;
		this.usersWhoLikeComment = new HashSet<>();
		this.usersWhoDislikeComment = new HashSet<>();
	}

	public Comment(Long commentId, Long userId, String description, LocalDateTime dateAndTimeOfUpload,
			int numberOfLikes, int numberOfDislikes) {
		this.userId = userId;
		this.id = commentId;
		this.description = description;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
		this.usersWhoLikeComment = new HashSet<>();
		this.usersWhoDislikeComment = new HashSet<>();
	}

	public Comment(Long commentId, String userName, String description, LocalDateTime dateAndTimeOfUpload,
			int numberOfLikes, int numberOfDislikes) {
		this.userName = userName;
		this.id = commentId;
		this.description = description;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
		this.usersWhoLikeComment = new HashSet<>();
		this.usersWhoDislikeComment = new HashSet<>();
	}

	public Comment(Long commentId, Long userId, String description, LocalDateTime dateAndTimeOfUpload,
			Set<User> usersWhoLikeComment, Set<User> usersWhoDislikeComment) {
		this.userId = userId;
		this.id = commentId;
		this.description = description;
		this.usersWhoLikeComment = usersWhoLikeComment;
		this.usersWhoDislikeComment = usersWhoDislikeComment;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
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

	public void setId(Long id) {
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

	public Set<User> getUsersWhoLikeComment() {
		// if (usersWhoLikeComment == null) {
		// return null;
		// }
		return Collections.unmodifiableSet(usersWhoLikeComment);
	}

	public void setUsersWhoLikeComment(Set<User> usersWhoLikeComment) {
		this.usersWhoLikeComment = usersWhoLikeComment;
	}

	public Set<User> getUsersWhoDislikeComment() {
		// if (usersWhoDislikeComment == null) {
		// return null;
		// }
		return Collections.unmodifiableSet(usersWhoDislikeComment);
	}

	public void setUsersWhoDislikeComment(Set<User> usersWhoDislikeComment) {
		this.usersWhoDislikeComment = usersWhoDislikeComment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", description=" + description + ", dateAndTimeOfUpload="
				+ dateAndTimeOfUpload + ", post=" + post + ", numberOfLikes=" + numberOfLikes + ", numberOfDislikes="
				+ numberOfDislikes + ", usersLikedTheComment=" + "]";
	}
}
