package com.fp.dbModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.model.Album;
import com.fp.utils.CommonUtils;

@Component
public class AlbumDao {

	private static final String CREATE_ALBUM = "INSERT INTO albums (category, date_upload, picture, user_id ) VALUES (?,?,?,?)";
	private static final String SELECT_ALBUMS_BY_USER = "SELECT album_id, category, picture, user_id, date_upload FROM albums WHERE user_id = ?";
	private static final String DELETE_POSTS_FROM_ALBUM = "DELETE FROM posts WHERE album_id = ?";
	private static final String DELETE_ALBUM = "DELETE FROM albums WHERE album_id =?";
	private static final String SELECT_ALBUM_BY_ALBUM_ID = "SELECT album_id,category,date_upload,picture,user_id FROM albums WHERE album_id = ?";
	private static final String EXISTS_ALBUM = "SELECT count(*)>0 FROM albums WHERE category=? AND user_id=?";

	@Autowired
	private DbManager manager;

	// createAlbum
	public synchronized void createAlbum(Album a) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(CREATE_ALBUM, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, a.getCategory());
		ps.setDate(2, Date.valueOf(LocalDate.now()));
		ps.setString(3, a.getPicture());
		ps.setLong(4, a.getUser());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		a.setId(rs.getLong(1));
		if (ps != null) {
			ps.close();
		}
	}

	// getAllAlbumFromUser
	public Set<Album> getAllAlbumFromUser(long userId) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(SELECT_ALBUMS_BY_USER);
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		Set<Album> albums = new TreeSet<>(Comparator.comparing(Album::getCategory).reversed());
		while (rs.next()) {
			albums.add(new Album(rs.getLong("album_id"), rs.getString("category"), rs.getString("picture"), userId,
					rs.getTimestamp("date_upload")));
		}
		return albums;
	}

	// deleteAlbum
	public void deleteAlbum(Album a) throws SQLException {
		PreparedStatement deletePosts = null;
		PreparedStatement deleteAlbum = null;
		try {
			manager.getConnection().setAutoCommit(false);
			deletePosts = manager.getConnection().prepareStatement(DELETE_POSTS_FROM_ALBUM);
			deletePosts.setLong(1, a.getId());
			deletePosts.executeUpdate();

			deleteAlbum = manager.getConnection().prepareStatement(DELETE_ALBUM);
			deleteAlbum.setLong(1, a.getId());
			deleteAlbum.executeUpdate();
			manager.getConnection().commit();
		} catch (SQLException e) {
			System.err.print("Transaction is being rolled back");
			manager.getConnection().rollback();
			throw e;
		} finally {
			if (deletePosts != null) {
				deletePosts.close();
			}
			if (deleteAlbum != null) {
				deleteAlbum.close();
			}
			manager.getConnection().setAutoCommit(true);
		}
	}

	public synchronized boolean existAlbum(String category, Long userId) throws SQLException {
		PreparedStatement ps = null;
		boolean albumExists = true;
		if (!CommonUtils.isValidString(category)) {
			albumExists = false;
		}
		ps = manager.getConnection().prepareStatement(EXISTS_ALBUM);
		ps.setString(1, category);
		ps.setLong(2, userId);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			albumExists = rs.getBoolean(1);
		}
		if (ps != null) {
			ps.close();
		}
		return albumExists;
	}

	public Set<Album> getAllAlbumFromUser(String username) throws SQLException {
		Set<Album> albums = new TreeSet<>(Comparator.comparing(Album::getCategory).reversed());
		// take id of the user
		PreparedStatement ps = manager.getConnection()
				.prepareStatement("SELECT user_id FROM users WHERE username = ? ");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		Long userId = rs.getLong(1);

		albums = getAllAlbumFromUser(userId);

		return albums;
	}

	public Album getAlbum(long albumId) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(SELECT_ALBUM_BY_ALBUM_ID);
		ps.setLong(1, albumId);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new Album(albumId, rs.getString("category"), rs.getString("picture"), rs.getLong("user_id"),
				rs.getTimestamp("date_upload"));
	}

	public String getCover(Long id) throws SQLException {
		if (id == null) {
			return null;
		}
		PreparedStatement ps = manager.getConnection().prepareStatement("SELECT picture FROM albums WHERE album_id=?;");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		String pic = rs.getString("picture");

		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return pic;
	}

	public void changeCover(Long id, String picture) throws SQLException {
		if (id == null) {
			return;
		}
		PreparedStatement ps = manager.getConnection()
				.prepareStatement("UPDATE albums SET picture=? WHERE album_id=?;");
		ps.setString(1, picture);
		ps.setLong(2, id);
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}
	}

}