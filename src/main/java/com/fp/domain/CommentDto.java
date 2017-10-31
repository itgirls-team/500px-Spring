package com.fp.domain;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {

	private Long id;
	private Long userId;
	private String userName;
	UserDto userDto;
	private Long postId;
	private List<UserDto> usersWhoLike;
	private List<UserDto> usersWhoDislike;
	private int numberOfLikes;
	private int numberOfDislikes;
	private String description;
	private LocalDateTime dateAndTimeOfUpload;

	public CommentDto() {
	}

	public CommentDto(Long id, UserDto userDto, Long postId, List<UserDto> usersWhoLike,
			List<UserDto> usersWhoDislike) {
		this.id = id;
		this.postId = postId;
		this.usersWhoLike = usersWhoLike;
		this.usersWhoDislike = usersWhoDislike;
		this.userDto = userDto;
	}

	public CommentDto(Long id, String userName, Long postId, String description, LocalDateTime dateAndTimeOfUpload,
			int numbersOfLikes, int numbersOfDislikes) {
		this.id = id;
		this.postId = postId;
		this.numberOfLikes = numbersOfLikes;
		this.numberOfDislikes = numbersOfDislikes;
		this.userName = userName;
		this.description = description;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public CommentDto(Long id, Long userId, Long postId, String description, LocalDateTime dateAndTimeOfUpload,
			int numbersOfLikes, int numbersOfDislikes) {
		this.id = id;
		this.postId = postId;
		this.numberOfLikes = numbersOfLikes;
		this.numberOfDislikes = numbersOfDislikes;
		this.userId = userId;
		this.description = description;
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public List<UserDto> getUsersWhoLike() {
		return usersWhoLike;
	}

	public void setUsersWhoLike(List<UserDto> usersWhoLike) {
		this.usersWhoLike = usersWhoLike;
	}

	public List<UserDto> getUsersWhoDislike() {
		return usersWhoDislike;
	}

	public void setUsersWhoDislike(List<UserDto> usersWhoDislike) {
		this.usersWhoDislike = usersWhoDislike;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numbersOfLikes) {
		this.numberOfLikes = numbersOfLikes;
	}

	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}

	public void setNumberOfDislikes(int numbersOfDislikes) {
		this.numberOfDislikes = numbersOfDislikes;
	}

	public LocalDateTime getDateAndTimeOfUpload() {
		return dateAndTimeOfUpload;
	}

	public void setDateAndTimeOfUpload(LocalDateTime dateAndTimeOfUpload) {
		this.dateAndTimeOfUpload = dateAndTimeOfUpload;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
