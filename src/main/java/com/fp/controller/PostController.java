package com.fp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
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


import com.fp.dbModel.PostDao;
import com.fp.model.Album;
import com.fp.model.Post;
import com.fp.model.Tag;

@Controller
public class PostController {

	@Autowired
	public PostDao postDao;
	
	public static final String POSTS_URL = "C:/pictures/";

	
	// Post
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String showPost(HttpSession session, HttpServletRequest request) {
		try {
			long postId = Long.parseLong(request.getParameter("postId"));
			request.getSession().setAttribute("post", postDao.getPost(postId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "post";
	}

	// PostFromAlbum
	/*
	@RequestMapping(value="/posts/{albumId}" , method = RequestMethod.GET)
	public String products(Model model, @PathVariable("albumId") Integer albumId) {
		Set<Post> posts = null;
		try {
			posts = postDao.getAllPostsFromAlbum(albumId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("posts", posts);
		return "posts";
	}
	*/
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showAllPosts(HttpSession session, HttpServletRequest request) {
		long albumId = Long.parseLong(request.getParameter("albumId"));
		try {
			request.getSession().setAttribute("posts", postDao.getAllPostsFromAlbum(albumId));
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
		}
		return "posts";
	}

	
	
	// UploadPost
	@RequestMapping(value = "/UploadPost", method = RequestMethod.POST)
	public String uploadPost(HttpSession session, HttpServletRequest request) {
		try {
			String description = request.getParameter("description");
			String[] inputTags = request.getParameter("tags").split(",");
			Set<Tag> tags = new HashSet<>();
			for (String string : inputTags) {
				tags.add(new Tag(string));
			}

			Part postPart = request.getPart("image");
			InputStream fis = postPart.getInputStream();
			File myFile = new File(POSTS_URL + description + ".jpg");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(myFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int b = fis.read();
			while (b != -1) {
				fos.write(b);
				b = fis.read();
			}
			fis.close();
			fos.close();

			String postUrl = description + ".jpg";

			Post p = new Post(postUrl, description, tags);
			postDao.uploadPost(p);
			request.getSession().setAttribute("post", p);
			// request.getRequestDispatcher("post.jsp").forward(request,
			// response);
		} catch (SQLException e) {
			System.out.println("Error with upload post!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		return "post";
	}
	
	@RequestMapping(value = "/UploadPost", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response){
		return "uploadPost.jsp";
	}

}
