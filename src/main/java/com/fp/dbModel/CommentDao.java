package com.fp.dbModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.model.Comment;
import com.fp.model.User;

@Component
public class CommentDao {

	private static final String ADD_COMMENT = "INSERT INTO comments (date_upload, description,post_id, user_id) VALUES (?,?,?,?)";
	private static final String DELETE_COMMENT = "DELETE FROM comments WHERE comment_id=?";
	private static final String LIKE_COMMENT = "INSERT INTO users_like_comments (user_id,comment_id) VALUES (?,?)";
	private static final String REMOVE_COMMENTS_LIKES = "DELETE FROM users_like_comments WHERE comment_id = ?";
	private static final String REMOVE_COMMENTS_DISLIKES = "DELETE FROM users_dislike_comments WHERE comment_id = ?";

	@Autowired
	private DbManager manager;

	@Autowired
	private UserDao userDao;

	public synchronized Comment addComment(Long userId, String text, Long postId) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(ADD_COMMENT);
		LocalDateTime dateAndTime = LocalDateTime.now();
		ps.setTimestamp(1, Timestamp.valueOf(dateAndTime));
		ps.setString(2, text);
		ps.setLong(3, postId);
		ps.setLong(4, userId);
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		Comment comment = new Comment(userId, text, postId);
		long id = rs.getLong(1);
		comment.setId(id);
		comment.setdateAndTimeOfUpload(dateAndTime);

		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return comment;
	}

	// delete comment
	public void deleteComment(long comment_id) throws SQLException {
		PreparedStatement deleteLikes = null;
		PreparedStatement deleteDislikes = null;
		PreparedStatement deleteComment = null;
		try {
			manager.getConnection().setAutoCommit(false);
			deleteLikes = manager.getConnection().prepareStatement(REMOVE_COMMENTS_LIKES);
			deleteLikes.setLong(1, comment_id);
			deleteLikes.executeUpdate();

			deleteDislikes = manager.getConnection().prepareStatement(REMOVE_COMMENTS_DISLIKES);
			deleteDislikes.setLong(1, comment_id);
			deleteDislikes.executeUpdate();

			deleteComment = manager.getConnection().prepareStatement(DELETE_COMMENT);
			deleteComment.setLong(1, comment_id);
			deleteComment.executeUpdate();
			manager.getConnection().commit();
		} catch (SQLException e) {
			System.err.print("Transaction is being rolled back");
			manager.getConnection().rollback();
			throw e;
		} finally {
			if (deleteLikes != null) {
				deleteLikes.close();
			}
			if (deleteDislikes != null) {
				deleteDislikes.close();
			}
			if (deleteComment != null) {
				deleteComment.close();
			}
			manager.getConnection().setAutoCommit(true);
		}
	}

	// like comment
	public synchronized void likeComment(long commentId, String username) throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(LIKE_COMMENT);

		PreparedStatement selectUserId = manager.getConnection()
				.prepareStatement("SELECT user_id FROM users WHERE username=?;");
		selectUserId.setString(1, username);
		ResultSet rs = selectUserId.executeQuery();
		rs.next();
		long userId = rs.getLong(1);

		ps.setLong(1, userId);
		ps.setLong(2, commentId);
		ps.executeUpdate();

		if (selectUserId != null) {
			selectUserId.close();
		}
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
	}

	public synchronized void removeLikeComment(long commentId, String username) throws SQLException {
		manager.getConnection().setAutoCommit(false);
		try {
			PreparedStatement ps = manager.getConnection()
					.prepareStatement("DELETE FROM users_like_comments WHERE user_id=? AND comment_id=?");

			PreparedStatement selectUserId = manager.getConnection()
					.prepareStatement("SELECT user_id FROM users WHERE username=?;");
			selectUserId.setString(1, username);
			ResultSet rs = selectUserId.executeQuery();
			rs.next();
			long userId = rs.getLong(1);

			ps.setLong(1, userId);
			ps.setLong(2, commentId);
			ps.executeUpdate();

			if (selectUserId != null) {
				selectUserId.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;
		} finally {
			manager.getConnection().setAutoCommit(true);
		}
	}

	public synchronized void addDislike(long commentId, String username) throws SQLException {
		manager.getConnection().setAutoCommit(false);
		try {
			PreparedStatement ps = manager.getConnection()
					.prepareStatement("INSERT INTO users_dislike_comments (user_id, comment_id) VALUES (?,?);");

			PreparedStatement selectUserId = manager.getConnection()
					.prepareStatement("SELECT user_id FROM users WHERE username=?;");
			selectUserId.setString(1, username);
			ResultSet rs = selectUserId.executeQuery();
			rs.next();
			long userId = rs.getLong(1);

			ps.setLong(1, userId);
			ps.setLong(2, commentId);
			ps.executeUpdate();

			if (selectUserId != null) {
				selectUserId.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			manager.getConnection().commit();
		} catch (

		SQLException e) {
			manager.getConnection().rollback();
			throw e;
		} finally {
			manager.getConnection().setAutoCommit(true);
		}
	}

	public synchronized void removeDislike(long commentId, String username) throws SQLException {
		PreparedStatement selectUserId = null;
		PreparedStatement psDeleteLike = null;
		ResultSet rs = null;
		try {
			selectUserId = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
			selectUserId.setString(1, username);
			rs = selectUserId.executeQuery();
			rs.next();
			long userId = rs.getLong(1);

			psDeleteLike = manager.getConnection()
					.prepareStatement("DELETE FROM users_dislike_comments WHERE user_id=? AND comment_id=?");
			psDeleteLike.setLong(1, userId);
			psDeleteLike.setLong(2, commentId);
			psDeleteLike.executeUpdate();
		} finally {
			if (selectUserId != null) {
				selectUserId.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (psDeleteLike != null) {
				psDeleteLike.close();
			}
		}
	}

	public synchronized void updateNumberOfLikesAndDislikes(long commentId, int numberOfLikes, int numberOfDislikes)
			throws SQLException {
		String updateCommentNumberOfLikesStatement = "UPDATE comments SET number_of_likes=?, number_of_dislikes=? WHERE comment_id=?;";
		PreparedStatement ps = manager.getConnection().prepareStatement(updateCommentNumberOfLikesStatement);
		ps.setInt(1, numberOfLikes);
		ps.setInt(2, numberOfDislikes);
		ps.setLong(3, commentId);
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}
	}

	public synchronized Set<Comment> getAllComments(long postId) throws SQLException {
		Set<Comment> comments = new TreeSet<>(Comparator.comparing(Comment::getdateAndTimeOfUpload).reversed());
		PreparedStatement ps = manager.getConnection().prepareStatement(
				"SELECT comment_id,user_id, description, date_upload , number_of_likes, number_of_dislikes FROM comments WHERE post_id=?;");
		ps.setLong(1, postId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Long userId = rs.getLong("user_id");
			User user = userDao.getUser(userId);
			Long commentId = rs.getLong("comment_id");
			Set<User> likers = getAllCommentUserLikersFromDB(commentId);
			Set<User> dislikers = getAllCommentUserDislikersFromDB(commentId);
			Comment comment = new Comment(commentId, user.getUserName(), rs.getString("description"),
					rs.getTimestamp("date_upload").toLocalDateTime(), likers, dislikers);
			comments.add(comment);
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return comments;
	}

	public synchronized Set<User> getAllCommentUserLikersFromDB(long commentId) throws SQLException {
		Set<User> allComentLikers = new HashSet<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String selectAllComentLikersFromDB = "SELECT user_id FROM users_like_comments WHERE comment_id=?;";

			ps = manager.getConnection().prepareStatement(selectAllComentLikersFromDB);
			ps.setLong(1, commentId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long userId = rs.getLong("user_id");

				PreparedStatement psOne = manager.getConnection()
						.prepareStatement("SELECT username FROM users WHERE user_id=?;");
				psOne.setLong(1, userId);
				ResultSet rsOne = psOne.executeQuery();
				rsOne.next();
				String username = rsOne.getString(1);

				allComentLikers.add(userDao.getUser(username));
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return allComentLikers;
	}

	public synchronized Set<User> getAllCommentUserDislikersFromDB(long commentId) throws SQLException {
		Set<User> allComentDislikers = new HashSet<>();
		String selectAllComentLikersFromDB = "SELECT user_id FROM users_dislike_comments WHERE comment_id=?;";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = manager.getConnection().prepareStatement(selectAllComentLikersFromDB);
			ps.setLong(1, commentId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long userId = rs.getLong("user_id");

				PreparedStatement psOne = manager.getConnection()
						.prepareStatement("SELECT username FROM users WHERE user_id=?;");
				psOne.setLong(1, userId);
				ResultSet rsOne = psOne.executeQuery();
				rsOne.next();
				String username = rsOne.getString(1);

				allComentDislikers.add(userDao.getUser(username));
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return allComentDislikers;
	}

	public String getUserName(long commentId, long userId) {
		PreparedStatement ps;
		String userName = null;
		try {
			ps = manager.getConnection().prepareStatement(
					"SELECT u.username FROM comments AS c JOIN users AS u ON (c.user_id = u.user_id) WHERE c.user_id = ? AND c.comment_id = ? ");

			ps.setLong(1, userId);
			ps.setLong(2, commentId);
			ResultSet rs = ps.executeQuery();
			rs.next();

			userName = rs.getString("username");
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userName;
	}

}
