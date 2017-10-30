package com.fp.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fp.dbModel.AlbumDao;
import com.fp.dbModel.PostDao;
import com.fp.domain.PostDto;
import com.fp.domain.UserDto;
import com.fp.model.Album;
import com.fp.model.Post;
import com.fp.model.User;
import com.fp.utils.CommonUtils;

@Controller
@MultipartConfig
public class PostController {

	@Autowired
	public PostDao postDao;

	@Autowired
	public AlbumDao albumDao;

	public static final String POSTS_URL = "C:/pictures/";

	// Post
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String showPost(HttpSession session, HttpServletRequest request) {
		try {
			long postId = Long.parseLong(request.getParameter("postId"));
			request.getSession().setAttribute("postId", postId);
			request.getSession().setAttribute("post", postDao.getPost(postId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "post";
	}

	// ShowPostsFromAlbum
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showAllPosts(HttpSession session, HttpServletRequest request) {

		try {
			long albumId = Long.parseLong(request.getParameter("albumId"));
			Album album = albumDao.getAlbum(albumId);
			album.setPosts(postDao.getAllPostsFromAlbum(albumId));
			session.setAttribute("album", album);
			session.setAttribute("albumId", albumId);
			request.getSession().setAttribute("posts", postDao.getAllPostsFromAlbum(albumId));
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
		}
		return "posts";
	}

	@RequestMapping(value = "/postId/{id}", method = RequestMethod.GET)
	public void getPicture(@PathVariable("id") Long postId, HttpServletResponse response, HttpServletRequest request) {

		try {
			Post post = postDao.getPost(postId);
			String cover = post.getPath();
			CommonUtils.showPicture(cover, response, request);
			// readPicture(post.getPath(), postId, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Show picture of post
	@RequestMapping(value = "/showPosts", method = RequestMethod.GET)
	public void getPicture(@RequestParam("postId") Long postId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			Post post = (Post) postDao.getPost(postId);
			String cover = post.getPath();
			CommonUtils.showPicture(cover, response, request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/likePost", method = RequestMethod.POST)
	public ResponseEntity likePost(@RequestParam("postId") Long postId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Long userId = ((User) session.getAttribute("user")).getId();
		Post post = ((Post) session.getAttribute("post"));
		Set<User> likers = post.getUsersWhoLike();
		Set<User> likersUpdated = likers;
		Set<User> usersDislike = post.getUsersWhoDislike();
		Set<User> usersDislikeNew = usersDislike;
		User loggedUser = (User) session.getAttribute("user");
		if (session.getAttribute("user") != null) {
			// TODO add user to people who likes
			likersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
			for (User user : likers) {
				likersUpdated.add(user);
			}
			boolean userIsInLikers = false;
			for (User user : likersUpdated) {
				if (user.getId().equals(userId)) {
					userIsInLikers = true;
					break;
				}
			}
			if (!userIsInLikers) {
				likersUpdated.add(loggedUser);
				try {
					postDao.like(postId, userId);
				} catch (SQLException e1) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				request.getSession().setAttribute("Liked", true);
				post.setLiked(true);
				request.getSession().setAttribute("post", post);
				// remove it from dislike list if it's there
				usersDislikeNew = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
				for (User user : usersDislike) {
					usersDislikeNew.add(user);
				}
				boolean userIsInDislikers = false;
				for (User user : usersDislikeNew) {
					if (user.getId().equals(userId)) {
						userIsInDislikers = true;
						break;
					}
				}
				if (userIsInDislikers) {
					usersDislikeNew.remove(loggedUser);
					request.getSession().setAttribute("Disliked", false);
					// TODO post.setDisliked(true);
					// TODO request.getSession().setAttribute("post",post);
					try {
						postDao.removePostDislike(postId, userId);
					} catch (SQLException e) {
						request.setAttribute("error", "Problem with the database. Could not execute query!");
					}
				}
				post.setUsersWhoDislike(usersDislikeNew);
			} else {
				likersUpdated.remove(loggedUser);
				request.getSession().setAttribute("Liked", false);
				post.setLiked(false);
				request.getSession().setAttribute("post", post);
				try {
					postDao.removePostLike(postId, userId);
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
			}
			post.setUsersWhoLike(likersUpdated);
		} else {
			// return "login"; // TODO return RespEntity with status and error
			// message
		}
		PostDto dto = null;
		UserDto userDto = new UserDto(userId, loggedUser.getUserName());
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = usersDislikeNew.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		dto = new PostDto(dtoLikers, dtoDislikers, userDto);
		return new ResponseEntity<PostDto>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/disLikePost", method = RequestMethod.POST)
	public ResponseEntity disLikePost(@RequestParam("postId") Long postId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Long userId = ((User) session.getAttribute("user")).getId();
		Post post = ((Post) session.getAttribute("post"));
		Set<User> likers = post.getUsersWhoLike();
		Set<User> likersUpdated = likers;
		Set<User> usersDislike = post.getUsersWhoDislike();
		Set<User> usersDislikeNew = usersDislike;
		User loggedUser = (User) session.getAttribute("user");
		if (session.getAttribute("user") != null) {
			// TODO add user to people who likes
			usersDislikeNew = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
			for (User user : usersDislike) {
				usersDislikeNew.add(user);
			}
			boolean userIsInDislikers = false;
			for (User user : usersDislikeNew) {
				if (user.getId().equals(userId)) {
					userIsInDislikers = true;
					break;
				}
			}
			if (!userIsInDislikers) {
				usersDislikeNew.add(loggedUser);
				try {
					postDao.dislike(postId, userId);
				} catch (SQLException e1) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				request.getSession().setAttribute("Disliked", true);
				// post.setLiked(true);
				request.getSession().setAttribute("post", post);
				// remove it from dislike list if it's there
				likersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
				for (User user : likers) {
					likersUpdated.add(user);
				}
				boolean userIsInLikers = false;
				for (User user : likersUpdated) {
					if (user.getId().equals(userId)) {
						userIsInLikers = true;
						break;
					}
				}
				if (userIsInLikers) {
					likersUpdated.remove(loggedUser);
					request.getSession().setAttribute("Liked", false);
					// TODO post.setDisliked(true);
					// TODO request.getSession().setAttribute("post",post);
					try {
						postDao.removePostLike(postId, userId);
					} catch (SQLException e) {
						request.setAttribute("error", "Problem with the database. Could not execute query!");
					}
				}
				post.setUsersWhoLike(likersUpdated);
			} else {
				usersDislikeNew.remove(loggedUser);
				request.getSession().setAttribute("Disliked", false);
				// post.setLiked(false);
				request.getSession().setAttribute("post", post);
				try {
					postDao.removePostDislike(postId, userId);
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
			}
			post.setUsersWhoDislike(usersDislikeNew);
		} else {
			// return "login"; // TODO return RespEntity with status and error
			// message
		}
		PostDto dto = null;
		UserDto userDto = new UserDto(userId, loggedUser.getUserName());
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = usersDislikeNew.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		dto = new PostDto(dtoLikers, dtoDislikers, userDto);
		return new ResponseEntity<PostDto>(dto, HttpStatus.OK);
	}
}
