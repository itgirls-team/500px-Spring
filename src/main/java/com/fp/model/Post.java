package com.fp.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Post {

	private long id;
	private String path;
	private int countsOfLikes;
	private int countsOfDislikes;
	private Timestamp dateOfUpload;
	private String description;
	private long albumId;
	private Set<Comment> commentsOfPost;
	private Set<User> usersWhoLike;
	private Set<User> usersWhoDislike;
	private Set<Tag> tagsOfPost;


	public Post(String path, String description, int countsOfLikes, int countsOfDislikes, Set<Tag> tags,
			Set<Comment> commentsOfPost, Set<User> usersWhoLike, Set<User> usersWhoDislike,Timestamp dateOfUpload) {
		this.path = path;
		this.countsOfLikes = countsOfLikes;
		this.countsOfDislikes = countsOfDislikes;
		this.dateOfUpload = dateOfUpload;
		this.description = description;
		this.tagsOfPost = tags;
		this.commentsOfPost = commentsOfPost;
		this.usersWhoLike = usersWhoLike;
		this.usersWhoDislike = usersWhoDislike;
	}
	
	public Post(long id,String path, String description, int countsOfLikes, int countsOfDislikes, Set<Tag> tags,
			Set<Comment> commentsOfPost, Set<User> usersWhoLike, Set<User> usersWhoDislike,Timestamp dateOfUpload) {
		this(path,description,countsOfLikes,countsOfDislikes,tags,commentsOfPost,usersWhoLike,usersWhoDislike,dateOfUpload);
		this.id = id;
	}

	public Post(String path, String description, int countsOfLikes, int countsOfDislikes, Set<Tag> tags, long albumId,
			Set<Comment> commentsOfPost, Set<User> usersWhoLike, Set<User> usersWhoDislike,Timestamp dateOfUpload) {
		this(path,description,countsOfLikes,countsOfDislikes,tags,commentsOfPost,usersWhoLike,usersWhoDislike,dateOfUpload);
		this.albumId = albumId;
	}
	
	public Post(long id,String path, String description, int countsOfLikes, int countsOfDislikes, Set<Tag> tags, long albumId,
			Set<Comment> commentsOfPost, Set<User> usersWhoLike, Set<User> usersWhoDislike,Timestamp dateOfUpload) {
		this(id,path,description,countsOfLikes,countsOfDislikes,tags,commentsOfPost,usersWhoLike,usersWhoDislike,dateOfUpload);
		this.albumId = albumId;
	}

	public Post(String path, String description, Set<Tag> tags, long album_id,Timestamp dateOfUpload) {
		this(path, description, 0, 0, tags, album_id, new HashSet<>(), new HashSet<>(), new HashSet<>(),dateOfUpload);
	}
  
	public Post(long id,String path, String description, Set<Tag> tags, long album_id,Timestamp dateOfUpload) {
		this(id,path, description, 0, 0, tags, album_id, new HashSet<>(), new HashSet<>(), new HashSet<>(),dateOfUpload);
		this.id = id;
	}
	
	// Getters
	public long getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public int getCountsOfLikes() {
		return countsOfLikes;
	}

	public int getCountsOfDislikes() {
		return countsOfDislikes;
	}

	public Timestamp getDateOfUpload() {
		return dateOfUpload;
	}

	public String getDescription() {
		return description;
	}

	public Set<Comment> getCommentsOfPost() {
		return Collections.unmodifiableSet(commentsOfPost);
	}

	public long getAlbumId() {
		return albumId;
	}

	public Set<User> getUsersWhoLike() {
		return Collections.unmodifiableSet(usersWhoLike);
	}

	public Set<User> getUsersWhoDislike() {
		return Collections.unmodifiableSet(usersWhoDislike);
	}

	public Set<Tag> getTagsOfPost() {
		return Collections.unmodifiableSet(tagsOfPost);
	}

	public void setPath(String path) {
		this.path = path;
	}

	// Setter

	public void setPostId(long id) {
		this.id = id;
	}

	public void setCommentsOfPost(Set<Comment> commentsOfPost) {
		this.commentsOfPost = commentsOfPost;
	}

	public void setCountsOfLikes(int countsOfLikes) {
		this.countsOfLikes = countsOfLikes;
	}

	public void setCountsOfDislikes(int countsOfDislikes) {
		this.countsOfDislikes = countsOfDislikes;
	}

	// HashCode and Equals
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
		Post other = (Post) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", path=" + path + ", countsOfLikes=" + countsOfLikes + ", countsOfDislikes="
				+ countsOfDislikes + ", dateOfUpload=" + dateOfUpload + ", description=" + description + ", albumId="
				+ albumId + ", commentsOfPost=" + commentsOfPost + ", usersWhoLike=" + usersWhoLike
				+ ", usersWhoDislike=" + usersWhoDislike + ", tagsOfPost=" + tagsOfPost + "]";
	}

}
