package com.fp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
import com.fp.dbModel.PostDao;
import com.fp.model.Post;
import com.fp.model.Tag;


@Controller
@MultipartConfig
class UploadImageController {

	@Autowired
	PostDao postDao;
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String uploadGet(){
		return "upload";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String zapishiSnimka(@RequestParam("failche") MultipartFile file,HttpSession ses,
								HttpServletRequest request){
		//SAVE IMAGE
		try {
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType type = allTypes.forName(file.getContentType());
			String ext = type.getExtension(); 
			File f = new File(WebAppInitializer.LOCATION + File.separator + file.getOriginalFilename());
			String description = request.getParameter("description");
			String[] inputTags = request.getParameter("tags").split(",");
			Set<Tag> tags = new HashSet<>();
			for (String string : inputTags) {
				tags.add(new Tag(string));
			}
			long albumId = (long) ses.getAttribute("albumId");
 			Post post = new Post(file.getOriginalFilename(), description, tags,albumId);
 			ses.setAttribute("post", post);
			postDao.uploadPost(post);
			file.transferTo(f);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MimeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "post";
	}
	
	@RequestMapping(value="/upload/{id}", method = RequestMethod.GET)
	public void daiSnimka(@RequestParam("id") Long postId){
		Post p=null;
		try {
			p = postDao.getPost(postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String url = p.getPath();
		File f = new File(WebAppInitializer.LOCATION + File.separator + url);
	}

	/*
	@RequestMapping(value="/upload", method = RequestMethod.GET)
	public void daiSnimka(HttpServletResponse resp){
		File f = new File(WebAppInitializer.LOCATION + File.separator + "homer-end-is-near.jpg");
		try {
			Files.copy(f.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
}
