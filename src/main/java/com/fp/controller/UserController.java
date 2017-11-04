package com.fp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fp.config.WebAppInitializer;
import com.fp.dbModel.DbManager;
import com.fp.dbModel.PostDao;
import com.fp.dbModel.UserDao;
import com.fp.model.Post;
import com.fp.model.User;

@Controller
@MultipartConfig(maxFileSize = 200000000)
public class UserController {
	public static final String AVATAR_URL = "C:/pictures/";
	private static final int MINIMUM_PASSWORD_LENGTH = 7;
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern VALID_USERNAME = Pattern.compile("^[a-zA-Z0-9._-]{3,}$", Pattern.CASE_INSENSITIVE);
	private static final String REG_SUCC_MSG = "Registration successful";

	@Autowired
	private DbManager manager;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("logged");
		boolean logged = (obj != null && ((boolean) obj));
		if (session.isNew() || !logged || request.getSession().getAttribute("user") == null) {
			return "index";
		} else {
			return "main";
		}
	}

	@RequestMapping(value = "*", method = RequestMethod.GET)
	public String handleError() {
		return "error404";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("avatar") MultipartFile file) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confpassword");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String description = request.getParameter("description");
		String avatarUrl = "";
		try {
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType type = allTypes.forName(file.getContentType());
			String ext = type.getExtension();
			File f = new File(WebAppInitializer.LOCATION + File.separator + userName + ext);
			file.transferTo(f);
			if (f.length() == 0) {
				avatarUrl = "default.jpg";
			} else {
				avatarUrl = userName + ext;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "error500";
		} catch (IOException e) {
			e.printStackTrace();
			return "error500";
		} catch (MimeTypeException e) {
			e.printStackTrace();
			return "error500";
		}

		String validationMessage = validateRegisterData(userName, password, confirmPassword, email, firstName,
				lastName);

		// Registration of user
		if (validationMessage.equals(REG_SUCC_MSG)) {
			try {
				userDao.insertUser(userName, password, email, firstName, lastName, description, avatarUrl);
				User user = new User(userName, password, email, firstName, lastName, description, avatarUrl);
				request.getSession().setAttribute("user", user);
				loadFollowedUser(request);
				return "main";
			} catch (SQLException e) {
				e.printStackTrace();
				return "error500";
			}
		} else {
			request.setAttribute("error", validationMessage);
			return "register";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if (validateLogInData(username, password)) {
				// login
				User user = userDao.getUser(username);
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("logged", true);
				// add to followed users
				loadFollowedUser(request);
				return "main";
			} else {
				// redirect to error page or to login.html again with popup for
				// error
				request.setAttribute("error", "username or password mismatch!");
				return "login";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
	}

	private void loadFollowedUser(HttpServletRequest request) {
		boolean followedUserIsFollowed = true;
		Set<User> followed;
		Map<User, Boolean> followedUsersAreFollowed = new HashMap<User, Boolean>();
		try {
			followed = userDao
					.getAllFollowedForUser(((User) (request.getSession().getAttribute("user"))).getUserName());
			request.getSession().setAttribute("followed", followed);

			for (User followedUser : followed) {
				followedUsersAreFollowed.put(followedUser, followedUserIsFollowed);
			}
			request.getSession().setAttribute("isFollowed", followedUsersAreFollowed);
			if (followedUsersAreFollowed.isEmpty()) {
				request.getSession().setAttribute("noFollowed", true);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "problem with the database. Could not execute query!");
		}
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "main";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutGet(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		if (request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
			request.getSession().setAttribute("logged", false);
		}
		request.getSession().invalidate();
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logoutPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		if (request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
			request.getSession().setAttribute("logged", false);
		}
		request.getSession().invalidate();
		return "index";
	}

	@RequestMapping(value = "/followInAnotherPage", method = RequestMethod.POST)
	public String followInAnotherPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			User followedUser = (User) (session.getAttribute("searchUser"));
			String followedUserName = followedUser.getUserName();
			User loggedUser = (User) (request.getSession().getAttribute("user"));
			userDao.addToFollowedUsers(followedUserName, loggedUser.getUserName());
			Map<User, Boolean> followers = (Map<User, Boolean>) request.getSession().getAttribute("isFollowed");
			for (Map.Entry<User, Boolean> entry : followers.entrySet()) {
				if (entry.getKey().getUserName().equals(followedUserName)) {
					entry.setValue(true);
					break;
				}
			}
			request.getSession().setAttribute("isFollowed", followers);
			request.getSession().setAttribute("noFollowed", false);
		} catch (SQLException e) {
			request.setAttribute("error", "problem with the database. Could not execute query!");
			e.printStackTrace();
		}
		return "profile";
	}

	@RequestMapping(value = "/unfollowInAnotherPage", method = RequestMethod.POST)
	public String unfollowInAnotherPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			User followedUser = (User) (session.getAttribute("searchUser"));
			String followedUserName = followedUser.getUserName();
			User loggedUser = (User) (request.getSession().getAttribute("user"));
			userDao.removeFromFollowedUsers(followedUserName, loggedUser.getUserName());
			Map<User, Boolean> followers = (Map<User, Boolean>) request.getSession().getAttribute("isFollowed");
			for (Map.Entry<User, Boolean> entry : followers.entrySet()) {
				if (entry.getKey().getUserName().equals(followedUserName)) {
					entry.setValue(false);
					break;
				}
			}
			request.getSession().setAttribute("isFollowed", followers);
			request.getSession().setAttribute("noFollowed", true);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return "profile";
	}

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public String follow(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String pageToRedirect = "";
		try {
			String followedUserName = request.getParameter("followedUserName");
			pageToRedirect = (String) request.getSession().getAttribute("pageToRedirect");
			User loggedUser = (User) (request.getSession().getAttribute("user"));
			userDao.addToFollowedUsers(followedUserName, loggedUser.getUserName());
			Map<User, Boolean> followers = (Map<User, Boolean>) request.getSession().getAttribute("isFollowed");
			for (Map.Entry<User, Boolean> entry : followers.entrySet()) {
				if (entry.getKey().getUserName().equals(followedUserName)) {
					entry.setValue(true);
					break;
				}
			}
			request.getSession().setAttribute("isFollowed", followers);

		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return pageToRedirect;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	public String unfollow(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String pageToRedirect = "";
		try {
			String followedUserName = request.getParameter("followedUserName");
			pageToRedirect = (String) request.getSession().getAttribute("pageToRedirect");
			User loggedUser = (User) (request.getSession().getAttribute("user"));
			userDao.removeFromFollowedUsers(followedUserName, loggedUser.getUserName());
			Map<User, Boolean> followers = (Map<User, Boolean>) request.getSession().getAttribute("isFollowed");
			for (Map.Entry<User, Boolean> entry : followers.entrySet()) {
				if (entry.getKey().getUserName().equals(followedUserName)) {
					entry.setValue(false);
					break;
				}
			}
			request.getSession().setAttribute("isFollowed", followers);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return pageToRedirect;
	}

	@RequestMapping(value = "/following", method = RequestMethod.GET)
	public String following(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String pageToRedirect = request.getParameter("pageToRedirect");
		request.getSession().setAttribute("pageToRedirect", pageToRedirect);
		boolean followedUserIsFollowed = true;
		Set<User> followed;
		Map<User, Boolean> followedUsersAreFollowed = new HashMap<User, Boolean>();
		try {
			followed = userDao
					.getAllFollowedForUser(((User) (request.getSession().getAttribute("user"))).getUserName());
			request.getSession().setAttribute("followed", followed);

			for (User followedUser : followed) {
				followedUsersAreFollowed.put(followedUser, followedUserIsFollowed);
			}
			request.getSession().setAttribute("isFollowed", followedUsersAreFollowed);
			if (followedUsersAreFollowed.isEmpty()) {
				request.getSession().setAttribute("noFollowed", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return "following";
	}

	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public String followers(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String pageToRedirect = request.getParameter("pageToRedirect");
		request.getSession().setAttribute("pageToRedirect", pageToRedirect);
		boolean userFollowerIsFollowed;
		Set<User> followers;
		Map<User, Boolean> userFollowersAreFollowed = new HashMap<User, Boolean>();
		try {
			followers = userDao
					.getAllFollowersForUser(((User) (request.getSession().getAttribute("user"))).getUserName());
			request.getSession().setAttribute("followers", followers);

			for (User follower : followers) {
				userFollowerIsFollowed = userDao.userFollowerIsFollowed(
						((User) (request.getSession().getAttribute("user"))).getUserName(), follower.getUserName());
				userFollowersAreFollowed.put(follower, userFollowerIsFollowed);
			}
			request.getSession().setAttribute("isFollowed", userFollowersAreFollowed);
			if (userFollowersAreFollowed.isEmpty()) {
				request.getSession().setAttribute("noFollowers", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return "followers";
	}

	@RequestMapping(value = "/newsfeed", method = RequestMethod.GET)
	public String showNewsFeed(HttpServletRequest request, Model model, HttpSession session) {
		LinkedHashSet<Post> posts;
		try {
			model.addAttribute("sortPost", true);
			model.addAttribute("hideUploadPost", true);
			posts = postDao.getAllPostOrderByDate();
			model.addAttribute("posts", posts);
			request.getSession().setAttribute("posts", posts);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return "posts";
	}

	private boolean validateLogInData(String username, String password) throws SQLException {
		if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
			if (userDao.existUser(username) && userDao.checkUserPass(username, password)) {
				return true;
			}
		}
		return false;
	}

	private boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	private boolean validateUsername(String userName) {
		Matcher matcher = VALID_USERNAME.matcher(userName);
		return matcher.find();
	}

	private boolean confirmPasswords(String password, String confPassword) {
		if (confPassword.equals(password)) {
			return true;
		}
		return false;
	}

	public static boolean validateFirstName(String firstName) {
		return firstName.matches("[A-Z][a-zA-Z]*");
	}

	public static boolean validateLastName(String lastName) {
		return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}

	private String validateRegisterData(String userName, String password, String confPassword, String email,
			String firstName, String lastName) {

		if (userName == null || password == null || confPassword == null || email == null || firstName == null
				|| lastName == null || userName.isEmpty() || password.isEmpty() || confPassword.isEmpty()
				|| email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			return "Please fill all the required fields!";
		}
		if (password.length() < MINIMUM_PASSWORD_LENGTH) {
			return "Password must be longer than 7 symbols!";
		}
		if (!validateEmail(email)) {
			return "Email is invalid!";
		}
		if (!validateUsername(userName)) {
			return "UserName contains forbidden symbols!";
		}
		if (!validateFirstName(firstName)) {
			return "Invalid First name!";
		}
		if (!validateLastName(lastName)) {
			return "Invalid Last name!";
		}
		if (!confirmPasswords(password, confPassword)) {
			return "Confirm password is different than the password!";
		}
		try {
			if (userDao.existUser(userName)) {
				return "This username already exists!";
			}
		} catch (SQLException e) {
			return "There is problem with the prepared statement";
		}
		return REG_SUCC_MSG;
	}

}
