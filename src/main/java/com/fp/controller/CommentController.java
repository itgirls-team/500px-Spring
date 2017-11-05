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

	private static final String REG_SUCC_MSG = "Comment add successful";
	
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
		// TODO validate input data

		Post post = ((Post) session.getAttribute("post"));
		if (session.getAttribute("user") == null) {
			// return "login"; // TODO !!!! return RespEntity with status and
			// error message
			return null;
		}
		User loggedUser = (User) session.getAttribute("user");
		Long userId = loggedUser.getId();

		Set<Comment> postComments = post.getCommentsOfPost();
		Comment comment = null;
		for (Comment commentOfPost : postComments) {
			if (commentOfPost.getId().equals(commentId)) {
				comment = commentOfPost;
				break;
			}
		}

		Set<User> likers = comment.getUsersWhoLikeComment();
		Set<User> likersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
		likersUpdated.addAll(likers);

		Set<User> dislikers = comment.getUsersWhoDislikeComment();
		Set<User> dislikersUpdated = dislikers; // by default

		boolean userIsInLikers = false;
		for (User user : likersUpdated) {
			if (user.getId().equals(userId)) {
				userIsInLikers = true;
				break;
			}
		}

		if (userIsInLikers) {
			// unlike only
			likersUpdated.remove(loggedUser);
			try {
				commentDao.removeLikeComment(commentId, loggedUser.getUserName());
			} catch (SQLException e) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
			}
		} else {
			dislikersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
			dislikersUpdated.addAll(dislikers);

			boolean userIsInDislikers = false;
			for (User user : dislikersUpdated) {
				if (user.getId().equals(userId)) {
					userIsInDislikers = true;
					break;
				}
			}

			if (userIsInDislikers) {
				// remove it from dislike list
				dislikersUpdated.remove(loggedUser);
				try {
					commentDao.removeDislike(commentId, loggedUser.getUserName());
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				comment.setUsersWhoDislikeComment(dislikersUpdated);
			}

			// actual like
			likersUpdated.add(loggedUser); // like (in copy collection)
			try {
				commentDao.likeComment(commentId, loggedUser.getUserName());
			} catch (SQLException e1) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
			}
		}
		comment.setUsersWhoLikeComment(likersUpdated); // updating session state

		// transform as DTO
		UserDto userDto = loggedUser.dto();
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = dislikersUpdated.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		CommentDto dto = new CommentDto(commentId, userDto, post.getId(), dtoLikers, dtoDislikers);
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/dislikeComment", method = RequestMethod.POST)
	public ResponseEntity disLikeComment(@RequestParam("commentId") Long commentId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO validate input data

		Post post = ((Post) session.getAttribute("post"));
		if (session.getAttribute("user") == null) {
			// return "login"; // TODO !!!! return RespEntity with status and
			// error message
			return null;
		}
		User loggedUser = (User) session.getAttribute("user");
		Long userId = loggedUser.getId();

		Set<Comment> postComments = post.getCommentsOfPost();
		Comment comment = null;
		for (Comment commentOfPost : postComments) {
			if (commentOfPost.getId().equals(commentId)) {
				comment = commentOfPost;
				break;
			}
		}

		Set<User> dislikers = comment.getUsersWhoDislikeComment();
		Set<User> dislikersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
		dislikersUpdated.addAll(dislikers);

		Set<User> likers = comment.getUsersWhoLikeComment();
		Set<User> likersUpdated = likers; // by default

		boolean userIsAlreadyDisliker = false;
		for (User user : dislikersUpdated) {
			if (user.getId().equals(userId)) {
				userIsAlreadyDisliker = true;
				break;
			}
		}

		if (userIsAlreadyDisliker) {
			// undislike only
			dislikersUpdated.remove(loggedUser);
			try {
				commentDao.removeDislike(commentId, loggedUser.getUserName());
			} catch (SQLException e) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
			}
		} else {
			likersUpdated = new TreeSet<>(Comparator.comparing(User::getUserName).reversed());
			likersUpdated.addAll(likers);

			boolean userHasLikedComment = false;
			for (User user : likersUpdated) {
				if (user.getId().equals(userId)) {
					userHasLikedComment = true;
					break;
				}
			}

			if (userHasLikedComment) {
				// remove it from like list
				likersUpdated.remove(loggedUser);
				try {
					commentDao.removeLikeComment(commentId, loggedUser.getUserName());
				} catch (SQLException e) {
					request.setAttribute("error", "Problem with the database. Could not execute query!");
				}
				comment.setUsersWhoLikeComment(likersUpdated);
			}

			// actual dislike
			dislikersUpdated.add(loggedUser); // dislike (in copy collection)
			try {
				commentDao.addDislike(commentId, loggedUser.getUserName());
			} catch (SQLException e1) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
			}
		}
		comment.setUsersWhoDislikeComment(dislikersUpdated); // updating session
																// state
		// transform as DTO
		UserDto userDto = loggedUser.dto();
		List<UserDto> dtoLikers = likersUpdated.stream().map(userLiker -> userLiker.dto()).collect(Collectors.toList());
		List<UserDto> dtoDislikers = dislikersUpdated.stream().map(userDisliker -> userDisliker.dto())
				.collect(Collectors.toList());
		CommentDto dto = new CommentDto(commentId, userDto, post.getId(), dtoLikers, dtoDislikers);
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}
	
	private String validateInputData(String comment) {
		if (comment == null || comment.isEmpty()) {
			return "Please enter comment!";
		}
		return REG_SUCC_MSG;
	}

}