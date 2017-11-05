package com.fp.dbModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.model.User;
import com.fp.utils.CommonUtils;

@Component
public class UserDao {

	@Autowired
	private DbManager manager;

	public synchronized void insertUser(String userName, String password, String email, String firstName,
			String lastName, String description, String pictureName) throws SQLException {
		PreparedStatement ps = null;
		ps = manager.getConnection().prepareStatement(
				"INSERT INTO users (first_name,last_name,email,username,password,register_date,profile_picture,description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, email);
		ps.setString(4, userName);
		// ps.setString(5, Encrypter.encrypt(password));
		ps.setString(5, password);
		ps.setDate(6, Date.valueOf(LocalDate.now()));
		ps.setString(7, pictureName);
		ps.setString(8, description);
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}
	}

	public synchronized boolean existUser(String userName) throws SQLException {
		PreparedStatement ps = null;
		boolean userExists = true;
		if (!CommonUtils.isValidString(userName)) {
			userExists = false;
		}
		ps = manager.getConnection().prepareStatement("SELECT count(*)>0 FROM users WHERE username=?;");
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			userExists = rs.getBoolean(1);
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return userExists;
	}

	public synchronized boolean existUser(Long id) throws SQLException {
		PreparedStatement ps = null;
		boolean userExists = true;
		if (id == null) {
			userExists = false;
		}
		ps = manager.getConnection().prepareStatement("SELECT count(*)>0 FROM users WHERE user_id=?;");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			userExists = rs.getBoolean(1);
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return userExists;
	}

