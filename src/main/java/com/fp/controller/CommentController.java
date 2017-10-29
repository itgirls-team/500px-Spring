package com.fp.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

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
import com.fp.domain.CommentDto;
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

	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity addComment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("postId") Long postId, @RequestParam("commenttxt") String description) {
		// check if user is logged
		// check if text is empty,null
		// if sth is wring set status!=200
		// update sessionScope.post.commentsOfPost after insert in db
		Comment comment = null;
		Long userId = ((User) session.getAttribute("user")).getId();
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
			dto = new CommentDto(comment.getId(), comment.getUserId(), comment.getPostId(), comment.getDescription(),
					comment.getdateAndTimeOfUpload(), comment.getNumberOfLikes(), comment.getNumberOfDislikes());
		}
		return new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}

}
