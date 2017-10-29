package com.fp.domain;

import java.util.List;

public class PostDto {

	private List<UserDto> usersWhoLike;
	private List<UserDto> usersWhoDislike;
	private UserDto userDto;

	public PostDto() {
	}

	public PostDto(List<UserDto> usersWhoLike, List<UserDto> usersWhoDislike, UserDto userDto) {
		this.usersWhoLike = usersWhoLike;
		this.usersWhoDislike = usersWhoDislike;
		this.userDto = userDto;
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

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
