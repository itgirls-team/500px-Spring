package com.fp.domain;

import java.time.LocalDateTime;

public class CommentDto {

	private Long id;
	private Long userId;
	private Long postId;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;
	private int numberOfLikes;
	private int numberOfDislikes;

	public CommentDto() {
	}

	public CommentDto(Long id, Long userId, Long postId, String description, LocalDateTime dateAndTimeOfUpload,
			int numberOfLikes, int numberOfDislikes) {
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.description = description;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateAndTimeOfUpload() {
		return dateAndTimeOfUpload;
	}

	public void setDateAndTimeOfUpload(LocalDateTime dateAndTimeOfUpload) {
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}

	public void setNumberOfDislikes(int numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}

}