	public synchronized String deleteUser(String username) throws SQLException {
		if (!CommonUtils.isValidString(username)) {
			return "Invalid username";
		}
		PreparedStatement ps = manager.getConnection().prepareStatement("DELETE FROM users WHERE username=?;");
		ps.setString(1, username);
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}
		return "User deleted successfully";
	}

	public synchronized User getUser(String username) throws SQLException {
		User user = null;
		if (existUser(username)) {
			PreparedStatement ps = manager.getConnection().prepareStatement(
					"SELECT user_id,first_name,last_name,email,username,register_date,profile_picture,description FROM users WHERE username=?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			rs.next();
			user = new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getString("email"), rs.getString("username"), rs.getDate("register_date").toLocalDate(),
					rs.getString("profile_picture"), rs.getString("description"));

			// Set<Album> albumsOfUser =
			// AlbumDao.getInstance().getAllAlbumFromUser(user);
			// user.setAlbumsOfUser(albumsOfUser);

			Set<User> followers = getAllFollowersForUser(username);
			user.setFollowers(followers);

			Set<User> following = getAllFollowedForUser(username);
			user.setFollowing(following);

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return user;
	}

	public synchronized User getUser(Long id) throws SQLException {
		User user = null;
		if (existUser(id)) {
			PreparedStatement ps = manager.getConnection().prepareStatement(
					"SELECT user_id,first_name,last_name,email,username,register_date,profile_picture,description FROM users WHERE user_id=?;");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			rs.next();
			user = new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getString("email"), rs.getString("username"), rs.getDate("register_date").toLocalDate(),
					rs.getString("profile_picture"), rs.getString("description"));

			// Set<Album> albumsOfUser =
			// AlbumDao.getInstance().getAllAlbumFromUser(user);
			// user.setAlbumsOfUser(albumsOfUser);

			Set<User> followers = getAllFollowersForUser(user.getUserName());
			user.setFollowers(followers);

			Set<User> following = getAllFollowedForUser(user.getUserName());
			user.setFollowing(following);

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return user;
	}

	public synchronized Set<User> getAllFollowersForUser(String username) throws SQLException {

		Set<User> allFollowers = new HashSet();

		if (!CommonUtils.isValidString(username)) {
			return null;
		}
		manager.getConnection().setAutoCommit(false);
		try {
			PreparedStatement ps = manager.getConnection()
					.prepareStatement("SELECT user_id FROM users WHERE username=?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Long userId = rs.getLong(1);

			String selectAllFollowersFromDB = "SELECT follower_id FROM user_follower WHERE user_id=?;";

			PreparedStatement psOne = manager.getConnection().prepareStatement(selectAllFollowersFromDB);
			psOne.setLong(1, userId);
			ResultSet rsOne = psOne.executeQuery();
			while (rsOne.next()) {
				Long followerId = rsOne.getLong("follower_id");

				PreparedStatement psTwo = manager.getConnection()
						.prepareStatement("SELECT username FROM users WHERE user_id=?;");
				psTwo.setLong(1, followerId);
				ResultSet rsTwo = psTwo.executeQuery();
				rsTwo.next();
				String usernameOfFollower = rsTwo.getString(1);

				allFollowers.add(getBasicUser(usernameOfFollower));
				if (psTwo != null) {
					psTwo.close();
				}
				if (rsTwo != null) {
					rsTwo.close();
				}
			}
			if (ps != null) {
				ps.close();
			}
			if (psOne != null) {
				psOne.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rsOne != null) {
				rsOne.close();
			}
			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;
		} finally {
			manager.getConnection().setAutoCommit(true);
		}
		return allFollowers;
	}

	public synchronized Set<User> getAllFollowedForUser(String followerUsername) throws SQLException {
		if (!CommonUtils.isValidString(followerUsername)) {
			return null;
		}
		manager.getConnection().setAutoCommit(false);
		Set<User> allFollowed = new HashSet();
		try {
			// take id of the follower
			PreparedStatement ps = manager.getConnection()
					.prepareStatement("SELECT user_id FROM users WHERE username=?;");
			ps.setString(1, followerUsername);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Long followerId = rs.getLong(1);

			String selectAllFollowedFromDB = "SELECT user_id FROM user_follower WHERE follower_id=?;";

			PreparedStatement psOne = manager.getConnection().prepareStatement(selectAllFollowedFromDB);
			psOne.setLong(1, followerId);
			ResultSet rsOne = psOne.executeQuery();
			while (rsOne.next()) {
				Long followedUserId = rsOne.getLong("user_id");

				PreparedStatement psTwo = manager.getConnection()
						.prepareStatement("SELECT username FROM users WHERE user_id=?;");
				psTwo.setLong(1, followedUserId);
				ResultSet rsTwo = psTwo.executeQuery();
				rsTwo.next();
				String usernameOfFollowed = rsTwo.getString(1);

				allFollowed.add(getBasicUser(usernameOfFollowed));
				if (psTwo != null) {
					psTwo.close();
				}
				if (rsTwo != null) {
					rsTwo.close();
				}
			}
			if (ps != null) {
				ps.close();
			}
			if (psOne != null) {
				psOne.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rsOne != null) {
				rsOne.close();
			}

			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;
		} finally {
			manager.getConnection().setAutoCommit(true);
		}
		return allFollowed;
	}

	public synchronized void addToFollowedUsers(String user, String follower) throws SQLException {
		Long userId = 0L;
		Long followerId = 0L;

		manager.getConnection().setAutoCommit(false);
		try {
			if (existUser(user) && existUser(follower)) {
				PreparedStatement ps = null;
				ps = manager.getConnection()
						.prepareStatement("INSERT INTO user_follower (user_id,follower_id) VALUES (?, ?)");

				PreparedStatement psUser = null;
				psUser = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psUser.setString(1, user);
				ResultSet rsUser = psUser.executeQuery();
				if (rsUser.next()) {
					userId = rsUser.getLong(1);
				}
				PreparedStatement psFollower = null;
				psFollower = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psFollower.setString(1, follower);
				ResultSet rsFollower = psFollower.executeQuery();
				if (rsFollower.next()) {
					followerId = rsFollower.getLong(1);
				}
				if (userId != 0L && followerId != 0L) {
					ps.setLong(1, userId);
					ps.setLong(2, followerId);
					ps.executeUpdate();
				}
				if (ps != null) {
					ps.close();
				}
				if (psUser != null) {
					psUser.close();
				}
				if (psFollower != null) {
					psFollower.close();
				}
				if (rsUser != null) {
					rsUser.close();
				}
				if (rsFollower != null) {
					rsFollower.close();
				}
			}
			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;

		} finally {
			manager.getConnection().setAutoCommit(true);
		}

	}

	public synchronized void removeFromFollowedUsers(String user, String follower) throws SQLException {
		Long userId = 0L;
		Long followerId = 0L;

		manager.getConnection().setAutoCommit(false);
		try {
			if (existUser(user) && existUser(follower)) {
				PreparedStatement ps = null;
				ps = manager.getConnection()
						.prepareStatement("DELETE FROM user_follower WHERE user_id=? AND follower_id=?");

				PreparedStatement psUser = null;
				psUser = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psUser.setString(1, user);
				ResultSet rsUser = psUser.executeQuery();
				if (rsUser.next()) {
					userId = rsUser.getLong(1);
				}
				PreparedStatement psFollower = null;
				psFollower = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psFollower.setString(1, follower);
				ResultSet rsFollower = psFollower.executeQuery();
				if (rsFollower.next()) {
					followerId = rsFollower.getLong(1);
				}
				if (userId != 0L && followerId != 0L) {
					ps.setLong(1, userId);
					ps.setLong(2, followerId);
					ps.executeUpdate();
				}
				if (ps != null) {
					ps.close();
				}
				if (psUser != null) {
					psUser.close();
				}
				if (psFollower != null) {
					psFollower.close();
				}
				if (rsUser != null) {
					rsUser.close();
				}
				if (rsFollower != null) {
					rsFollower.close();
				}
			}
			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;

		} finally {
			manager.getConnection().setAutoCommit(true);
		}
	}

	public synchronized ArrayList<User> getAllUsers() throws SQLException {
		PreparedStatement ps = manager.getConnection().prepareStatement(
				"SELECT first_name,last_name,email,username,register_date,profile_picture,description FROM users;");
		ResultSet rs = ps.executeQuery();
		ArrayList<User> users = new ArrayList<>();
		while (rs.next()) {
			users.add(new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
					rs.getString("username"), rs.getDate("register_date").toLocalDate(),
					rs.getString("profile_picture"), rs.getString("description")));
		}

		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return users;
	}

	public synchronized boolean checkUserPass(String username, String password) throws SQLException {
		PreparedStatement ps = null;
		boolean passMatchUsername = false;

		ps = manager.getConnection().prepareStatement("SELECT password FROM users WHERE username=?;");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();

		String actualPass = rs.getString(1);
		// if (Encrypter.encrypt(password).equals(actualPass)) {
		if (password.equals(actualPass)) {
			passMatchUsername = true;
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return passMatchUsername;
	}

	public synchronized User getBasicUser(String username) throws SQLException {
		User user = null;
		if (existUser(username)) {
			PreparedStatement ps = manager.getConnection().prepareStatement(
					"SELECT user_id,first_name,last_name,email,username,register_date,profile_picture,description FROM users WHERE username=?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			rs.next();
			user = new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getString("email"), rs.getString("username"), rs.getDate("register_date").toLocalDate(),
					rs.getString("profile_picture"), rs.getString("description"));

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return user;
	}

	public synchronized boolean userFollowerIsFollowed(String username, String followerName) throws SQLException {
		boolean userFollowerIsFollowed = false;
		manager.getConnection().setAutoCommit(false);
		if (!CommonUtils.isValidString(username) || !CommonUtils.isValidString(followerName)) {
			return userFollowerIsFollowed = false;
		}
		try {
			if (existUser(username) && existUser(followerName)) {
				PreparedStatement ps = null;
				ps = manager.getConnection()
						.prepareStatement("SELECT count(*)>0 FROM user_follower WHERE follower_id=? AND user_id=?;");

				PreparedStatement psUser = null;
				long userId = 0;
				psUser = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psUser.setString(1, username);
				ResultSet rsUser = psUser.executeQuery();
				if (rsUser.next()) {
					userId = rsUser.getLong(1);
				}

				PreparedStatement psFollower = null;
				long followerId = 0;
				psFollower = manager.getConnection().prepareStatement("SELECT user_id FROM users WHERE username=?;");
				psFollower.setString(1, followerName);
				ResultSet rsFollower = psFollower.executeQuery();
				if (rsFollower.next()) {
					followerId = rsFollower.getLong(1);
				}
				ResultSet rs = null;
				if (userId != 0L && followerId != 0L) {
					ps.setLong(1, userId);
					ps.setLong(2, followerId);
					rs = ps.executeQuery();
				}
				if (rs.next()) {
					userFollowerIsFollowed = rs.getBoolean(1);
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (psUser != null) {
					psUser.close();
				}
				if (psFollower != null) {
					psFollower.close();
				}
				if (rsUser != null) {
					rsUser.close();
				}
				if (rsFollower != null) {
					rsFollower.close();
				}
			}
			manager.getConnection().commit();
		} catch (SQLException e) {
			manager.getConnection().rollback();
			throw e;

		} finally {
			manager.getConnection().setAutoCommit(true);
		}
		return userFollowerIsFollowed;
	}

	public synchronized String getUserPic(Long userId) throws SQLException {
		if (userId == null) {
			return null;
		}

		PreparedStatement ps = manager.getConnection()
				.prepareStatement("SELECT profile_picture FROM users WHERE user_id=?;");
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		rs.next();
		String pic = rs.getString("profile_picture");

		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		return pic;
	}
}
