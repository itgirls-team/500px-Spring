package com.fp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fp.config.WebAppInitializer;
import com.fp.dbModel.AlbumDao;
import com.fp.dbModel.PostDao;
import com.fp.dbModel.UserDao;
import com.fp.model.Album;
import com.fp.model.Post;
import com.fp.model.Tag;
import com.fp.model.User;

@Controller
@MultipartConfig
class UploadImageController {

	private static final String REG_SUCC_MSG = "Post upload successful";
	
	@Autowired
	PostDao postDao;
	@Autowired
	AlbumDao albumDao;
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadGet(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		} else {
			return "upload";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String zapishiSnimka(@RequestParam("failche") MultipartFile file, HttpServletRequest request,
			HttpSession ses) {
		// SAVE IMAGE
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		} else {
			try {
				MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
				MimeType type = allTypes.forName(file.getContentType());
				String ext = type.getExtension();
				File f = new File(WebAppInitializer.LOCATION + File.separator + file.getOriginalFilename());
				String[] inputTags = request.getParameter("tags").split(",");
				Set<Tag> tags = new HashSet<>();
				for (String string : inputTags) {
					tags.add(new Tag(string));
				}
				long albumId = (long) ses.getAttribute("albumId");
				String description = (String) request.getParameter("description");
				String validationMessage = validateInputData(description);
				if (validationMessage.equals(REG_SUCC_MSG)) {
				Post post = new Post(file.getOriginalFilename(), description, tags, albumId,
						Timestamp.valueOf(LocalDateTime.now()));
				ses.setAttribute("post", post);
				postDao.uploadPost(post);
				file.transferTo(f);

				// update session
				Album album = albumDao.getAlbum(albumId);
				album.setPosts(postDao.getAllPostsFromAlbum(albumId));
				request.getSession().setAttribute("album", album);

				// change album cover
				String albumImage = post.getPath();
				album.setPicture(albumImage);
				albumDao.changeCover(album.getId(), albumImage);

				User u = (User) request.getSession().getAttribute("user");
				User realUser = userDao.getUser(u.getUserName());
				realUser.setAlbumsOfUser(albumDao.getAllAlbumFromUser(realUser.getUserName()));
				request.getSession().setAttribute("user", realUser);
				}
				else{
					request.setAttribute("emptyDescriptionField", validationMessage);
					return "upload";
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
			} catch (SQLException e) {
				e.printStackTrace();
				return "error500";
			}
			return "album";
		}
	}
	
	private String validateInputData(String description) {
		if (description == null || description.isEmpty()) {
			return "Please fill all the required fields!";
		}
		return REG_SUCC_MSG;
	}

}
