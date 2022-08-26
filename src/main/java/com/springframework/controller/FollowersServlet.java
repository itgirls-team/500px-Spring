package com.springframework.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springframework.dbModel.DbManager;
import com.springframework.dbModel.UserDao;
import com.springframework.model.User;



@WebServlet("/followers")
public class FollowersServlet extends HttpServlet {

	private Connection connection;

	@Override
	public void init() throws ServletException {
		// open connections
		super.init();
		connection = DbManager.getInstance().getConnection();
	}

	@Override
	public void destroy() {
		// close connections
		super.destroy();
		DbManager.getInstance().closeConnection();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageToRedirect = request.getParameter("pageToRedirect");
		request.getSession().setAttribute("pageToRedirect", pageToRedirect);
		boolean userFollowerIsFollowed;
		Set<User> followers;
		Map<User, Boolean> userFollowersAreFollowed = new HashMap<User, Boolean>();
		try {
			followers = UserDao.getInstance()
					.getAllFollowersForUser(((User) (request.getSession().getAttribute("user"))).getUserName());
			request.getSession().setAttribute("followers", followers);

			for (User follower : followers) {
				userFollowerIsFollowed = UserDao.getInstance().userFollowerIsFollowed(
						((User) (request.getSession().getAttribute("user"))).getUserName(), follower.getUserName());
				userFollowersAreFollowed.put(follower, userFollowerIsFollowed);
			}
			request.getSession().setAttribute("isFollowed", userFollowersAreFollowed);
			if (userFollowersAreFollowed.isEmpty()) {
				request.getSession().setAttribute("noFollowers", true);
			}
			response.sendRedirect("followers.jsp");
		} catch (SQLException e) {
			request.setAttribute("error", "problem with the database. Could not execute query!");
			return;
		}
	}
}
