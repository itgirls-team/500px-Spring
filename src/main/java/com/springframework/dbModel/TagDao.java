package com.springframework.dbModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springframework.model.Post;
import com.springframework.model.Tag;

@Component
public class TagDao {

	@Autowired
	private DbManager manager;

	private static final String INSERT_POST_TAG = "INSERT INTO post_tag (post_id, tag_id) VALUES (?, ?)";
	private static final String SELECT_TAG = "SELECT * FROM tags WHERE title = ?";
	private static final String SELECT_TITLE_OF_TAG = "SELECT title FROM tags WHERE title = ?";
	private static final String INSERT_TAG = "INSERT INTO tags (title) VALUES (?)";
	private static final String SELECT_TAGS_FROM_POST = "SELECT t.title FROM post_tag AS p JOIN tags AS t USING (tag_id) WHERE p.post_id = ? ";

	// insert into common table
	public synchronized void insertPostTags(Post p) throws SQLException {
		Set<Tag> tags = p.getTagsOfPost();

		String insert_into_post_tag = INSERT_POST_TAG;

		for (Tag tag : tags) {
			insertTag(tag);
			tag = getTag(tag.getTitle());
			PreparedStatement post_tag = manager.getConnection().prepareStatement(insert_into_post_tag);
			post_tag.setLong(1, p.getId());
			post_tag.setLong(2, tag.getId());
			post_tag.executeUpdate();
		}
	}

	// insertTag
	public synchronized void insertTag(Tag t) throws SQLException {
		if (!existTag(t)) {
			PreparedStatement ps = manager.getConnection().prepareStatement(INSERT_TAG);
			ps.setString(1, t.getTitle());
			ps.executeUpdate();
		}
	}

	// getTag
	public Tag getTag(String tag) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(SELECT_TAG);
		ps.setString(1, tag);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new Tag(rs.getLong("tag_id"), rs.getString("title"));
	}

	// is exist
	public boolean existTag(Tag t) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(SELECT_TITLE_OF_TAG);
		ps.setString(1, t.getTitle());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	// get all tags from post
	public HashSet<Tag> getAllTagsFromPost(long post_id) throws SQLException {
		PreparedStatement ps;
		ps = manager.getConnection().prepareStatement(SELECT_TAGS_FROM_POST);
		ps.setLong(1, post_id);
		ResultSet rs = ps.executeQuery();
		HashSet<Tag> tags = new HashSet<>();
		while (rs.next()) {
			tags.add(new Tag(rs.getLong("tag_id"), rs.getString("title")));
		}
		return tags;
	}

	public static void main(String[] args) throws SQLException {
		// TagDao.getInstance().insertTag(new Tag("angry"));
		// Tag tag = TagDao.getInstance().getTag("angry");
		// System.out.println(tag);
		// System.out.println(TagDao.getInstance().existTag(new Tag("angry")));
	}

}
