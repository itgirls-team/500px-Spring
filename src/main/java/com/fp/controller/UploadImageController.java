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

	@Autowired
	PostDao postDao;
	@Autowired
	AlbumDao albumDao;
	
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
			
			Album album = albumDao.getAlbum(albumId);
			album.setPosts(postDao.getAllPostsFromAlbum(albumId));
			request.getSession().setAttribute("album", album);
			
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
		return "upload";
	}
	
	
}
