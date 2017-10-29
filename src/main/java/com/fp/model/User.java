package com.fp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "password" })
public class User implements Serializable {

	private Long id;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String description;
	private String profilePicture;
	private LocalDate registerDate;
	private Set<User> followers = new HashSet<>();;
	private Set<User> following = new HashSet<>();;
	private Set<Album> albumsOfUser = new HashSet<>();

	public User(String firstName, String lastName, String email, String userName, String profilePicture,
			String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.description = description;
		this.profilePicture = profilePicture;
	}

	public User(String userName, String password, String email, String firstName, String lastName, String description,
			String profilePicture) {
		this(firstName, lastName, email, userName, profilePicture, description);
		this.password = password;
	}

	public User(Long id, String firstName, String lastName, String email, String userName, LocalDate registerDate,
			String profilePicture, String description) {
		this(firstName, lastName, email, userName, profilePicture, description);
		this.id = id;
		this.registerDate = registerDate;
	}

	public User(String firstName, String lastName, String email, String userName, LocalDate registerDate,
			String profilePicture, String description) {
		this(firstName, lastName, email, userName, profilePicture, description);
		this.registerDate = registerDate;
	}

	public User(Long id, String userName, String firstName, String lastName, String password, String email,
			String description, String profilePicture, LocalDate registerDate) {
		this(userName, firstName, lastName, password, email, description, profilePicture);
		this.id = id;
		this.registerDate = registerDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public Long getId() {
		return id;
	}

	public void setAlbumsOfUser(Set<Album> albumsOfUser) {
		this.albumsOfUser = albumsOfUser;
	}

	public Set<Album> getAlbumsOfUser() {
		return Collections.unmodifiableSet(albumsOfUser);
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<User> getFollowers() {
		return Collections.unmodifiableSet(followers);
	}

	public Set<User> getFollowing() {
		return Collections.unmodifiableSet(following);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
