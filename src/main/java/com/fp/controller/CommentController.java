package com.fp.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fp.dbModel.CommentDao;
import com.fp.dbModel.DbManager;
import com.fp.dbModel.UserDao;
import com.fp.domain.CommentDto;
import com.fp.domain.UserDto;
import com.fp.model.Comment;
import com.fp.model.Post;
import com.fp.model.User;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	private DbManager manager;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity addComment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("postId") Long postId, @RequestParam("commenttxt") String description) {
		// check if user is logged
		// check if text is empty,null
		// if sth is wring set status!=200
		// update sessionScope.post.commentsOfPost after insert in db
		Comment comment = null;
		Long userId = ((User) session.getAttribute("user")).getId();
		String userName = ((User) session.getAttribute("user")).getUserName();
		if (session.getAttribute("user") != null) {
			try {
				comment = commentDao.addComment(userId, description, postId);
			} catch (SQLException e) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
				// TODO set resp status
				// return "index"; // TODO
			}
			Set<Comment> comments = new TreeSet<>(Comparator.comparing(Comment::getdateAndTimeOfUpload).reversed());
			for (Comment commentInPost : ((Post) (session.getAttribute("post"))).getCommentsOfPost()) {
				comments.add(commentInPost);
			}

			if (comment != null) {
				comments.add(comment);
			}

			((Post) session.getAttribute("post")).setCommentsOfPost(comments);
			response.setStatus(200);
		} else {
			// return "login"; // TODO return RespEntity with status and error
			// message
		}

		CommentDto dto = null;
		if (comment != null) {
			dto = new CommentDto(comment.getId(), userName, comment.getPostId(), comment.getDescription(),
					comment.getdateAndTimeOfUpload(), comment.getNumberOfLikes(), comment.getNumberOfDislikes());
		}
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/likeComment", method = RequestMethod.POST)
	public ResponseEntity likeComment(@RequestParam("commentId") Long commentId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Long userId = ((User) session.getAttribute("user")).getId();
		Post post = ((Post) session.getAttribute("post"));
		User loggedUser = (User) session.getAttribute("user");
		Set<Comment> comments = post.getCommentsOfPost();

		Set<Comment> commentsNew = new TreeSet<>(Comparator.comparing(Comment::getdateAndTimeOfUpload).reversed());
		for (Comment commentInPost : comments) {
			commentsNew.add(commentInPost);
		}
		Comment comment = null;
		for (Comment com : commentsNew) {
			if (com.getId().equals(commentId)) {
				comment = com;
				comment.setUserName(loggedUser.getUserName());
				break;
			}
		}
		Set<User> likers = comment.getUsersWhoLikeComment();
		Set<User> likersUpdated = likers;
		Set<User> usersDislike = comment.getUsersWhoDislikeComment();
		Set<User> usersDislikeNew = usersDislike;

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
					commentDao.likeComment(commentId, loggedUser.getUserName());
				} catch (SQLException e1) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				request.getSession().setAttribute("Liked", true);
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
					try {
						commentDao.removeDislike(commentId, loggedUser.getUserName());
					} catch (SQLException e) {
						request.setAttribute("error", "Problem with the database. Could not execute query!");
					}
				}
				comment.setUsersWhoDislikeComment(usersDislikeNew);
			} else {
				likersUpdated.remove(loggedUser);
				request.getSession().setAttribute("Liked", false);
				request.getSession().setAttribute("post", post);
				try {
					commentDao.removeLikeComment(commentId, loggedUser.getUserName());
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
			}
			comment.setUsersWhoLikeComment(likersUpdated);
		} else {
			// return "login"; // TODO return RespEntity with status and
			// error
			// message
		}
		for (Comment com : commentsNew) {
			if (com.getId().equals(commentId)) {
				com = comment;
				break;
			}
		}

		// TODO people who like are not in session
		// TODO change dislike like like
		post.setCommentsOfPost(commentsNew);
		request.getSession().setAttribute("post", post);
		CommentDto dto = null;
		UserDto userDto = new UserDto(userId, loggedUser.getUserName());
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = usersDislikeNew.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		dto = new CommentDto(commentId, userDto, post.getId(), dtoLikers, dtoDislikers);
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/dislikeComment", method = RequestMethod.POST)
	public ResponseEntity disLikeComment(@RequestParam("commentId") Long commentId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Long userId = ((User) session.getAttribute("user")).getId();
		Post post = ((Post) session.getAttribute("post"));
		User loggedUser = (User) session.getAttribute("user");
		Set<Comment> comments = post.getCommentsOfPost();
		Comment comment = null;
		for (Comment com : comments) {
			if (com.getId().equals(commentId)) {
				comment = com;
				comment.setUserName(loggedUser.getUserName());
			}
		}
		Set<User> likers = comment.getUsersWhoLikeComment();
		Set<User> likersUpdated = likers;
		Set<User> usersDislike = comment.getUsersWhoDislikeComment();
		Set<User> usersDislikeNew = usersDislike;

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
					commentDao.addDislike(commentId, loggedUser.getUserName());
				} catch (SQLException e1) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				request.getSession().setAttribute("Disliked", true);
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
					try {
						commentDao.removeLikeComment(commentId, loggedUser.getUserName());
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
					commentDao.removeDislike(commentId, loggedUser.getUserName());
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
			}
			post.setUsersWhoDislike(usersDislikeNew);
		} else {
			// return "login"; // TODO return RespEntity with status and error
			// message
		}
		request.getSession().setAttribute("post", post);
		CommentDto dto = null;
		UserDto userDto = new UserDto(userId, loggedUser.getUserName());
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = usersDislikeNew.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		dto = new CommentDto(commentId, userDto, post.getId(), dtoLikers, dtoDislikers);
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}
}