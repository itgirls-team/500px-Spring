package com.fp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.config.WebAppInitializer;
import com.fp.dbModel.AlbumDao;
import com.fp.dbModel.PostDao;
import com.fp.model.Album;
import com.fp.model.Post;
import com.fp.model.Tag;
import com.fp.model.User;

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

	private static void read(String absolutePath, Long postId, HttpServletResponse response) {
		File myFile = new File(absolutePath);
		try (OutputStream out = response.getOutputStream()) {
			Files.copy(myFile.toPath(), out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readPicture(String filename, Long postId, HttpServletResponse response) throws IOException {
		String absolutePath = WebAppInitializer.LOCATION + File.separator + filename;
		System.out.println("Resources:readImage:absolutePath:" + absolutePath);
		read(absolutePath, postId, response);
	}

	@RequestMapping(value = "/postId/{id}", method = RequestMethod.GET)
	public void getPicture(@PathVariable("id") Long postId, HttpServletResponse response) {
		try {
			Post post = postDao.getPost(postId);
			readPicture(post.getPath(), postId, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Show picture of post
	@RequestMapping(value = "/showPosts", method = RequestMethod.GET)
	public void getPicture(HttpServletRequest request, HttpServletResponse response) {
		String cover = null;
		Album album = (Album) request.getSession().getAttribute("album");
		for (Post post : album.getPosts()) {
			cover = post.getPath();
			File file = new File(WebAppInitializer.LOCATION + cover);
			try {
				Files.copy(file.toPath(), response.getOutputStream());
			} catch (IOException e) {
				request.setAttribute("error", "problem with the stream. Could not open output stream!");
			}
		}
	}

}
